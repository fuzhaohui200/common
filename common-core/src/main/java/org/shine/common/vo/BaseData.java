/*
package org.shine.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vividsolutions.jts.geom.Geometry;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseData implements Serializable {

	private static final long serialVersionUID = -5366871140515483878L;
	private String id;
	@JsonIgnore
	private long dataSetId;
	@JsonIgnore
	private Integer dataSetType;
	@JsonIgnore
	private long model;
	@JsonProperty
	private long dsId;
    private Geometry geometry;
	private String timestamp;
	// 单个数据对象版本
	private Integer version;
	// 整个数据集版本
	private Integer changeset;
	// 是否偏转0,不偏转；1，偏转
	protected int deflection;
	//修改类型，是编辑接口还是导入接口。
	protected int modifyType;
	protected String geomRule;
	protected String geoCodeField;
	// geom字段数据.
	protected String geom;
	// 存放数据的map,最后可能会拿到其它类里.
	protected Map<String, Object> dataMap = new HashMap<String, Object>();
	// 资源ID
	private long resId;
	private long jobId;
	private long taskId;
	private String spaceId;
	private String userId;
	private String errorFilePath;
	private String writeFilePath;
	//数据集字段名
	protected List<String> dataSetHeadList;
	//存放原始文件中文件头信息
	protected List<String> headInfo;
	//存放转换过的文件头信息
	protected List<String> headList;

    private List<FieldInfoVo> fieldInfoVos;//数据集字段信息

	//开始行号
	private  long beginNO;
	//结束行号
	private  long endNO;
	
	private String errorMessage;//错误信息

    private String errorFileHeader;//错误头

    @JsonProperty
    private long fsId;
    
    @JsonProperty
    private long hisInstanceId;
    
    private long rightId;
    
    private long mappingId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(long dataSetId) {
		this.dataSetId = dataSetId;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public JSONObject toJson() {
		return null;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getChangeset() {
		return changeset;
	}

	public void setChangeset(Integer changeset) {
		this.changeset = changeset;
	}

	public int getDeflection() {
		return deflection;
	}

	public void setDeflection(int deflection) {
		this.deflection = deflection;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public long getResId() {
		return resId;
	}

	public void setResId(long resId) {
		this.resId = resId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getErrorFilePath() {
		return errorFilePath;
	}

	public void setErrorFilePath(String errorFilePath) {
		this.errorFilePath = errorFilePath;
	}

	public List<String> getHeadInfo() {
		return headInfo;
	}

	public void setHeadInfo(List<String> headInfo) {
		this.headInfo = headInfo;
	}

	public List<String> getHeadList() {
		return headList;
	}

	public void setHeadList(List<String> headList) {
		this.headList = headList;
	}

	public String getWriteFilePath() {
		return writeFilePath;
	}

	public void setWriteFilePath(String writeFilePath) {
		this.writeFilePath = writeFilePath;
	}

	public Integer getDataSetType() {
		return dataSetType;
	}

	public void setDataSetType(Integer dataSetType) {
		this.dataSetType = dataSetType;
	}

	public String getGeomRule() {
		return geomRule;
	}

	public void setGeomRule(String geomRule) {
		this.geomRule = geomRule;
	}

	public String getGeoCodeField() {
		return geoCodeField;
	}

	public void setGeoCodeField(String geoCodeField) {
		this.geoCodeField = geoCodeField;
	}

	public int getModifyType() {
		return modifyType;
	}

	public void setModifyType(int modifyType) {
		this.modifyType = modifyType;
	}

	public List<String> getDataSetHeadList() {
		return dataSetHeadList;
	}

	public void setDataSetHeadList(List<String> dataSetHeadList) {
		this.dataSetHeadList = dataSetHeadList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getBeginNO() {
		return beginNO;
	}

	public void setBeginNO(long beginNO) {
		this.beginNO = beginNO;
	}

	public long getEndNO() {
		return endNO;
	}

	public void setEndNO(long endNO) {
		this.endNO = endNO;
	}

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    public String toGeoJsonString(){
        return null;
    }

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

    public long getFsId() {
        return fsId;
    }

    public void setFsId(long fsId) {
        this.fsId = fsId;
    }

    */
/**
     * Getter method for property <tt>hisInstanceId</tt>.
     * 
     * @return property value of hisInstanceId
     *//*

    public long getHisInstanceId() {
        return hisInstanceId;
    }

    */
/**
     * Setter method for property <tt>hisInstanceId</tt>.
     * 
     * @param hisInstanceId value to be assigned to property hisInstanceId
     *//*

    public void setHisInstanceId(long hisInstanceId) {
        this.hisInstanceId = hisInstanceId;
    }

    public long getRightId() {
        return rightId;
    }

    public void setRightId(long rightId) {
        this.rightId = rightId;
    }

    public long getModel() {
        return model;
    }

    public void setModel(long model) {
        this.model = model;
    }

    public long getDsId() {
        return dsId;
    }

    public void setDsId(long dsId) {
        this.dsId = dsId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public long getMappingId() {
        return mappingId;
    }

    public void setMappingId(long mappingId) {
        this.mappingId = mappingId;
    }

    public List<FieldInfoVo> getFieldInfoVos() {
        return fieldInfoVos;
    }

    public void setFieldInfoVos(List<FieldInfoVo> fieldInfoVos) {
        this.fieldInfoVos = fieldInfoVos;
    }

    public String importToGeoJsonString(Map<Integer,Object> lineDatas){
        return null;
    }

    public String getErrorFileHeader() {
        return errorFileHeader;
    }

    public void setErrorFileHeader(String errorFileHeader) {
        this.errorFileHeader = errorFileHeader;
    }
}*/
