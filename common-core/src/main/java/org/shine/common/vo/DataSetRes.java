package org.shine.common.vo;

/**
 * 数据集回应信息定义
 * @author tao.zhai
 *
 */
public class DataSetRes {

	private String id;
	
	//模型id
	private String model;
	
	//模板id
	private String templateId;
	
	private String eid;
	
	//数据实例id
	private String instanceId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}


}
