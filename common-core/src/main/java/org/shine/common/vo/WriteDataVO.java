package org.shine.common.vo;


public class WriteDataVO {
	
	//图层id
	private long dataSetId;
	
	//数据实例id
	private long instanceId;
	
	//模型id
	private long model;
	
	//模板id
	private long templateId;
	
	//xml
	private String resType;
	
	//要处理的数据
	private String data;
	
    //企业Id
    private String entId;
    //用户Id
    private String userId;

    private long fsId;//文件服务Id
    
    private long hisInstanceId; //历史表数据库实例
    
    private long rightId; //权限id
    
    private String editor; //编辑者
	
	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getInstanceId() {
		return instanceId;
	}

	public void setInstanceid(long instanceId) {
		this.instanceId = instanceId;
	}

	public long getmodel() {
		return model;
	}

	public void setmodel(long modelId) {
		this.model = modelId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long dataSetId) {
		this.templateId = dataSetId;
	}

	public void setDataSetId(long dataSetId) {
		this.dataSetId = dataSetId;
	}

	public long getDataSetId() {
		return dataSetId;
	}

	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    public long getFsId() {
        return fsId;
    }

    public void setFsId(long fsId) {
        this.fsId = fsId;
    }

    /**
     * Getter method for property <tt>hisInstanceId</tt>.
     * 
     * @return property value of hisInstanceId
     */
    public long getHisInstanceId() {
        return hisInstanceId;
    }

    /**
     * Setter method for property <tt>hisInstanceId</tt>.
     * 
     * @param hisInstanceId value to be assigned to property hisInstanceId
     */
    public void setHisInstanceId(long hisInstanceId) {
        this.hisInstanceId = hisInstanceId;
    }

    public long getRightId() {
        return rightId;
    }

    public void setRightId(long rightId) {
        this.rightId = rightId;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}