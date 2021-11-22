package com.ssafy.happyhouse.word.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.word.model.WordDto;

@Mapper
public interface WordMapper {

	List<WordDto> listWord();
	void updateCount(String word);
	
}
