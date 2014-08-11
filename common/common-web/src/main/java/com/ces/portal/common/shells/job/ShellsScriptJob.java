package com.ces.portal.common.shells.job;

import com.ces.portal.common.shells.ShellsScriptListener;
import com.ces.portal.common.shells.manage.ShellsScriptManage;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellsScriptJob implements Job {
    private Logger logger = LoggerFactory.getLogger(ShellsScriptJob.class);

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        logger.info("------- Excuting ShellsScriptJob  -------------");
        ShellsScriptManage.readFolderShells(ShellsScriptListener.SHELLSCRIPT_FORLDER_PATH);
    }

}
