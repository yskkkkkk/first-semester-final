<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          id="userid-group"
          label="작성자:"
          label-for="userid"
          description="작성자를 입력하세요."
        >
          <b-form-input
            id="userid"
            :disabled="isUserid"
            v-model="article.writer"
            type="text"
            required
            placeholder="작성자 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="subject-group"
          label="제목:"
          label-for="title"
          description="제목을 입력하세요."
        >
          <b-form-input
            id="title"
            v-model="article.title"
            type="text"
            required
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <b-form-checkbox
          v-model="article.isExposing"
          value="0"
          unchecked-value="1"
        >
          비공개로 작성
        </b-form-checkbox>
        <b-button
          type="submit"
          variant="primary"
          class="m-1"
          v-if="this.type === 'register'"
          >글작성</b-button
        >
        <b-button type="submit" variant="primary" class="m-1" v-else
          >글수정</b-button
        >
        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
// import { writeArticle, getArticle, modifyArticle } from "@/api/board";
import { mapActions, mapGetters } from "vuex";
const boardStore = "boardStore";

export default {
  name: "BoardWriteForm",
  data() {
    return {
      article: {
        boardNo: 0,
        title: "",
        writer: "",
        content: "",
        isExposing: "1", // 1이면 공개, 0이면 비공개
        isNotice: "0", // 1이면 공지, 0이면 일반글
      },
      isUserid: false,
      type: this.$route.params.type,
    };
  },
  created() {
    // console.log(this.type);
    if (this.type === "modify") {
      this.article = this.getArticle;
      this.isUserid = true;
    }
  },
  methods: {
    ...mapActions(boardStore, [
      "writeArticle",
      "updateArticleByNo",
      "getlistArticle",
    ]),
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      if (!this.article.writer) {
        msg = "작성자 입력해주세요";
        err = false;
      } else if (!this.article.title) {
        msg = "제목 입력해주세요";
        err = false;
      } else if (!this.article.content) {
        msg = "내용 입력해주세요";
        err = false;
      }

      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.updateArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.boardNo = 0;
      this.article.title = "";
      this.article.content = "";
      this.$router.push({ name: "BoardList" });
    },
    registArticle() {
      const params = {
        content: this.article.content,
        isExposing: this.article.isExposing,
        isNotice: this.article.isNotice,
        title: this.article.title,
        writer: this.article.writer,
      };
      this.writeArticle(params);
      this.getlistArticle();
      alert("글이 등록되었습니다.");
      this.moveList();
    },
    updateArticle() {
      const params = {
        boardNo: this.article.boardNo,
        content: this.article.content,
        isExposing: this.article.isExposing,
        isNotice: this.article.isNotice,
        title: this.article.title,
      };
      this.updateArticleByNo(params);
      this.getlistArticle();
      alert("글이 수정되었습니다.");
      this.$router.push({
        name: "BoardView",
        params: { boardNo: this.article.boardNo },
      });
    },
    moveList() {
      this.$router.push({ name: "BoardList" });
    },
  },
  computed: {
    ...mapGetters(boardStore, ["getArticle"]),
  },
};
</script>

<style></style>
