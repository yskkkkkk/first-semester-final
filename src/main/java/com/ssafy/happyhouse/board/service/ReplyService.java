package com.ssafy.happyhouse.board.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.board.model.ReplyDto;

public interface ReplyService {

	int regist(ReplyDto reply) throws SQLException;

	int update(ReplyDto reply) throws SQLException;
	
	void updateRecommand(Map<String, String> param) throws SQLException;

	int delete(int replyNo) throws SQLException;
	
	ReplyDto replyInfo(int replyNo) throws SQLException;
	
	List<ReplyDto> replyList(int boardNo) throws SQLException;

	Boolean isLiked(Map<String, String> map) throws SQLException;

	Boolean likeChanged(Map<String, String> map) throws SQLException;
	
}
