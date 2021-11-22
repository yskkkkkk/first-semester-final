package com.ssafy.happyhouse.board.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.board.dao.BoardMapper;
import com.ssafy.happyhouse.board.model.BoardDto;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private SqlSession sqlSession;
	

	@Override
	public int regist(BoardDto board) throws SQLException {
		return sqlSession.getMapper(BoardMapper.class).regist(board);
	}

	@Override
	public int update(BoardDto board) throws SQLException {
		return sqlSession.getMapper(BoardMapper.class).update(board);
	}

	@Override
	public int delete(int boardNo) throws SQLException {
		return sqlSession.getMapper(BoardMapper.class).delete(boardNo);
	}

	@Override
	public void updateReadCount(int boardNo) throws SQLException {
		BoardDto board = sqlSession.getMapper(BoardMapper.class).boardInfo(boardNo);
		sqlSession.getMapper(BoardMapper.class).updateReadCount(new BoardDto(boardNo, board.getReadCount()+1));
	}
	
	@Override
	public BoardDto boardInfo(int boardNo) throws SQLException {
		return sqlSession.getMapper(BoardMapper.class).boardInfo(boardNo);
	}

	@Override
	public List<BoardDto> boardList(BoardDto board) throws SQLException {
		return sqlSession.getMapper(BoardMapper.class).boardList(board);
	}




}
