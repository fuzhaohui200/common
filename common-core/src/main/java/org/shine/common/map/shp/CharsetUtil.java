package org.shine.common.map.shp;

import com.mapabc.gds.util.log.GLog;
import info.monitorenter.cpdetector.io.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;

public class CharsetUtil {
	private static int lenght = 1024000;
	public static String getCharset(File file) throws Exception {
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		// 可以增加多个编码检测的实例,但一般还是JChardetFacade最有效
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		detector.add(new ParsingDetector(false));
		detector.add(new ByteOrderMarkDetector());
		Charset charset = null;
		String chartSetName = "UTF-8";
//		用url方式，如果文件过大，获取编码时间太长。
//		charset = detector.detectCodepage(file.toURI().toURL());
		FileInputStream fis  = null;
		ByteArrayInputStream bis = null;
		byte[] fs = new byte[lenght];
		try {
			fis = new FileInputStream(file);
			fis.read(fs);
			bis = new ByteArrayInputStream(fs);
			charset = detector.detectCodepage(bis,lenght);
		} catch (Exception e) {
			GLog.error(e.getMessage(), e,true);
		}finally{
			if(null != fis){
				fis.close();
			}
			if(null != bis){
				bis.close();
			}
		}
		if(null != charset){
			chartSetName = charset.name();
		}
        //把UTF-16LE和UTF-16BE统一当作UTF-8处理.
        if("UTF-8".equals(chartSetName) || "UTF-16BE".equals(chartSetName)){
            return "UTF-8";
        }else{
            return "GBK";
        }
//		if(chartSetName.startsWith("GB") || chartSetName.startsWith("windows") || chartSetName.contains("TW")){
//			return "GBK";
//		}
//		//把UTF-16LE和UTF-16BE统一当作UTF-8处理.
//		if(chartSetName.startsWith("UTF-")){
//			return "UTF-8";
//		}
//		return chartSetName;
	}

	public static void main(String[] args) throws Exception {
		 String str = CharsetUtil.getCharset(new File(
//		 "D:\\工行自助设备\\中国工商银行离行式自助机具.shp"));
//		 "D:\\广州2007公交站点\\广州2007公交站点.zip"));
//		 "D:\\1dianSHP\\1dianSHP.zip"));
//		 "D:\\shp_point\\yd_point.shp"));
//		 "D:\\gds测试数据\\T_2013_11_19.csv"));
	 "D:\\gds测试数据\\T_2013_46.csv"));
//		 "D:\\layerData20130625153958.zip"));
//		 "D:\\test.zip"));
		 System.out.println("文件编码：" + str);
	}
}
