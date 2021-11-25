package com.ssafy.happyhouse.chat.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.chat.model.ChatDto;

@Mapper
public interface ChatMapper {
	int saveChat(ChatDto chatDto);
}
