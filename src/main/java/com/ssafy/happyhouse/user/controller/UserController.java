package com.ssafy.happyhouse.user.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.user.model.UserDto;
import com.ssafy.happyhouse.user.service.JwtServiceImpl;
import com.ssafy.happyhouse.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api("사용자 컨트롤러  API V1")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;
	@Autowired
	JavaMailSender mailSender; // 메일 서비스를 사용하기 위해 의존성을 주입함
	@Autowired
	private UserService userService;

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		logger.debug("로그인 실행 : {}", userDto);
		try {
			UserDto loginUser = userService.login(userDto);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserId(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try { 
				UserDto userDto = userService.userInfo(userId);
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "아이디중복체크", notes = "해당 아이디의 중복여부를 반환한다.", response = Map.class)
	@PostMapping("/id")
	public ResponseEntity<String> checkId(@RequestBody String userId) {
		logger.info("아이디 중복 체크 요청!!!" + userId);
		
		int cnt = userService.checkId(userId);
		
		if (cnt == 1) {
			System.out.println("아이디 중복!");
			return new ResponseEntity<String>("NO", HttpStatus.CONFLICT);
		} else {
			System.out.println("아이디 사용 가능!");
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		}
	}

	@ApiOperation(value = "이메일중복체크", notes = "해당 이메일의 중복여부를 반환한다.", response = Map.class)
	@PostMapping("/email")
	public ResponseEntity<String> checkEmail(@RequestBody String userEmail) {
		logger.info("이메일 중복 체크 요청!!!" + userEmail);

		int cnt = userService.checkEmail(userEmail);

		if (cnt == 1) { // 이메일 중복
			System.out.println("이메일 중복!");
			return new ResponseEntity<String>("NO", HttpStatus.CONFLICT);
		} else { // 가입 가능
			System.out.println("이메일 사용 가능!");
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		}
	}

	@ApiOperation(value = "regist", notes = "회원 정보를 받아서 회원가입합니다.")
	@PostMapping("/")
	public ResponseEntity<UserDto> regist(@RequestBody UserDto user, HttpSession session, Model model) {
		System.out.println(user);
		try {
			userService.regist(user);
			return new ResponseEntity<UserDto>(user, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "회원가입 중 문제 발생");
			return new ResponseEntity<UserDto>(user, HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
	}

	@ApiOperation(value = "update", notes = "회원정보를 수정합니다.")
	@PutMapping("/")
	public ResponseEntity<UserDto> update(@RequestBody UserDto user, HttpSession session, Model model) {
		try {
			userService.update(user);
			session.setAttribute("userinfo", user);
			return new ResponseEntity<UserDto>(user, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "회원 정보 수정 중 문제 발생");
			return new ResponseEntity<UserDto>(user, HttpStatus.INTERNAL_SERVER_ERROR); // 500
		} 
	}

	@ApiOperation(value = "delete", notes = "회원탈퇴를 진행합니다.")
	@DeleteMapping("/")
	public ResponseEntity<String> delete(@RequestBody String userId, HttpSession session, Model model) {
		try {
			userService.delete(userId);
			session.setAttribute("userinfo", null);
			return new ResponseEntity<String>(userId, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "회원 탈퇴 중 문제 발생");
			return new ResponseEntity<String>(userId, HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
	}

	@ApiOperation(value = "check", notes = "비밀번호 찾기를 위해 회원존재여부를 반환합니다.")
	@PostMapping("/check")
	public ResponseEntity<Integer> check(@RequestBody UserDto user, Model model) {
		int result = 0; // 0: 메일발송 성공, 1: 이메일이 없음, 2: 서버오류
		try {
			String email = userService.userInfo(user.getUserId()).getEmail();
			if (email.equals(user.getEmail())) {
				// 여기서 이메일 발송
			} else {
				result = 1;
			}
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			result = 2;
			e.printStackTrace();
			model.addAttribute("msg", "회원 조회 중 문제 발생");
			return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 임시비밀번호 발송
	@ApiOperation(value = "임시비밀번호발송", notes = "임시비밀번호를 발송하여 비밀번호찾기를 진행시켜준다.", response = Map.class)
	@PutMapping("/pw")
	public ResponseEntity<String> findPw(@RequestBody UserDto user) throws IOException, SQLException {
		logger.info("임시 비밀번호 방송 요청 발생 / 요청 email: " + user.getEmail());

		// 임시 번호 만들기
		String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // - 제거
		uuid = uuid.substring(0, 10); // uuid를 앞에서부터 10자리 잘라줌.

		// 임시 비밀번호로 변경하기
		user.setUserPw(uuid);
		
		if (userService.update(user) == 1) {
			String setFrom = "HappyHouseindustry@gmail.com"; // 보내는 사람(관리자) 이메일
			String tomail = user.getEmail(); // 받는 사람 이메일
			String title = "[HappyHouse] 임시비밀번호 발송"; // 제목
			String content =
					System.getProperty("line.separator") + System.getProperty("line.separator")
							+ "안녕하세요, 회원님. 저희 [HappyHouse]를 찾아주셔서 감사합니다." + System.getProperty("line.separator")
							+ "임시 비밀번호는 " + uuid + " 입니다." + System.getProperty("line.separator")
							+ "로그인 후 비밀번호 변경을 꼭 진행해주시길 바랍니다." + System.getProperty("line.separator");
	
			try {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom(setFrom); // 보내는사람 (생략불가)
				messageHelper.setTo(tomail); // 받는사람 이메일
				messageHelper.setSubject(title);// 메일제목 (생략가능)
				messageHelper.setText(content); // 메일 내용
	
				mailSender.send(message);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			logger.info("임시 비밀번호: " + uuid);
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("NO", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
