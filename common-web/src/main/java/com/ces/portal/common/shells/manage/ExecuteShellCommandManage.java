package com.ces.portal.common.shells.manage;

import com.ces.portal.common.shells.ShellsScriptListener;
import com.ces.portal.common.shells.bean.ServerShellBean;
import com.ces.portal.common.utils.ThreadPoolExecuteTaskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExecuteShellCommandManage {

    private static Logger logger = LoggerFactory.getLogger(ExecuteShellCommandManage.class);

    public static void excute(String folderName) {
        List<ServerShellBean> serverShellList = ShellsScriptManage.getAllServerInfo();
        for (ServerShellBean serverShellBean : serverShellList) {
            if (serverShellBean.getName() != null && serverShellBean.getName().equals(folderName)) {
                Map<String, String> shellScriptFileMap = serverShellBean.getShellScript();
                for (Iterator<String> iterator = shellScriptFileMap.keySet().iterator(); iterator.hasNext(); ) {
                    String minitorName = iterator.next();
                    String shFileName = shellScriptFileMap.get(minitorName);
                    logger.info("**********Excuting Thread ShellScriptFile: [" + shFileName + ", " + serverShellBean.getName() + "]**************");
                    // 为每个Shell脚本文件开一个线程
                    String ipAddress = serverShellBean.getIpAddress();
                    if (ipAddress != null && !ipAddress.equals("")) {
                        ExecuteShellCommandThread excuteShellCommandThread = new ExecuteShellCommandThread(shFileName, serverShellBean.getName(),
                                ShellsScriptListener.WEBSERVICE_URL, ipAddress, serverShellBean.getUsername(), serverShellBean.getPassword(), serverShellBean.getTelnetCmdPrompt());
                        ThreadPoolExecuteTaskUtil.excuteTask(excuteShellCommandThread);
                    } else {
                        logger.error("Server description：" + serverShellBean.getDescription() + " label： " + serverShellBean.getLabel() + "ipAddress 为空!");
                    }
                }
            }
        }
    }
}
