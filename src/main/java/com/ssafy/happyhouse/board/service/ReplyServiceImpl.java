package com.ssafy.happyhouse.board.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.board.dao.ReplyMapper;
import com.ssafy.happyhouse.board.model.ReplyDto;

@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int regist(ReplyDto reply) throws SQLException {
		return sqlSession.getMapper(ReplyMapper.class).regist(reply);
	}

	@Override
	public int update(ReplyDto reply) throws SQLException {
		return sqlSession.getMapper(ReplyMapper.class).update(reply);
	}
	
	@Override
	public void updateRecommand(Map<String, String> param) throws SQLException{
		int replyNo = Integer.parseInt(param.get("replyNo"));
		ReplyDto dto = sqlSession.getMapper(ReplyMapper.class).replyInfo(replyNo);
		int recommandCount = dto.getRecommand();
		
		if (param.get("direction").equals("up"))
			recommandCount++;
		else 
			recommandCount--;
		
		sqlSession.getMapper(ReplyMapper.class).update(new ReplyDto(replyNo, recommandCount));
	}

	@Override
	public int delete(int replyNo) throws SQLException {
		return sqlSession.getMapper(ReplyMapper.class).delete(replyNo);
	}

	@Override
	public ReplyDto replyInfo(int replyNo) throws SQLException {
		return sqlSession.getMapper(ReplyMapper.class).replyInfo(replyNo);
	}

	@Override
	public List<ReplyDto> replyList(int boardNo) throws SQLException {
		return sqlSession.getMapper(ReplyMapper.class).replyList(boardNo);
	}

}
