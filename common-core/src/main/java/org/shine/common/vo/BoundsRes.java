package org.shine.common.vo;


/**
 * 数据查询时，bound回应信息定义
 * @author tao.zhai
 *
 */
public class BoundsRes {

	private String minlat;
	
	private String minlon;
	
	private String maxlat;
	
	private String maxlon;

	public String getMinlat() {
		return minlat;
	}

	public void setMinlat(String minlat) {
		this.minlat = minlat;
	}

	public String getMinlon() {
		return minlon;
	}

	public void setMinlon(String minlon) {
		this.minlon = minlon;
	}

	public String getMaxlat() {
		return maxlat;
	}

	public void setMaxlat(String maxlat) {
		this.maxlat = maxlat;
	}

	public String getMaxlon() {
		return maxlon;
	}

	public void setMaxlon(String maxlon) {
		this.maxlon = maxlon;
	}
	

}
