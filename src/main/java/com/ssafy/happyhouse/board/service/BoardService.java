package com.ssafy.happyhouse.board.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.board.model.BoardDto;

public interface BoardService {

	int regist(BoardDto board) throws SQLException;

	int update(BoardDto board) throws SQLException;
	
	int delete(int boardNo) throws SQLException;
	
	void updateReadCount(int boardNo) throws SQLException;
	
	BoardDto boardInfo(int boardNo) throws SQLException;
	
	List<BoardDto> boardList(BoardDto board) throws SQLException;
	
}
