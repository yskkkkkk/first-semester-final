package com.ssafy.happyhouse.board.dao;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ssafy.happyhouse.board.model.ReplyDto;

@Mapper
public interface ReplyMapper {

	int regist(ReplyDto reply) throws SQLException;

	int update(ReplyDto reply) throws SQLException;
	
	int delete(int replyNo) throws SQLException;
	
	ReplyDto replyInfo(int replyNo) throws SQLException;
	
	List<ReplyDto> replyList(int boardNo) throws SQLException;
}