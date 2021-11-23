package com.ssafy.happyhouse.user.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.user.model.UserDto;

public interface UserService {

	int regist(UserDto user) throws SQLException;

	int update(UserDto user) throws SQLException;
	
	int delete(String userId) throws SQLException;
	
	UserDto userInfo(String userId) throws SQLException;
	
	List<UserDto> userList() throws SQLException;
	
	UserDto login(UserDto user) throws SQLException;

	int checkEmail(String userEmail);

	int checkId(String userId);
}
