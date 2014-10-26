package org.shine.common.map.osm;


import com.mongodb.DBObject;
import org.shine.common.vo.BoundsRes;
import org.shine.common.vo.DataSetRes;
import org.shine.common.vo.ResultDTO;
import org.shine.common.vo.WriteRes;

public interface BaseOsmProcess {

	/**
	 * 数据查询结果，返回的json结果
	 * @param bounds 查询bound值
	 * @param layer 图层信息
	 * @param results 模型信息
	 * @return
	 */
	public  String readData4Json(BoundsRes bounds, DataSetRes layer, ResultDTO<DBObject> results) throws Exception;

	/**
	 * 写入数据的返回值数据处理
	 * @param bounds
	 * @param layer
	 * @param results
	 * @return
	 */
	public  String writeData4Json(BoundsRes bounds, DataSetRes layer, ResultDTO<WriteRes> results) throws Exception;
}
