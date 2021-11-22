package com.ssafy.happyhouse.word.service;

import java.util.List;

import com.ssafy.happyhouse.word.model.WordDto;

public interface WordService {

	List<WordDto> listWord();
	void registWord(List<String> concerns);
	void updateCount(String word);
	
}
