package org.shine.common.map.shp;

import com.mapabc.gds.constant.GdsConstants;
import com.mapabc.gds.util.CheckGeomtry;
import com.mapabc.gds.util.csv.CSVUtil;
import com.mapabc.gds.util.log.GLog;
import com.vividsolutions.jts.geom.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureSource;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.shapefile.ShapefileDirectoryFactory;
import org.geotools.data.shapefile.ShapefileFeatureLocking;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class SHPUtil {
	/**
     * 读取shap格式的文件
     * @param shpFile	shp文件
     * @param csvFile   csv文件
     * @param encoding  编码
     * @param readLineCount  读取几行，存储在resource表的head列。
	 * @throws Exception 
     * @
     */
	public static String convertSHP2CSV(String shpFile,String csvFile,String encoding,int readLineCount,int model) throws Exception {
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(csvFile, true),encoding);
		ShapefileDataStore shpDataStore;
        StringBuilder res = new StringBuilder();
        StringBuilder head = new StringBuilder();
		try {
			shpDataStore = new ShapefileDataStore(new File(shpFile).toURI().toURL());
			shpDataStore.setStringCharset(Charset.forName(encoding));
			// 文件名称
			String typeName = shpDataStore.getTypeNames()[0];
			FeatureSource<SimpleFeatureType, SimpleFeature> featureSource;
			featureSource = shpDataStore.getFeatureSource(typeName);
			FeatureCollection<SimpleFeatureType, SimpleFeature> result = featureSource.getFeatures();
			FeatureIterator<SimpleFeature> itertor = result.features();
			
			//检验数据与dataType是否匹配      只取一条数据校验
			while (itertor.hasNext()) {
				SimpleFeature featureC = itertor.next();
				Collection<Property> pC = featureC.getProperties();
				Iterator<Property> itC = pC.iterator();
				while (itC.hasNext()) {
					Property proC = itC.next();
                    Object valueC = proC.getValue();
                    if(null != valueC){
                    	CheckGeomtry.checkSHP(valueC,model);
                    }
				}
				break;
			}
			
			
			int lineNO = 0;
			int tempFlag=0;
			while (itertor.hasNext()) {
				tempFlag++;
				SimpleFeature feature = itertor.next();
				Collection<Property> p = feature.getProperties();
				Iterator<Property> it = p.iterator();
				StringBuilder content = new StringBuilder();
				while (it.hasNext()) {
					Property pro = it.next();
					String colum = pro.getName().toString();
                    Object valueObj = pro.getValue();
                    String value = "";
                    Geometry geometry = null;
                    if(null != valueObj){
                        if (valueObj instanceof Polygon) {	//面
                            colum = GdsConstants.GEOM_FIELD;
                            Polygon area = (Polygon) valueObj;
                            Polygon[] polys = new Polygon[1];
                            polys[0] = area;
                            geometry = new MultiPolygon(polys,null);
                        }else if (valueObj instanceof Point) {	//点
                            geometry = (Point) valueObj;
                            colum = GdsConstants.GEOM_FIELD;
                        }else if (valueObj instanceof MultiLineString) {	//共享点，线
                            geometry = (MultiLineString)valueObj;
                            colum = GdsConstants.GEOM_FIELD;
                        }else if (valueObj instanceof MultiPolygon) {	//共享点，面
                            geometry = (MultiPolygon)valueObj;
                            colum = GdsConstants.GEOM_FIELD;
                        }else{
                            value = valueObj.toString();
                        }
                        if(null != geometry){
                            String obj = geometry.toString();
                            if(null != obj){
                                //用双引号替换单引号
                                value = StringUtils.replace(obj,"\"","'");
                                value = "\"" + value + "\"";
//                                if(StringUtils.isNotBlank(value) && value.length() > 50){
//                                    value = value.substring(0,50) + "...";
//                                }
                            }
                        }

                    }
                    //处理头
                    if(tempFlag<=1){
                        head.append(colum);
                        head.append(",");
                    }
					content.append(value);
					content.append(",");
				}
				if(tempFlag==1){
					 head.setLength(head.length()-1);
				}
				content.setLength(content.length()-1);
				if(lineNO==0){
					fw.write(head.toString() + "\n");
                    res.append(head.toString()).append("\n");
				}
				fw.write(content.toString() + "\n");
                if(lineNO <readLineCount){
                    res.append(content.toString()).append("\n");
                }
				lineNO++;
			}
			itertor.close();
			return res.toString();
		} catch (Exception e) {
			GLog.error(e.getMessage(),e,true);
			throw new Exception(e.getMessage());
		}finally{
			fw.flush();
			fw.close();
		}
	}
	
	public static void makeSHPFile(int model,String csvPath,String outPath,Map<String,Long> fieldTypeMap) throws IOException{
		File csvFile = new File(csvPath);
		LineNumberReader lnr = null;
		try {
			lnr = new LineNumberReader(new InputStreamReader(new FileInputStream(csvFile),"GBK"));
			String head = lnr.readLine();
			StringBuilder typeStr = new StringBuilder();
			int geomIndex = makeType(model, head, typeStr,fieldTypeMap);
			makeSHP(outPath, typeStr.toString(), lnr, geomIndex, model);
		} catch (Exception e) {
			GLog.error(e.getMessage(), e,true);
		}finally{
			IOUtils.closeQuietly(lnr);
		}
	}
	/**
	 * 生成shp文件
	 * @param outPath
	 * @param typeStr
	 * @param lnr
	 * @param geomIndex
	 * @param model
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private static void makeSHP(String outPath,String typeStr,LineNumberReader lnr,int geomIndex,int model) throws Exception{
		File newFile = new File(outPath);
		newFile.getParentFile().mkdirs();
		//生成shp解析相关对象.
		Map<String, Serializable> params = new HashMap<String, Serializable>();
		params.put("url", newFile.toURI().toURL());
		params.put("create spatial index", Boolean.FALSE);
		params.put(ShapefileDirectoryFactory.DBFCHARSET.key, "GBK");
		ShapefileDataStore newDataStore = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);
		SimpleFeatureType TYPE = DataUtilities.createType("loc",typeStr);
		newDataStore.createSchema(TYPE);
		newDataStore.setStringCharset(Charset.forName("GBK"));
		newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);
		ShapefileFeatureLocking featureSource = (ShapefileFeatureLocking) newDataStore.getFeatureSource(newDataStore.getTypeNames()[0]);
		FeatureCollection<SimpleFeatureType, SimpleFeature> collection = FeatureCollections.newCollection();
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
		//保存字段类型
		String[] split = StringUtils.split(typeStr, ",");
		//将geom列类型移到对应位置
		String fieldTypes[]=new String[split.length];
		for(int i=1;i<split.length;i++){
			fieldTypes[i-1]=split[i];
		}
		if(model==1){
			fieldTypes[fieldTypes.length-1]= split[0];
		}else{
			fieldTypes[fieldTypes.length-1]=split[split.length-1];
			fieldTypes[fieldTypes.length-2]= split[0];
		}
		//循环解析数据。
		int batchSize = 0;
		String line = null;
		while ((line = lnr.readLine()) != null) {
			List<String>  columnArray = CSVUtil.fromCSVLinetoArray(line);
			List<Object> lineList = new ArrayList<Object>();
			//占位符。占住第一个位置。 
			lineList.add("1");
			for(int j=0;j< columnArray.size();j++){
				String temp= columnArray.get(j);
				String fieldType=fieldTypes[j];
				//获取geom列数据
				if(geomIndex!=-1&&geomIndex==j){
					if(StringUtils.isNotBlank(temp)){
						temp=StringUtils.replace(temp, "'", "\"");
						GeometryJSON gjson = new GeometryJSON();
						Geometry geom = null;
						switch (model) {
						case 1:		//点
							geom=gjson.readPoint(temp);
							break;
						case 2:	//线
						case 4:	//共享点的线
							if(StringUtils.contains(temp,"MultiLineString")){
								geom=gjson.readMultiLine(temp);
							}else if(StringUtils.contains(temp,"LineString")){
								geom=gjson.readLine(temp);
							}
							break;
						case 3:	//面
						case 5:	//共享点的面
							if(StringUtils.contains(temp,"MultiPolygon")){
								geom=gjson.readMultiPolygon(temp);
							}else if(StringUtils.contains(temp,"Polygon")){
								geom=gjson.readPolygon(temp);
							}							
							break;
						default:
							GLog.error("生成shp文件出错!不支持的model：" + model, true);
							break;
						}
						//lineList.add(geom);
						lineList.set(0, geom);
					}else{
						//lineList.add(null);
						lineList.set(0, null);
					}
				}else{
					if(fieldType.contains("Integer")){
						lineList.add(StringUtils.isNotBlank(temp)?Integer.parseInt(temp):0);
					}else{
						lineList.add(temp);
					}
				}
			}
			SimpleFeature feature = featureBuilder.buildFeature(null, lineList.toArray());
			//添加每一行数据
			collection.add(feature);
			batchSize++;
			//4000一批提交。
			if(batchSize>=40000){
				//提交事务
				commitTran(featureSource, collection);
				collection.clear();
				batchSize = 0;
			}
		}
		//提交事务
		commitTran(featureSource, collection);
		collection.clear();
	}
	
	/**
	 * 根据文件头生成shp的TYPE
	 * @param model
	 * @param head
	 * @param typeStr
	 * @return
	 */
	private static int makeType(int model,String head,StringBuilder typeStr,Map<String,Long> fieldTypeMap){
		int geomIndex = 0;
		// 取第一行的文件头
		String fileHead[] =StringUtils.split(head, ",");
		switch (model) {
			case 1:
				typeStr.append("geom:Point,");
				break;
			case 2:
			case 4:
				//typeStr.append("geom:LineString,");
				typeStr.append("geom:MultiLineString,");
				break;
			case 3:
			case 5:
				typeStr.append("geom:MultiPolygon,");
				break;
			default:
				break;
		}
		for(int i=0;i<fileHead.length;i++){
			if(StringUtils.isNotBlank(fileHead[i])){
				if(GdsConstants.GEOM_FIELD.equals(fileHead[i])){
					geomIndex=i;
				}else{
					typeStr.append(fileHead[i]+":" + convertGDSType2model(fieldTypeMap.get(fileHead[i])) + ",");
				}
			}
		}
		typeStr.setLength(typeStr.length()-1);
		return geomIndex;
	}
	/**
	 * 提交事务。
	 * @param featureSource
	 * @param collection
	 * @throws Exception
	 */
	private static void commitTran(ShapefileFeatureLocking featureSource,FeatureCollection<SimpleFeatureType, SimpleFeature> collection) throws Exception{
		Transaction transaction = new DefaultTransaction(System.currentTimeMillis()+"");
		featureSource.setTransaction(transaction);
		try {
			featureSource.addFeatures(collection);
			// 提交事务
			transaction.commit();
		} catch (Exception e) {
			GLog.error(e.getMessage(), e, true);
		} finally {
			transaction.close();
		}
	}
	
	private static String convertGDSType2model(Long gdstype){
		if("char".equals(gdstype) || "varchar".equals(gdstype)){
			return "String";
		}else if("int".equals(gdstype) || "integer".equals(gdstype)){
			return "Integer";
		}else{
			return "String";
		}
	}
	
	public static void main(String args[]) throws Exception{
		
//		Map<String,String> fieldTypeMap=new HashMap<String,String>();
//		fieldTypeMap.put("id", "varchar");
//		fieldTypeMap.put("name", "varchar");
//		fieldTypeMap.put("status", "varchar");
//		fieldTypeMap.put(GdsConstants.GEOM_FIELD, "varchar");
//		SHPUtil.makeSHPFile(1,0,"D:\\8wan.csv","D:\\ffff\\ffff.shp",fieldTypeMap);
//		
//		File out=new File("D:\\广州2007公交站点\\广州2007公交站点.dbf");
//		String charset = CharsetUtil.getCharset(out);
//		System.out.println("charset------shp-----"+charset);
//		
		SHPUtil.convertSHP2CSV("D:\\shp_road\\road3_region.shp","D:\\shp_road\\shp_road.csv","GBK",10,1);
//		SHPUtil.convertSHP2CSV("D:\\shp_area\\yd_area.shp","D:\\shp_area\\yd_area.csv","GBK",10);

//		Map<String,String> fieldTypeMap = new HashMap<String, String>();
//		fieldTypeMap.put("name", "varchar");
//		SHPUtil.makeSHPFile(1,"D:\\500wan_dian.csv","D:\\500wan_dian.shp",fieldTypeMap);

        System.exit(0);
	}
}
