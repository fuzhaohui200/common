package org.shine.common.map.osm;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * osm 解析类
 * Created by chong.song on 14-2-28.
 */
@SuppressWarnings({"unchecked"})
public class OsmParser {
    private String xml;
    private Element root;
    public OsmParser(String xml){
        this.xml = xml;
    }

    /**
     * 解析xml为root节点
     * @throws Exception
     */
    public void parseInit() throws Exception{
        if(StringUtils.isBlank(xml)){
            throw new Exception("输入xml为空!");
        }
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new StringReader(xml));
        root = doc.getRootElement();
    }

    /**
     * 获取指定节点的子node节点
     * @param pEleName 指定的父节点
     * @return  指定节点的子node节点
     * @throws Exception
     */
    public Map<String,OsmNode> getNodes(String pEleName) throws Exception{
        Element parentEle = root.element(pEleName);
        if(null != parentEle){
            return getNode(parentEle);
        }else{
            return null;
        }
    }

    /**
     * 获取指定节点的所有way节点
     * @param pEleName 指定节点
     * @return  指定节点的所有way节点
     * @throws Exception
     */
    public Map<String,OsmWay> getWay(String pEleName) throws Exception{
        Element parentEle = root.element(pEleName);
        if(null != parentEle){
            return getWay(parentEle);
        }else{
            return null;
        }
    }

    /**
     * 获取指定节点的关系节点
     * @param pEleName 指定节点
     * @return  指定节点的关系节点
     * @throws Exception
     */
    public List<OsmRelation> getRelation(String pEleName) throws Exception{
        Element parentEle = root.element(pEleName);
        if(parentEle != null){
            return getRelation(parentEle);
        }else{
            return null;
        }
    }

    /**
     * 根据传进来的ele，获取他子节点的所有关系节点
     * @param parentEle 传进来的ele
     * @return  子节点的所有关系节点
     * @throws Exception
     */
    private List<OsmRelation> getRelation(Element parentEle) throws Exception{
        List<OsmRelation> relList = new ArrayList<OsmRelation>();
        List<Element> relationEleList = (List<Element>)parentEle.elements("relation");
        OsmRelation osmRelation;
        for(Element oneRel:relationEleList){
            osmRelation = new OsmRelation();
            osmRelation.setId(oneRel.attributeValue("id"));
            osmRelation.setVersion(oneRel.attributeValue("version"));
            osmRelation.setChangeset(oneRel.attributeValue("changeset"));
            List<OsmRelationNumber> numberList = new ArrayList<OsmRelationNumber>();
            List<Element> memEles = (List<Element>)oneRel.elements("member");
            OsmRelationNumber number;
            for(Element oneMem: memEles){
                number = new OsmRelationNumber();
                number.setRef(oneMem.attributeValue("ref"));
                number.setRole(oneMem.attributeValue("role"));
                number.setType(oneMem.attributeValue("type"));
                numberList.add(number);
            }
            osmRelation.setNumberList(numberList);

            Map<String,String> tagMap = new HashMap<String, String>();
            List<Element> tagEles = (List<Element>)oneRel.elements("tag");
            for(Element oneTag: tagEles){
                tagMap.put(oneTag.attributeValue("k"), oneTag.attributeValue("v"));
            }
            osmRelation.setTagMap(tagMap);
            relList.add(osmRelation);
        }
        return relList;
    }

    /**
     * 根据传进来的ele，获取他子节点的所有way节点
     * @param parentEle     父节点
     * @return  返回的所有way节点
     * @throws Exception
     */
    private Map<String,OsmWay> getWay(Element parentEle) throws Exception{
        Map<String,OsmWay> wayMap = new HashMap<String, OsmWay>();
        List<Element> wayEleList = (List<Element>)parentEle.elements("way");
        OsmWay osmWay;
        for(Element oneWay:wayEleList){
            osmWay = new OsmWay();
            osmWay.setId(oneWay.attributeValue("id"));
            osmWay.setChangeset(oneWay.attributeValue("changeset"));
            osmWay.setVersion(oneWay.attributeValue("version"));
            List<Element> ndEle = (List<Element>)oneWay.elements("nd");
            List<String> ndList = new ArrayList<String>();
            for(Element oneND:ndEle){
                ndList.add(oneND.attributeValue("ref"));
            }
            osmWay.setNodeList(ndList);
            Map<String,String> tagMap = new HashMap<String,String>();
            List<Element> tagEle = (List<Element>)oneWay.elements("tag");
            for(Element oneTag:tagEle){
                tagMap.put(oneTag.attributeValue("k"), oneTag.attributeValue("v"));
            }
            osmWay.setTagMap(tagMap);
            wayMap.put(osmWay.getId(),osmWay);
        }
        return wayMap;
    }

    /**
     * 根据传进来的ele，获取他子节点的所有node节点
     * @param nodeParentEle     父节点
     * @return  返回的所有node节点
     * @throws Exception
     */
    private Map<String,OsmNode> getNode(Element nodeParentEle) throws Exception{
        Map<String,OsmNode> nodeMap = new HashMap<String, OsmNode>();
        List<Element> nodeEleList = (List<Element>)nodeParentEle.elements("node");
        OsmNode osmNode;
        for(Element oneNode:nodeEleList){
            osmNode = new OsmNode();
            osmNode.setNodeId(oneNode.attributeValue("id"));
            if(StringUtils.isNotBlank(oneNode.attributeValue("changeset"))){
                if(StringUtils.isNotBlank(oneNode.attributeValue("changeset"))){
                    osmNode.setChangeSet(Integer.parseInt(oneNode.attributeValue("changeset")));
                }
            }
            if(StringUtils.isNotBlank(oneNode.attributeValue("lon"))){
                if(StringUtils.isNotBlank(oneNode.attributeValue("lon"))){
                    osmNode.setLon(Double.parseDouble(oneNode.attributeValue("lon")));
                }
            }
            if(StringUtils.isNotBlank(oneNode.attributeValue("lat"))){
                if(StringUtils.isNotBlank(oneNode.attributeValue("lat"))){
                    osmNode.setLat(Double.parseDouble(oneNode.attributeValue("lat")));
                }
            }
            if(StringUtils.isNotBlank(oneNode.attributeValue("version"))){
                if(StringUtils.isNotBlank(oneNode.attributeValue("version"))){
                    osmNode.setVersion(Integer.parseInt(oneNode.attributeValue("version")));
                }
            }
            //获取tag
            List<Element> tagEle = (List<Element>)oneNode.elements("tag");
            Map<String,String> tagMap = new HashMap<String,String>();
            for(Element oneTag:tagEle){
                tagMap.put(oneTag.attributeValue("k"), oneTag.attributeValue("v"));
            }
            osmNode.setTagMap(tagMap);
            nodeMap.put(osmNode.getNodeId(), osmNode);
        }
        return nodeMap;
    }

    public static void main(String[] args) throws Exception{
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <osmChange> <create> <node id=\"-13\" lon=\"121.44192681222536\" lat=\"31.204386854364294\" version=\"0\" changeset=\"2\"/> <node id=\"-16\" lon=\"121.44703373818972\" lat=\"31.204184968127002\" version=\"0\" changeset=\"2\"/> <node id=\"-19\" lon=\"121.44613251596073\" lat=\"31.201927483581972\" version=\"0\" changeset=\"2\"/> <node id=\"-22\" lon=\"121.44248471170046\" lat=\"31.201358515562514\" version=\"0\" changeset=\"2\"/> <node id=\"-25\" lon=\"121.44031748681643\" lat=\"31.202900227430586\" version=\"0\" changeset=\"2\"/> <node id=\"-28\" lon=\"121.44282803445438\" lat=\"31.203542599959917\" version=\"0\" changeset=\"2\"/> <node id=\"-31\" lon=\"121.445102547699\" lat=\"31.203359065396675\" version=\"0\" changeset=\"2\"/> <node id=\"-34\" lon=\"121.4447806826172\" lat=\"31.202276204225054\" version=\"0\" changeset=\"2\"/> <node id=\"-37\" lon=\"121.44272074609377\" lat=\"31.202386326266325\" version=\"0\" changeset=\"2\"/> <way id=\"-6\" version=\"0\" changeset=\"2\"> <nd ref=\"-13\"/> <nd ref=\"-16\"/> <nd ref=\"-19\"/> <nd ref=\"-22\"/> <nd ref=\"-25\"/> <nd ref=\"-13\"/> </way> <way id=\"-12\" version=\"0\" changeset=\"2\"> <nd ref=\"-28\"/> <nd ref=\"-31\"/> <nd ref=\"-34\"/> <nd ref=\"-37\"/> <nd ref=\"-28\"/> <tag k=\"name\" v=\"\"/> </way> <relation id=\"-1\" version=\"0\" changeset=\"2\"> <member type=\"way\" role=\"outer\" ref=\"-6\"/> <member type=\"way\" role=\"inner\" ref=\"-12\"/> <tag k=\"type\" v=\"multipolygon\"/> <tag k=\"name\" v=\"zzh\"/> </relation> </create> <modify/> <delete/> </osmChange>";
        OsmParser parser = new OsmParser(xml);
        parser.parseInit();
//        List<OsmNode> createNodes = parser.getCreateNodes();
//        List<OsmWay> createWays = parser.getDeleteWay();
        List<OsmRelation> createRel = parser.getRelation("create");
        System.out.println(createRel);
    }
}
