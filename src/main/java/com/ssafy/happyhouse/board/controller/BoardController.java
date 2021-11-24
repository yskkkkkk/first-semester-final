package com.ssafy.happyhouse.board.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.board.model.BoardDto;
import com.ssafy.happyhouse.board.model.ReplyDto;
import com.ssafy.happyhouse.board.service.BoardService;
import com.ssafy.happyhouse.board.service.ReplyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/board")
@Api("게시판 및 댓글 컨트롤러  API V1")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ReplyService replyService;
	
	@ApiOperation(value = "list", notes = "게시판 리스트를 반환합니다.")
	@PostMapping("/list")
	public ResponseEntity<List<BoardDto>> list(@RequestBody BoardDto board) throws SQLException {
		List<BoardDto> list = boardService.boardList(board);
		logger.info("boarList - 호출");
		if (list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<BoardDto>>(list, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/{boardNo}")
	@ApiOperation(value = "detail", notes = "질문상세 내용을 반환합니다.")
	public ResponseEntity<BoardDto> detail(@PathVariable int boardNo) throws SQLException {
		BoardDto board = boardService.boardInfo(boardNo);
		logger.info("boarInfo - 호출, no: "+boardNo);
		if (board!=null) {
			return new ResponseEntity<BoardDto>(board, HttpStatus.OK);
		} else {
			return new ResponseEntity<BoardDto>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/write")
	@ApiOperation(value = "create", notes = "새 게시글을  작성합니다.")
	public ResponseEntity<BoardDto> create(@RequestBody BoardDto board) throws SQLException {
		boolean result = boardService.regist(board) == 1 ? true : false;
		logger.info("boarCreate - 호출");
		if (result) {
			return new ResponseEntity<BoardDto>(board, HttpStatus.OK);
		} else {
			return new ResponseEntity<BoardDto>(board, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/readCount/{boardNo}")
	@ApiOperation(value = "readCount", notes = "조회수를 증가시킵니다.")
	public void modify(@PathVariable int boardNo) throws SQLException {
		logger.info("readCount - 호출");
		boardService.updateReadCount(boardNo);
	}

	@PutMapping("/update")
	@ApiOperation(value = "modify", notes = "게시물을 수정합니다.")
	public ResponseEntity<BoardDto> modifyBoard(@RequestBody BoardDto board) throws SQLException {
		System.out.println(board);
		boolean result = boardService.update(board) == 1 ? true : false;
		logger.info("modifyBoard - 호출");
		if (result) {
			return new ResponseEntity<BoardDto>(board, HttpStatus.OK);
		} else {
			return new ResponseEntity<BoardDto>(board, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{boardNo}")
	@ApiOperation(value = "remove", notes = "게시물을 삭제합니다.")
	public ResponseEntity<Boolean> removeBoard(@PathVariable int boardNo) throws SQLException {
		boolean result = boardService.delete(boardNo) == 1 ? true : false;
		logger.info("removeBoard - 호출");
		if (result) {
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(result, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/like")
	@ApiOperation(value = "postIsLiked", notes = "유저가 게시물에 좋아요 누른 여부를 반환합니다.")
	public ResponseEntity<Boolean> postIsLiked(@RequestBody Map<String, String> map) throws SQLException {
		Boolean result = boardService.isLiked(map);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PutMapping("/like")
	@ApiOperation(value = "postLikeChanged", notes = "게시물의 좋아요를 토글합니다.")
	public ResponseEntity<Boolean> postLikeChanged(@RequestBody Map<String, String> map) throws SQLException {
		Boolean result = boardService.likeChanged(map);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "list", notes = "댓글 리스트를 반환합니다.")
	@GetMapping("/reply/{boardNo}")
	public ResponseEntity<List<ReplyDto>> replyList(@PathVariable int boardNo) throws SQLException {
		List<ReplyDto> list = replyService.replyList(boardNo);
		logger.info("replyList - 호출");
		if (list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<ReplyDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ReplyDto>>(list, HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/reply")
	@ApiOperation(value = "create", notes = "새 댓글을  작성합니다.")
	public ResponseEntity<ReplyDto> createReply(@RequestBody ReplyDto reply) throws SQLException {
		boolean result = replyService.regist(reply) == 1 ? true : false;
		logger.info("createBoard - 호출");
		if (result) {
			return new ResponseEntity<ReplyDto>(reply, HttpStatus.OK);
		} else {
			return new ResponseEntity<ReplyDto>(reply, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/reply/like")
	@ApiOperation(value = "isliked", notes = "유저가 해당 댓글에 좋아요 누른 여부를 반환합니다.")
	public ResponseEntity<Boolean> isLiked(@RequestBody Map<String, String> map) throws SQLException {
		Boolean result = replyService.isLiked(map);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PutMapping("/reply/like")
	@ApiOperation(value = "likeChanged", notes = "댓글의 좋아요를 토글합니다.")
	public ResponseEntity<Boolean> likeChanged(@RequestBody Map<String, String> map) throws SQLException {
		Boolean result = replyService.likeChanged(map);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PutMapping("/reply")
	@ApiOperation(value = "modify", notes = "댓글내용을 수정합니다.")
	public ResponseEntity<ReplyDto> modifyReply(@RequestBody ReplyDto reply) throws SQLException {
		boolean result = replyService.update(reply) == 1 ? true : false;
		logger.info("modifyReply - 호출");
		if (result) {
			return new ResponseEntity<ReplyDto>(reply, HttpStatus.OK);
		} else {
			return new ResponseEntity<ReplyDto>(reply, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/reply/{replyNo}")
	@ApiOperation(value = "remove", notes = "게시물을 삭제합니다.")
	public ResponseEntity<Boolean> removeReply(@PathVariable int replyNo) throws SQLException {
		boolean result = replyService.delete(replyNo) == 1 ? true : false;
		logger.info("removeReply - 호출");
		if (result) {
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(result, HttpStatus.BAD_REQUEST);
		}
	}
	
	
}