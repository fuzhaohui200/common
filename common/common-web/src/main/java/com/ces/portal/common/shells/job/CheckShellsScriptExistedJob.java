package com.ces.portal.common.shells.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckShellsScriptExistedJob implements Job {

    private Logger logger = LoggerFactory.getLogger(CheckShellsScriptExistedJob.class);

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        logger.info("------- Excuting CheckShellsScriptExistedJob  -------------");
        /*try {
			File tivoliConfigFile = new File(ShellsScriptListener.TIVOLICONF_PATH);
			Document document = ConfigXMLUtil.parseXmlFile(tivoliConfigFile);
			Element root = document.getRootElement();
			// iterate through child elements of root
			for (Iterator iterator = root.elementIterator("Server"); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				Element name = element.element("name");
				if(name != null) {
					Element SubElements = element.element("SubElements"); 
					Element shName = SubElements.element("shellSciptFileName");
					if(shName != null && !shName.getTextTrim().equals("")) {
						File shellScripFile = new File(ShellsScriptListener.SUCCES_FOLDER_PATH  + name.getText() , shName.getText()); 
						if(!shellScripFile.exists()) {
							shName.setText("");
							//  讨论是否需要删除数据库里相应脚本数据
						}
					}
				}
			}
			ConfigXMLUtil.write(document, new FileWriter(tivoliConfigFile));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}*/
    }
}
