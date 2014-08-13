package com.ces.portal.common.shells.manage;

import com.ces.portal.common.shells.ShellsScriptListener;
import com.ces.portal.common.shells.bean.ServerShellBean;
import com.ces.portal.common.shells.job.ShellCommandJob;
import com.ces.portal.common.shells.pojo.ErrorShellScriptPojo;
import com.ces.portal.common.shells.pojo.ShellScriptPojo;
import com.ces.portal.common.shells.service.ErrorShellScriptService;
import com.ces.portal.common.shells.service.ShellScriptService;
import com.ces.portal.common.shells.service.impl.ErrorShellScriptServiceImpl;
import com.ces.portal.common.shells.service.impl.ShellScriptServiceImpl;
import com.ces.portal.common.utils.ConfigXMLUtil;
import com.ces.portal.common.utils.DateUtils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class ShellsScriptManage {

    private static Logger logger = LoggerFactory.getLogger(ShellsScriptManage.class);
    private static ShellScriptService shellScriptService = new ShellScriptServiceImpl();
    private static ErrorShellScriptService errorShellScriptService = new ErrorShellScriptServiceImpl();

    /**
     * Description:  读取文件夹里的相应用shell脚本
     *
     * @param folderPath
     */
    public static void readFolderShells(String folderPath) {
        File rootFolder = new File(folderPath);
        if (rootFolder.exists()) {
            File[] rootFiles = rootFolder.listFiles();
            for (File rootFile : rootFiles) {
                if (rootFile.isDirectory() && !rootFile.isHidden()) {
                    readServerFolderShells(rootFile.getAbsolutePath());
                }
            }
        } else {
            logger.error("文件夹：[" + folderPath + "]  不存在，请检查！");
        }
    }

    /**
     * 遍历文件夹
     *
     * @param folderName
     */
    private static void readServerFolderShells(String folderName) {
        File rootFolder = new File(folderName);
        if (rootFolder.exists()) {
            File[] rootFiles = rootFolder.listFiles();
            for (File rootFile : rootFiles) {
                if (rootFile.isFile()) {
                    readShellScript(folderName, rootFile);
                }
            }
        } else {
            logger.error("文件夹：[" + folderName + "]  不存在，请检查！");
        }
    }


    /**
     * Description: 读取配置文件某model块的数据信息
     *
     * @param serverName
     * @param shellScriptName
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, ServerShellBean> readConfigXMLInfo() {
        Map<String, ServerShellBean> results = new HashMap<String, ServerShellBean>();
        try {
            File file = new File(ShellsScriptListener.TIVOLICONF_PATH);
            //File file = new File("src/main/resources/TivoliConfig.xml");
            Document document = ConfigXMLUtil.parseXmlFile(file);
            Element root = document.getRootElement();
            // iterate through child elements of root
            for (Iterator iterator = root.elementIterator("Server"); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                Element ipAddress = element.element("ipAddress");
                if (!ipAddress.getTextTrim().equals("")) {
                    Element description = element.element("description");
                    Element label = element.element("label");
                    ServerShellBean serverShellBean = new ServerShellBean();
                    serverShellBean.setIpAddress(ipAddress.getTextTrim());
                    serverShellBean.setDescription(description.getTextTrim());
                    serverShellBean.setLabel(label.getTextTrim());
                    Element name = element.element("name");
                    if (name != null) {
                        serverShellBean.setName(name.getText());
                        Element telnetCmdPrompt = element.element("telnetCmdPrompt");
                        if (telnetCmdPrompt != null) {
                            serverShellBean.setTelnetCmdPrompt(telnetCmdPrompt.getTextTrim());
                        }
                        Element accessAuthority = element.element("accessAuthority");
                        if (accessAuthority != null) {
                            for (Iterator<Element> authorityIterator = accessAuthority.elementIterator(); authorityIterator.hasNext(); ) {
                                Element authority = authorityIterator.next();
                                if (authority != null) {
                                    if (authority.getName().equals("userName")) {
                                        serverShellBean.setUsername(authority.getTextTrim());
                                    } else if (authority.getName().equals("password")) {
                                        serverShellBean.setPassword(authority.getTextTrim());
                                    }
                                }
                            }
                        }
                        Map<String, String> shellScriptFiles = new HashMap<String, String>();
                        for (Iterator SubElements = element.elementIterator("SubElements"); SubElements.hasNext(); ) {
                            Element SubElement = (Element) SubElements.next();
                            Element minitorNode = SubElement.element("monitorNodeName");
                            if (minitorNode != null && SubElement.element("shellSciptFileName") != null) {
                                shellScriptFiles.put(minitorNode.getTextTrim(), SubElement.element("shellSciptFileName").getTextTrim());
                            }
                        }
                        serverShellBean.setShellScript(shellScriptFiles);
                    }
                    results.put(ipAddress.getTextTrim(), serverShellBean);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return results;
    }

    // 读取shell脚本，向数据库里逐行插入脚本
    private static void insertShellScriptToDB(String fileName, String groupId, int sequence, String shellCommand) {
        ShellScriptPojo shellScriptPojo = new ShellScriptPojo();
        shellScriptPojo.setShellCommand(shellCommand);
        shellScriptPojo.setFileName(fileName);
        shellScriptPojo.setGroupId(groupId);
        shellScriptPojo.setSequence((long) sequence);
        shellScriptPojo.setDateTime(new Date());
        shellScriptService.add(shellScriptPojo);
    }

    // 移动shell脚本
    private static void moveShellScript(String folderName, File shellScriptFile, boolean flag) {
        try {
            String targetFolder;
            if (flag) {
                targetFolder = ShellsScriptListener.SUCCES_FOLDER_PATH + "\\" + folderName.substring(folderName.lastIndexOf("\\") + 1);
            } else {
                targetFolder = ShellsScriptListener.FAILED_FOLDER_PATH + "\\" + folderName.substring(folderName.lastIndexOf("\\") + 1);
            }
            File targetPath = new File(targetFolder);
            if (!targetPath.exists()) {
                targetPath.mkdir();
            }
            String shellScriptName = shellScriptFile.getName();
            File targetFile = new File(targetFolder, shellScriptName);
            if (targetFile.exists()) {
                File bakFolder = new File(targetFolder, "bak");
                if (!bakFolder.exists()) {
                    bakFolder.mkdir();
                }
                String bakFileName = shellScriptName.substring(0, shellScriptName.lastIndexOf(".")) + "_"
                        + DateUtils.format(new Date(), DateUtils.FORMAT_ALL_FULL) + shellScriptName.substring(shellScriptName.lastIndexOf("."));
                targetFile.renameTo(new File(bakFolder, bakFileName));
                targetFile.delete();
            }
            shellScriptFile.renameTo(targetFile);
            shellScriptFile.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 读取文件夹下脚本数据
     *
     * @param folderName 　文件夹
     * @param file       　文件
     */
    private static void readShellScript(String folderName, File file) {
        String fileName = file.getName().toLowerCase().trim();
        if (fileName.indexOf(".") != -1 && fileName.substring(fileName.indexOf(".")).equals(ShellsScriptListener.SHELL_SUFFIX)) {
            InputStreamReader reader = null;
            BufferedReader bufferReader = null;
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                reader = new InputStreamReader(fileInputStream, "GB2312");
                bufferReader = new BufferedReader(reader);
                String shellCommand = "";
                int sequence = 1;
                String groupId = folderName.substring(folderName.lastIndexOf("\\") + 1);
                while ((shellCommand = bufferReader.readLine()) != null) {
                    if (!shellCommand.trim().equals("") && !shellCommand.trim().startsWith("#")) {
                        insertShellScriptToDB(file.getName(), groupId, sequence++, shellCommand);
                    }
                }
                bufferReader.close();
                reader.close();
                fileInputStream.close();
                moveShellScript(folderName, file, true);

                // 开启执行shell脚本JOB
                JobDetail excuteShellCommandJob = JobBuilder.newJob(ShellCommandJob.class).withIdentity("excuteShellCommandJob", "excuteShellCommandGroup").build();
                excuteShellCommandJob.getJobDataMap().put("folderName", groupId);
                SimpleTrigger excuteShellCommandTriger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("excuteShellCommandTriger", "excuteShellCommandGroup")
                        .startAt(new Date()).withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0).withIntervalInSeconds(0)).build();
                ShellsScriptListener.getSchedulerFactory().getScheduler().scheduleJob(excuteShellCommandJob, excuteShellCommandTriger);

            } catch (Exception e) {
                moveShellScript(folderName, file, false);
                ErrorShellScriptPojo errorShellScript = new ErrorShellScriptPojo();
                errorShellScript.setGroupId(folderName.substring(folderName.lastIndexOf("\\") + 1));
                errorShellScript.setFileName(file.getName());
                errorShellScript.setDateTime(new Date());
                errorShellScript.setErrorMessage("脚本操作错误：" + e.getMessage());
                errorShellScriptService.addErrorShellScript(errorShellScript);
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 获取IP关联所有Server数据
     *
     * @return
     */
    public static Map<String, ServerShellBean> getIp_ServerShellScriptMap() {
        Map<String, ServerShellBean> ip_ServerShellScriptMap = readConfigXMLInfo();
        return ip_ServerShellScriptMap;
    }

    /**
     * 获取TivoliConfig.xml里所有Server节点数据
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ServerShellBean> getAllServerInfo() {
        List<ServerShellBean> serverShellList = new ArrayList<ServerShellBean>();
        try {
            File file = new File(ShellsScriptListener.TIVOLICONF_PATH);
            //File file = new File("src/main/resources/TivoliConfig.xml");
            Document document = ConfigXMLUtil.parseXmlFile(file);
            Element root = document.getRootElement();
            // iterate through child elements of root
            for (Iterator iterator = root.elementIterator("Server"); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                Element ipAddress = element.element("ipAddress");
                Element description = element.element("description");
                Element label = element.element("label");
                ServerShellBean serverShellBean = new ServerShellBean();
                if (!ipAddress.getTextTrim().equals("")) {
                    serverShellBean.setIpAddress(ipAddress.getTextTrim());
                }
                serverShellBean.setDescription(description.getTextTrim());
                serverShellBean.setLabel(label.getTextTrim());
                Element name = element.element("name");
                if (name != null) {
                    serverShellBean.setName(name.getText());
                    Element telnetCmdPrompt = element.element("telnetCmdPrompt");
                    if (telnetCmdPrompt != null) {
                        serverShellBean.setTelnetCmdPrompt(telnetCmdPrompt.getTextTrim());
                    }
                    Element accessAuthority = element.element("accessAuthority");
                    if (accessAuthority != null) {
                        for (Iterator<Element> authorityIterator = accessAuthority.elementIterator(); authorityIterator.hasNext(); ) {
                            Element authority = authorityIterator.next();
                            if (authority != null) {
                                if (authority.getName().equals("userName")) {
                                    serverShellBean.setUsername(authority.getTextTrim());
                                } else if (authority.getName().equals("password")) {
                                    serverShellBean.setPassword(authority.getTextTrim());
                                }
                            }
                        }
                    }
                    Map<String, String> shellScriptFiles = new HashMap<String, String>();
                    for (Iterator SubElements = element.elementIterator("SubElements"); SubElements.hasNext(); ) {
                        Element SubElement = (Element) SubElements.next();
                        Element minitorNode = SubElement.element("monitorNodeName");
                        if (minitorNode != null && SubElement.element("shellSciptFileName") != null) {
                            shellScriptFiles.put(minitorNode.getTextTrim(), SubElement.element("shellSciptFileName").getTextTrim());
                        }
                    }
                    serverShellBean.setShellScript(shellScriptFiles);
                }
                serverShellList.add(serverShellBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return serverShellList;
    }

    public static ServerShellBean getServerShellScriptModelByIP(String ip) {
        Map<String, ServerShellBean> ip_ServerShellScriptMap = getIp_ServerShellScriptMap();
        return ip_ServerShellScriptMap.get(ip);
    }

    /**
     * 　备份TivoliConfig.xml
     */
    private static void bakTivoliConfig() {
        String tivoliConfigPath = ShellsScriptListener.TIVOLICONF_PATH;
        File tivoliConfigFile = new File(tivoliConfigPath);
        String tivoliConfigFolder = tivoliConfigPath.substring(0, tivoliConfigPath.lastIndexOf("/"));
        String tivoliConfigFileName = tivoliConfigPath.substring(tivoliConfigPath.lastIndexOf("/") + 1);
        File bakFolder = new File(tivoliConfigFolder, "bak");
        if (!bakFolder.exists()) {
            bakFolder.mkdir();
        }
        String bakFileName = tivoliConfigFileName.substring(0, tivoliConfigFileName.lastIndexOf(".")) + "_"
                + DateUtils.format(new Date(), DateUtils.FORMAT_ALL_FULL) + tivoliConfigFileName.substring(tivoliConfigFileName.lastIndexOf("."));
        tivoliConfigFile.renameTo(new File(bakFolder, bakFileName));
    }

    /**
     * 向TivoliConfig.xml里添加IP、 telnetCmdPrompt、username、password
     *
     * @param description
     * @param label
     * @param ipAddress
     * @param telnetCmdPrompt
     * @param username
     * @param password
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void writeTivoliConfig(String description, String label, String ipAddress, String telnetCmdPrompt, String username, String password) {
        try {
            File file = new File(ShellsScriptListener.TIVOLICONF_PATH);
            //File file = new File("src/main/resources/TivoliConfig.xml");
            Document document = ConfigXMLUtil.parseXmlFile(file);
            Element root = document.getRootElement();
            // iterate through child elements of root
            for (Iterator iterator = root.elementIterator("Server"); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                Element descriptionElement = element.element("description");
                Element labelElement = element.element("label");
                if (descriptionElement.getTextTrim().equals(description) && labelElement.getTextTrim().equals(label)) {
                    Element ipAddressElement = element.element("ipAddress");
                    ipAddressElement.setText(ipAddress);
                    Element telnetCmdPromptElement = element.element("telnetCmdPrompt");
                    telnetCmdPromptElement.setText(telnetCmdPrompt);
                    Element accessAuthority = element.element("accessAuthority");
                    if (accessAuthority != null) {
                        for (Iterator<Element> authorityIterator = accessAuthority.elementIterator(); authorityIterator.hasNext(); ) {
                            Element authority = authorityIterator.next();
                            if (authority != null) {
                                if (authority.getName().equals("userName")) {
                                    authority.setText(username);
                                } else if (authority.getName().equals("password")) {
                                    authority.setText(password);
                                }
                            }
                        }
                    }
                }
            }
            bakTivoliConfig();
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            ConfigXMLUtil.write(document, out);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public static void main(String[] args) {
        //readConfigXMLInfo();
        writeTivoliConfig("EPS Patrol", "EPS 183.66", "10.10.10.10", "#linux", "root", "root");
    }
}
