package com.ces.portal.common.shells.manage;

import com.ces.portal.common.shells.ShellsScriptListener;
import com.ces.portal.common.shells.pojo.ShellScriptPojo;
import com.ces.portal.common.shells.service.ShellScriptService;
import com.ces.portal.common.shells.service.impl.ShellScriptServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExecuteShellCommandThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ExecuteShellCommandThread.class);

    private String fileName;
    private String groupId;
    private String webserviceURL;
    private String host;
    private String userName;
    private String password;
    private String strCmdPrompt;

    private ShellScriptService shellScriptService = new ShellScriptServiceImpl();
    private List<ShellScriptPojo> shellScriptList = new ArrayList<ShellScriptPojo>();

    public ExecuteShellCommandThread(String fileName, String groupId, String webserviceURL, String host, String userName, String password, String strCmdPrompt) {
        this.fileName = fileName;
        this.groupId = groupId;
        this.webserviceURL = webserviceURL;
        this.host = host;
        this.userName = userName;
        this.password = password;
        this.strCmdPrompt = strCmdPrompt;
        this.shellScriptList = shellScriptService.queryAllShellScriptsByFileName(fileName, groupId);
    }

    @Override
    public void run() {
        if (shellScriptList.size() > 0) {
            String sendCommand = "> " + ShellsScriptListener.UPLOAD_TEMP_FOLDER_PATH + "/" + fileName;
            // 调用执行命令位置
            while (!excuteCommmand(null, webserviceURL, host, userName, password, sendCommand, strCmdPrompt)) {
            }
            for (ShellScriptPojo shellScriptPojo : shellScriptList) {
                String command = shellScriptPojo.getShellCommand();

					 /*
					  * Replace with special character with escaped character.
					  * such as: " with \" and $ with \$ and ` with \`
					  */
                command = command.replace("\"", "\\\"").replace("$", "\\$").replace("`", "\\`");
                sendCommand = "echo \"" + command + "\" >> " + ShellsScriptListener.UPLOAD_TEMP_FOLDER_PATH + "/" + fileName;
                // 调用执行命令位置
                while (!excuteCommmand(shellScriptPojo, webserviceURL, host, userName, password, sendCommand, strCmdPrompt)) {
                }
            }
            sendCommand = "chmod 777 " + ShellsScriptListener.UPLOAD_TEMP_FOLDER_PATH + "/" + fileName;

            while (!excuteCommmand(null, webserviceURL, host, userName, password, sendCommand, strCmdPrompt)) {
            }
        } else {
            logger.info("--配置文件中[" + groupId + "]模块，Shell脚本文件[" + fileName + "]在数据库里没有找到相关数据，请向"
                    + ShellsScriptListener.SHELLSCRIPT_FORLDER_PATH + "\\" + groupId + "文件夹里添加" + fileName + "脚本文件--");
        }
    }

    private boolean excuteCommmand(ShellScriptPojo shellScriptPojo, String webserviceURL, String host, String userName, String password, String sendCommand, String strCmdPrompt) {
        boolean result = true;
        try {
//			String strThreadID = WSUtils.ExecuteTelnetCmd(webserviceURL, host, userName, password, sendCommand, strCmdPrompt);
//			if(strThreadID != null) {
//				while(true) {
//					if(WSUtils.GetCMDStatus(webserviceURL, strThreadID).trim().equals("Complete")) {
//						if(shellScriptPojo != null) {
//							shellScriptPojo.setLastExcuteTime(new Date());
//							shellScriptService.update(shellScriptPojo);
//							logger.info("执行完毕命令：" + webserviceURL + " : " + host + ":  [" + sendCommand  + "]");
//						}
//						break;
//					} else {
//						Thread.sleep(500L); // 线程休眠1秒钟，等待命令执行完毕
//					}
//				}
//			} else {
//				logger.info("－－－－－－－－－－－－－ 指令：[" + sendCommand + "] 不存在 threadId－－－－－－－－－－－－－－－");
//			}
        } catch (Exception e) {
            logger.info("ERROR指令：[" + sendCommand + "]----error:" + e.getMessage());
            result = false;
        }
        return result;
    }

}
