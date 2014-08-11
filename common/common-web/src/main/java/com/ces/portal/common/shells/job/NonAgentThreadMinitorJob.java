package com.ces.portal.common.shells.job;

import com.ces.portal.common.shells.manage.NonAgentThreadMinitorManage;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonAgentThreadMinitorJob implements Job {

    private Logger logger = LoggerFactory.getLogger(NonAgentThreadMinitorJob.class);

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        logger.info("------- Excuting NonAgentThreadMinitorJob  -------------");
        NonAgentThreadMinitorManage.nonAgentThreadHandle();

    }

}
