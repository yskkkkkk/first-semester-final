package com.ssafy.happyhouse.board.dao;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.board.model.BoardDto;

@Mapper
public interface BoardMapper {

	int regist(BoardDto board) throws SQLException;

	int update(BoardDto board) throws SQLException;
	
	int delete(int boardNo) throws SQLException;
	
	BoardDto boardInfo(int boardNo) throws SQLException;
	
	List<BoardDto> boardList(BoardDto board) throws SQLException;

	void updateReadCount(BoardDto boardDto) throws SQLException;
	
}
