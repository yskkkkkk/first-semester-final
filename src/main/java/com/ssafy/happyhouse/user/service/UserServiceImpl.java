package com.ssafy.happyhouse.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.user.dao.UserMapper;
import com.ssafy.happyhouse.user.model.UserDto;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private SqlSession sqlSession;

	@Value("${config.base62.character}")
	private String character;
	
	@Override
	public int regist(UserDto user) throws SQLException {
		user.setUserPw(code(user.getUserPw()));
		return sqlSession.getMapper(UserMapper.class).regist(user);
	}

	@Override
	public int update(UserDto user) throws SQLException {
		user.setUserPw(code(user.getUserPw()));
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
		user.setUserPw(code(user.getUserPw()));
		return sqlSession.getMapper(UserMapper.class).login(user);
	}

	@Override
	public int checkEmail(String userEmail) {
		return sqlSession.getMapper(UserMapper.class).checkEmail(userEmail);
	}

	@Override
	public int checkId(String userId) {
		return sqlSession.getMapper(UserMapper.class).checkId(userId);
	}
	
	public String code(String pw) {
		int cardinalNum = 62;
		int original = 1;
		for (int i = 0; i < pw.length(); i++) {
			original *= (int)pw.charAt(i) * Math.pow(i+1, 2);
		}
		List<Character> output = new ArrayList<>();
		while (original != 0) {
			output.add(character.charAt(original % cardinalNum));
			original /= cardinalNum;
		}
		Collections.reverse(output);
		StringBuilder sb = new StringBuilder();
	    for (char ch : output) 
	        sb.append(ch);
		
		return sb.toString();
	}
}
