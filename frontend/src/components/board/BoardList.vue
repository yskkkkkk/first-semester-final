<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col class="text-right">
        <b-button variant="outline-primary" @click="moveWrite()"
          >글쓰기</b-button
        >
      </b-col>
    </b-row>
    <br />

    <!-- 게시물 수 -->
    <b-row class="mt-4 mb-4 text-center">
      <b-col cols="2">
        <b-form-select v-model="perPage" :options="articleRows"></b-form-select>
      </b-col>
    </b-row>

    <!-- 테이블 -->
    <b-row>
      <!-- 게시판에 글이 있거나 검색 결과가 존재할 때 -->
      <b-col
        v-if="
          (articles.length > 0 && filterArticles == 'null') ||
          (articles.length > 0 && filterArticles.length > 0)
        "
        class="center"
        style="flex-direction: column"
      >
        <b-table
          id="my-table"
          :items="filterArticles != 'null' ? filterArticles : articles"
          :per-page="perPage"
          :current-page="currentPage"
          :fields="fields"
          @row-clicked="confirmStatus"
        >
          <template #cell(boardNo)="data">
            <span v-if="currentPage == 1"> {{ data.index + 1 }}</span>
            <span v-else>
              {{ (currentPage - 1) * perPage + (data.index + 1) }}
            </span>
          </template>

          <template #cell(title)="data">
            <div v-if="data.item.isExposing == '0'">
              <b-icon icon="lock-fill" class="mr-1"></b-icon>
              <span>{{ data.value }}</span>
            </div>
            <div v-else-if="data.item.isNotice == '1'">
              <span class="underline-hotpink"
                ><b-icon icon="chat-right-dots" class="mr-1"></b-icon
                >{{ data.value }}</span
              >
            </div>
            <div v-else>{{ data.value }}</div>
          </template>
        </b-table>
        <br />

        <!-- 페이지네이션 -->
        <b-pagination
          v-model="currentPage"
          :total-rows="rows"
          :per-page="perPage"
          aria-controls="my-table"
        ></b-pagination>
      </b-col>
      <!-- 게시판에 글이 없을 때 -->
      <b-col v-else
        ><b-alert variant="danger" show>작성된 글이 없습니다. </b-alert></b-col
      >
    </b-row>

    <!-- 검색창 -->
    <b-row class="mt-4 mb-4 text-center center">
      <b-col cols="2">
        <b-form-select v-model="key" :options="keys"></b-form-select>
      </b-col>
      <b-col cols="5">
        <b-form-input v-model="val"></b-form-input>
      </b-col>
      <b-col cols="2">
        <b-button id="searchBtn" @click="searchBtn">검색</b-button>
      </b-col>
    </b-row>
    <!-- <b-table-simple
          hover
          responsive
          :items="filterArticles != 'null' ? filterArticles : articles"
          :per-page="perPage"
          :current-page="currentPage"
        >
          <b-thead head-variant="light">
            <b-tr>
              <b-th class="text-center" width="7%">No</b-th>
              <b-th>제목</b-th>
              <b-th width="10%">작성자</b-th>
              <b-th class="text-center" width="20%">작성일</b-th>
              <b-th class="text-center" width="10%">조회수</b-th>
            </b-tr>
          </b-thead>
         검색 전: 게시판 
          <tbody v-if="filterArticles == 'null'">
            <board-list-row
              v-for="(article, index) in articles"
              :key="article.boardNo"
              :article="article"
              :length="articles.length"
              :index="index"
            />
          </tbody>
          검색 후: 데이터가 있을 경우
          <tbody v-else-if="filterArticles.length > 0">
            <board-list-row
              v-for="(article, index) in filterArticles"
              :key="index"
              :article="article"
              :length="filterArticles.length"
              :index="index"
            />
            <b-td colspan="5">
              <b-alert variant="info" show
                >검색 결과 : {{ filterArticles.length }} 건
              </b-alert></b-td
            >
          </tbody>
          <tbody v-else>
            <b-td colspan="5">
              <b-alert variant="warning" show
                >데이터가 존재하지 않습니다.
              </b-alert></b-td
            >
          </tbody>
        </b-table-simple> -->
  </b-container>
</template>

<script>
// import BoardListRow from "@/components/board/child/BoardListRow";
import { mapActions, mapMutations, mapState } from "vuex";
const boardStore = "boardStore";
const wordStore = "wordStore";
const memberStore = "memberStore";

export default {
  name: "BoardList",
  data() {
    return {
      key: null,
      keys: [
        { value: null, text: "선택" },
        { value: "title", text: "제목" },
        { value: "writer", text: "작성자" },
      ],
      val: "",
      perPage: 10,
      currentPage: 1,
      fields: [
        { key: "boardNo", label: "no" },
        { key: "title", label: "제목" },
        { key: "writer", label: "작성자" },
        { key: "regTime", label: "작성일" },
        { key: "readCount", label: "조회수" },
      ],
      articleRows: [
        { value: 5, text: "5개" },
        { value: 10, text: "10개" },
        { value: 20, text: "20개" },
      ],
    };
  },
  // components: {
  //   BoardListRow,
  // },
  created() {
    this.getlistArticle();
    this.CLEAR_FILTER_ARTICLE();
  },
  methods: {
    ...mapActions(wordStore, ["addWord"]),
    ...mapActions(boardStore, [
      "increaseHit",
      "getlistArticle",
      "SearchArticle",
    ]),
    ...mapMutations(boardStore, [
      "SET_ARTICLE",
      "CLEAR_FILTER_ARTICLE",
      "SET_FILTER_ARTICLE",
    ]),
    moveWrite() {
      this.$router.push({ name: "BoardWrite", params: { type: "register" } });
    },
    searchBtn() {
      if (this.key == null) {
        alert("검색 분류를 선택해주세요");
      } else if (this.val == "") {
        alert("검색어를 입력해주세요.");
      } else {
        // 검색할 때마다 wordCloud에 단어 추가
        this.addWord(this.val);

        if (this.key == "title") {
          this.SearchArticle({
            title: this.val,
            writer: "",
          });
        } else if (this.key == "writer") {
          this.SearchArticle({
            title: "",
            writer: this.val,
          });
        }
      }
    },
    confirmStatus(article) {
      if (this.userInfo == null) {
        // 비회원일 경우
        if (article.isExposing == "1") {
          // 공개글일 경우
          this.BoardView(article);
        } else {
          alert("로그인이 필요합니다."); // 비공개글일 경우
        }
      } else if (this.userInfo.userType == "1") {
        // 관리자일 경우
        this.BoardView(article);
      } else {
        // 일반 회원일 경우
        if (article.isExposing == "1") {
          // 공개글일 경우
          this.BoardView(article);
        } else {
          // 비공개글일 경우
          if (article.writer == this.userInfo.userName) {
            this.BoardView(article);
          } else {
            alert("비공개 글입니다.");
          }
        }
      }
    },
    BoardView(article) {
      this.increaseHit(article.boardNo);
      this.SET_ARTICLE(article);
      this.$router.push({
        name: "BoardView",
        params: { boardNo: article.boardNo },
      });
    },
  },
  watch: {
    key: function () {
      if (this.key == null) this.CLEAR_FILTER_ARTICLE();
    },
  },
  computed: {
    ...mapState(boardStore, ["articles", "filterArticles"]),
    ...mapState(memberStore, ["userInfo"]),
    rows() {
      if (this.filterArticles != "null") return this.filterArticles.length;
      return this.articles.length;
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(74, 164, 238, 0.3) 30%
  );
}
.center {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
