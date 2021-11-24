package com.ssafy.happyhouse.board.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.board.dao.BoardMapper;
import com.ssafy.happyhouse.board.dao.ReplyMapper;
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

	@Override
	public Boolean isLiked(Map<String, String> map) throws SQLException {
		String userNo = map.get("userNo");
		int boardNo = Integer.parseInt(map.get("boardNo"));
		System.out.printf("userNo: %s, replyNo: %d\n",userNo, boardNo);

		// board의 recommad 컬럼 가져옴
		String res = sqlSession.getMapper(BoardMapper.class).isLiked(boardNo);
		
		// 컬럼이 비어있으면 아무도 좋아요를 누르지 않았다는 의미 => 좋아요 누르기
		if (res == null || res == "" || res.equals("")) {
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
		int boardNo = Integer.parseInt(map.get("boardNo"));
		
		Map<String, String> param = new HashMap<String, String>();
		String res = sqlSession.getMapper(BoardMapper.class).isLiked(boardNo);
		Boolean isLiked = false;
		
		if (res == null || res.equals("0") || res.equals("")) {
			param.put("boardNo", Integer.toString(boardNo));
			param.put("recommand", ","+userNo);
			sqlSession.getMapper(BoardMapper.class).likeChanged(param);
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
			
			param.put("boardNo", Integer.toString(boardNo));
			param.put("recommand", sb.toString());
			System.out.println(sb.toString());
			sqlSession.getMapper(BoardMapper.class).likeChanged(param);
			
			if (isLiked) return false;
			return true;
		}
	}

}
