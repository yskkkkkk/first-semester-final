package com.ssafy.happyhouse.map.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseInfoDto : 아파트정보", description = "아파트의 상세 정보를 나타낸다.")
public class HouseInfoDto {

	@ApiModelProperty(value = "아파트일련번호")
	private int aptCode;
	@ApiModelProperty(value = "아파트이름")
	private String aptName;
	@ApiModelProperty(value = "동코드")
	private String dongCode;
	@ApiModelProperty(value = "건축년도")
	private int buildYear;
	@ApiModelProperty(value = "지번")
	private String jibun;
	@ApiModelProperty(value = "위도")
	private String lat;
	@ApiModelProperty(value = "경도")
	private String lng;
	
	public int getAptCode() {
		return aptCode;
	}
	public void setAptCode(int aptCode) {
		this.aptCode = aptCode;
	}
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public int getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
}
