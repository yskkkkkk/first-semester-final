package com.ssafy.happyhouse.chat.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.chat.dao.ChatMapper;
import com.ssafy.happyhouse.chat.model.ChatDto;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int saveChat(ChatDto chatDto) {
		return sqlSession.getMapper(ChatMapper.class).saveChat(chatDto);
	}
}
