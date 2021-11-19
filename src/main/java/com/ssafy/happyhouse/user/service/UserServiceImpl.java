package com.ssafy.happyhouse.user.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.user.dao.UserMapper;
import com.ssafy.happyhouse.user.model.UserDto;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int regist(UserDto user) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).regist(user);
	}

	@Override
	public int update(UserDto user) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).update(user);
	}

	@Override
	public int delete(UserDto user) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).delete(user);
	}

	@Override
	public UserDto userInfo(String userId) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).userInfo(userId);
	}

	@Override
	public List<UserDto> userList() throws SQLException {
		return sqlSession.getMapper(UserMapper.class).userList();
	}

	@Override
	public UserDto login(UserDto user) throws SQLException {
		if (user.getUserId() == null || user.getUserPw() == null) return null;
		return sqlSession.getMapper(UserMapper.class).login(user);
	}

}
