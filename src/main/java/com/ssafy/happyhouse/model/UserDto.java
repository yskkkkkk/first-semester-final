package com.ssafy.happyhouse.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UserDto : 유저정보", description = "유저의 상세 정보를 나타낸다.")
public class UserDto {

	@ApiModelProperty(value = "유저 일련번호")
	private int userNo;
	@ApiModelProperty(value = "계정명")
	private String userId;
	@ApiModelProperty(value = "암호")
	private String userPw;
	@ApiModelProperty(value = "사용자명")
	private String userName;
	@ApiModelProperty(value = "사용자 전자우편")
	private String email;
	@ApiModelProperty(value = "유저 권한 표시")
	private String userType;
	@ApiModelProperty(value = "가입 일시")
	private String joinDate;
	@ApiModelProperty(value = "데이터 수정 일시")
	private String modifyDate;
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
}
