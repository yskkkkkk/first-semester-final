<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col class="text-right">
        <b-button variant="outline-primary" @click="moveWrite()"
          >글쓰기</b-button
        >
      </b-col>
    </b-row>
    <b-row>
      <!-- 게시판에 글이 있을 때 -->
      <b-col v-if="articles.length">
        <b-table-simple hover responsive>
          <b-thead head-variant="light">
            <b-tr>
              <b-th>글번호</b-th>
              <b-th>제목</b-th>
              <b-th>조회수</b-th>
              <b-th>작성자</b-th>
              <b-th>작성일</b-th>
            </b-tr>
          </b-thead>
          <!-- 검색 전: 게시판 -->
          <tbody v-if="filterArticles == 'null'">
            <board-list-row
              v-for="(article, index) in articles"
              :key="index"
              :article="article"
            />
          </tbody>
          <!-- 검색 후: 데이터가 있을 경우 -->
          <tbody v-else-if="filterArticles.length > 0">
            <board-list-row
              v-for="(article, index) in filterArticles"
              :key="index"
              :article="article"
            />
          </tbody>
          <tbody v-else>
            <b-td colspan="5">
              <b-alert variant="warning" show
                >데이터가 존재하지 않습니다.
              </b-alert></b-td
            >
          </tbody>
        </b-table-simple>
      </b-col>
      <!-- 게시판에 글이 없을 때 -->
      <b-col v-else
        ><b-alert variant="danger" show>작성된 글이 없습니다. </b-alert></b-col
      >
    </b-row>
    <b-row class="mt-4 mb-4 text-center">
      <b-col cols="2">
        <b-form-select v-model="key" :options="keys"></b-form-select>
      </b-col>
      <b-col cols="8">
        <b-form-input v-model="val"></b-form-input>
      </b-col>
      <b-col cols="2">
        <b-button id="searchBtn" @click="searchBtn">검색</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import BoardListRow from "@/components/board/child/BoardListRow";
import { mapActions, mapMutations, mapState } from "vuex";
const boardStore = "boardStore";
const wordStore = "wordStore";

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
    };
  },
  components: {
    BoardListRow,
  },
  created() {
    this.getlistArticle();
    this.CLEAR_FILTER_ARTICLE();
  },
  methods: {
    ...mapActions(wordStore, ["addWord"]),
    ...mapActions(boardStore, ["getlistArticle", "SearchArticle"]),
    ...mapMutations(boardStore, ["CLEAR_FILTER_ARTICLE", "SET_FILTER_ARTICLE"]),
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
  },
  watch: {
    key: function () {
      if (this.key == null) this.CLEAR_FILTER_ARTICLE();
    },
  },
  computed: {
    ...mapState(boardStore, ["articles", "filterArticles"]),
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
</style>
