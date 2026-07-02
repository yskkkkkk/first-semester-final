# HappyHouse — 부동산 실거래가 시세조회 서비스 🏆

지도 기반으로 아파트 실거래가를 조회하고, 관련 정보와 커뮤니티를 함께 제공하는 웹 서비스입니다.
**학기 최종 프로젝트로 진행했으며, 최우수 프로젝트로 선정되었습니다.**

## 기술 스택

**Backend**
- Spring Boot 2.5, MyBatis, MySQL
- JWT(jjwt) 인증, Spring WebSocket / STOMP(실시간 채팅), Spring Mail
- jsoup(뉴스 크롤링 · Open Graph 파싱), Swagger(springfox)

**Frontend**
- Vue 2, Vuex, Vue Router, Bootstrap-Vue
- Kakao Maps JavaScript SDK

**External API**
- 국토교통부 아파트 실거래가 Open API
- Kakao Maps API, Naver 뉴스

## 주요 기능

- **지도 기반 시세 검색** — 시/도 → 구/군 → 동 → 아파트 단계별 검색, 지도 위 마커·오버레이로 단지명·건축년도·최근 실거래가 표시
- **실거래가 데이터** — 사전 적재된 실거래 데이터 + 공공 Open API 연동
- **검색어 워드클라우드** — 자주 검색된 단어 시각화
- **부동산 뉴스** — 뉴스 크롤링 기반 기사 제공
- **게시판** — 글 작성/조회/수정/삭제, 페이지네이션, 공지 상단 고정, 게시물·댓글 좋아요, 게시물 링크 미리보기(Open Graph)
- **실시간 채팅** — WebSocket/STOMP 기반 채팅
- **회원** — 회원가입(아이디·이메일 중복 확인), JWT 로그인, 프로필 수정/삭제, 이메일 기반 임시 비밀번호 발급(비밀번호 찾기)
- **관련 사이트 모음** — 주요 부동산·청약·인테리어 사이트 링크 디렉토리

## 프로젝트 구조

```
src/main/java/com/ssafy/happyhouse/
  board/  chat/  map/  news/  opengraph/  user/  word/   # 도메인별 패키지 (controller/service/dao|mapper/model)
src/main/resources/                                       # MyBatis 매퍼, DB 스키마
frontend/                                                 # Vue 2 웹 클라이언트
```

## 참고 (보안)

- `src/main/resources/application.properties`의 메일·DB 자격증명, 그리고 프론트엔드에 포함된 Kakao Maps API 키가 코드에 하드코딩되어 있습니다.
- 별도의 보안 정리 단계에서 **자격증명 폐기·재발급 및 환경변수/`.gitignore` 이전**이 필요합니다.
