package com.ces.portal.common.shells.job;

import com.ces.portal.common.shells.manage.ExecuteShellCommandManage;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCommandJob implements Job {

    private Logger logger = LoggerFactory.getLogger(ShellCommandJob.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        String folderName = jobExecutionContext.getJobDetail().getJobDataMap().getString("folderName");

        if (folderName != null && !folderName.equals("")) {
            logger.info("------- Excuting ExcuteShellCommandJob  ------------- " + folderName);
            ExecuteShellCommandManage.excute(folderName);
        } else {
            logger.error("----Excuting ExcuteShellCommandJob's   folderName is Null ! ");
        }
    }

}
