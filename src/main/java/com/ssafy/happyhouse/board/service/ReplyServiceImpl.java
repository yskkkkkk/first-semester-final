package com.ssafy.happyhouse.board.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
		String recommandCount = dto.getRecommand();
		
//		if (param.get("direction").equals("up"))
//			recommandCount++;
//		else 
//			recommandCount--;
		
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

	@Override
	public Boolean isLiked(Map<String, String> map) throws SQLException {
		String userNo = map.get("userNo");
		int replyNo = Integer.parseInt(map.get("replyNo"));
		System.out.printf("userNo: %s, replyNo: %d\n",userNo, replyNo);

		// reply의 recommad 컬럼 가져옴
		String res = sqlSession.getMapper(ReplyMapper.class).isLiked(replyNo);
		
		// 컬럼이 비어있으면 아무도 좋아요를 누르지 않았다는 의미 => 좋아요 누르기
		if (res == "" || res.equals("")) {
			System.out.println("컬럼 비어있음 => 좋아요 안누름");
			return false;
		}
		else {
			StringTokenizer st = new StringTokenizer(res, ",");
			StringBuffer sb = new StringBuffer();
			while (st.hasMoreTokens()) {
				String no = st.nextToken();
				System.out.println("st.nextToken():"+no+" / userNo:"+userNo );
				
				if (no.equals(userNo)) {
					// 좋아요 이미 눌러져 있다는 의미 => 좋아요 취소하기
					System.out.println("좋아요 눌러져있음");
					return true;
				}
				
				sb.append(",").append(no);
			}
			sb.append(",").append(userNo);

			System.out.println("좋아요 안누름");
			return false;
		}
	}

	@Override
	public Boolean likeChanged(Map<String, String> map) throws SQLException {
		String userNo = map.get("userNo");
		int replyNo = Integer.parseInt(map.get("replyNo"));
		
		Map<String, String> param = new HashMap<String, String>();
		String res = sqlSession.getMapper(ReplyMapper.class).isLiked(replyNo);
		Boolean isLiked = false;
		
		if (res.equals("0") || res.equals("")) {
			param.put("replyNo", Integer.toString(replyNo));
			param.put("recommand", ","+userNo);
			sqlSession.getMapper(ReplyMapper.class).likeChanged(param);
			return true;
		}
		else {
			StringTokenizer st = new StringTokenizer(res, ",");
			StringBuffer sb = new StringBuffer();

			while (st.hasMoreTokens()) {
				String no = st.nextToken();
				System.out.println("st.nextToken():"+no+" / userNo:"+userNo );
				
				if (no.equals(userNo)) {
					// 좋아요 이미 눌러져 있다는 의미 => 좋아요 취소하기
					isLiked = true;
				} else {
					sb.append(",").append(no);
				}
			}
			if (!isLiked) {
				sb.append(",").append(userNo);
			}
			
			param.put("replyNo", Integer.toString(replyNo));
			param.put("recommand", sb.toString());
			System.out.println(sb.toString());
			sqlSession.getMapper(ReplyMapper.class).likeChanged(param);
			
			if (isLiked) return false;
			return true;
		}
	}

	@Override
	public int getReplyCnt(String boardNo) throws SQLException {
		return sqlSession.getMapper(ReplyMapper.class).getReplyCnt(boardNo);
	}

}
