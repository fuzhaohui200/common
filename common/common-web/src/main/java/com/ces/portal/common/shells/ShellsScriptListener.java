package com.ces.portal.common.shells;

import com.ces.portal.common.shells.job.NonAgentThreadMinitorJob;
import com.ces.portal.common.shells.job.ShellsScriptJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class ShellsScriptListener implements ServletContextListener {

    public static final String SHELL_SUFFIX = ".sh";
    public static String TIVOLICONF_PATH = null;
    public static String SHELLSCRIPT_FORLDER_PATH = null;
    public static String SUCCES_FOLDER_PATH = null;
    public static String FAILED_FOLDER_PATH = null;
    public static String UPLOAD_TEMP_FOLDER_PATH = null;
    public static String WEBSERVICE_URL = null;
    public static String HIBERANTE_CONFG = null;
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Logger logger = LoggerFactory.getLogger(ShellsScriptListener.class);

    public static SchedulerFactory getSchedulerFactory() {
        return schedulerFactory;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            String initTivoliConfig = event.getServletContext().getInitParameter("tivoliConfig");
            if (initTivoliConfig == null || initTivoliConfig.equals("")) {
                initTivoliConfig = "WEB-INF/TivoliConfig.xml";
            }
            TIVOLICONF_PATH = event.getServletContext().getRealPath(initTivoliConfig);

            String shellScriptIntervalInSeconds = event.getServletContext().getInitParameter("shellScriptIntervalInSeconds");
            if (shellScriptIntervalInSeconds == null || shellScriptIntervalInSeconds.equals("")) {
                shellScriptIntervalInSeconds = "45";
            }

            String errorShellScriptIntervalInSeconds = event.getServletContext().getInitParameter("errorShellScriptIntervalInSeconds");
            if (errorShellScriptIntervalInSeconds == null || errorShellScriptIntervalInSeconds.equals("")) {
                errorShellScriptIntervalInSeconds = "60";
            }

            String nonAgentThreadMinitorIntervalInSeconds = event.getServletContext().getInitParameter("nonAgentResponseMinitorIntervalInSeconds");
            if (nonAgentThreadMinitorIntervalInSeconds == null || nonAgentThreadMinitorIntervalInSeconds.equals("")) {
                nonAgentThreadMinitorIntervalInSeconds = "60";
            }

            String excuteShellCommandIntervalInSeconds = event.getServletContext().getInitParameter("excuteShellCommandIntervalInSeconds");
            if (excuteShellCommandIntervalInSeconds == null || excuteShellCommandIntervalInSeconds.equals("")) {
                excuteShellCommandIntervalInSeconds = "120";
            }

            String shellScriptFolder = event.getServletContext().getInitParameter("shellScriptFolder");
            if (shellScriptFolder == null || shellScriptFolder.equals("")) {
                shellScriptFolder = "shellscript/shellscripts";
            }

            String successShellScriptFolder = event.getServletContext().getInitParameter("successShellScriptFolder");
            if (successShellScriptFolder == null || successShellScriptFolder.equals("")) {
                successShellScriptFolder = "shellscript/success";
            }
            String failedShellScriptFolder = event.getServletContext().getInitParameter("failedShellScriptFolder");
            if (failedShellScriptFolder == null || failedShellScriptFolder.equals("")) {
                failedShellScriptFolder = "shellscript/failed";
            }
            String uploadTempShellScriptFolder = event.getServletContext().getInitParameter("uploadTempShellScriptFolder");
            if (uploadTempShellScriptFolder == null || uploadTempShellScriptFolder.equals("")) {
                uploadTempShellScriptFolder = "/tmp";
            }
            UPLOAD_TEMP_FOLDER_PATH = uploadTempShellScriptFolder;

            String webserviceURL = event.getServletContext().getInitParameter("webserviceURL");
            if (webserviceURL == null || webserviceURL.equals("")) {
                webserviceURL = "http://182.2.183.169:8080/ControlAIX";
            }
            WEBSERVICE_URL = webserviceURL;

            File shellScriptFolderFile = null;
            if (shellScriptFolder.contains("/")) {
                shellScriptFolderFile = new File(event.getServletContext().getRealPath(shellScriptFolder.substring(0, shellScriptFolder.indexOf("/"))),
                        event.getServletContext().getRealPath(shellScriptFolder.substring(shellScriptFolder.indexOf("/"))));
                if (!shellScriptFolderFile.exists()) {
                    shellScriptFolderFile.mkdirs();
                }
            } else {
                shellScriptFolderFile = new File(event.getServletContext().getRealPath(shellScriptFolder));
                if (!shellScriptFolderFile.exists()) {
                    shellScriptFolderFile.mkdir();
                }
            }
            SHELLSCRIPT_FORLDER_PATH = event.getServletContext().getRealPath(shellScriptFolder);

            File successShellScriptFolderFile = new File(event.getServletContext().getRealPath(successShellScriptFolder));
            if (!successShellScriptFolderFile.exists()) {
                successShellScriptFolderFile.mkdir();
            }
            SUCCES_FOLDER_PATH = event.getServletContext().getRealPath(successShellScriptFolder);

            File failedShellScriptFolderFile = new File(event.getServletContext().getRealPath(failedShellScriptFolder));
            if (!failedShellScriptFolderFile.exists()) {
                failedShellScriptFolderFile.mkdir();
            }
            FAILED_FOLDER_PATH = event.getServletContext().getRealPath(failedShellScriptFolder);
            HIBERANTE_CONFG = event.getServletContext().getRealPath("WEB-INF/hibernate.cfg.xml");

            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDetail shellScriptJob = JobBuilder.newJob(ShellsScriptJob.class).withIdentity("shellScriptJob", "shellScriptGroup").build();
            SimpleTrigger shellScriptTriger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("shellScriptTriger", "shellScriptGroup")
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND)).withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY).withIntervalInSeconds(Integer.parseInt(shellScriptIntervalInSeconds))).build();
            scheduler.scheduleJob(shellScriptJob, shellScriptTriger);

		/*    JobDetail checkShellsScriptExistedJob = JobBuilder.newJob(CheckShellsScriptExistedJob.class).withIdentity("checkShtedellScriptExistedJob", "checkShellScriptExistedGroup").build();
		    SimpleTrigger checkShtedellScriptExistedTriger = (SimpleTrigger)TriggerBuilder.newTrigger().withIdentity("checkShtedellScriptExistedTriger", "checkShellScriptExistedGroup")
		    				.startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND)).withSchedule(SimpleScheduleBuilder.simpleSchedule()
		    						.withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY).withIntervalInSeconds(Integer.parseInt(errorShellScriptIntervalInSeconds))).build();
		    scheduler.scheduleJob(checkShellsScriptExistedJob, checkShtedellScriptExistedTriger);*/

            JobDetail nonAgentThreadMinitorJob = JobBuilder.newJob(NonAgentThreadMinitorJob.class).withIdentity("nonAgentThreadMinitorJob", "nonAgentThreadMinitorGroup").build();
            SimpleTrigger nonAgentThreadMinitorJobTriger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("nonAgentThreadMinitorTriger", "nonAgentThreadMinitorGroup")
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND)).withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY).withIntervalInSeconds(Integer.parseInt(nonAgentThreadMinitorIntervalInSeconds))).build();
            scheduler.scheduleJob(nonAgentThreadMinitorJob, nonAgentThreadMinitorJobTriger);

            scheduler.start();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }
}
