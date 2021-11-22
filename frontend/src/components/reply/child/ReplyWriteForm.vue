<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-row>
          <b-col cols="10">
            <!-- 로그인 구현 완료되면 유저 이름 넣기 -->
            <b-form-group id="content-group" :label="reply.writer">
              <b-form-textarea
                id="content"
                v-model="reply.content"
                placeholder="댓글 입력..."
                rows="2"
              ></b-form-textarea>
            </b-form-group>
          </b-col>
          <b-col cols="2" class="btn-group">
            <button type="submit" v-if="this.type === 'register'">작성</button>
            <div v-else>
              <button type="submit">수정</button>
              <button>취소</button>
            </div>
          </b-col>
        </b-row>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
// import { writeArticle, getArticle, modifyArticle } from "@/api/board";
import { mapState, mapActions, mapGetters } from "vuex";
const replyStore = "replyStore";
const boardStore = "boardStore";

export default {
  name: "ReplyWriteForm",
  data() {
    return {
      reply: {
        boardNo: 0,
        replyNo: 0,
        writer: "ssafy",
        // !!!!로그인 구현 완료되면 유저 정보 가져오기!!!
        content: "",
        recommand: 0,
      },
    };
  },
  props: {
    type: String,
  },
  created() {
    // console.log(this.type);
    if (this.type === "modify") {
      this.reply = this.getReply;
      // console.log(this.getReply);
      this.isUserid = true;
    }
  },
  methods: {
    ...mapActions(replyStore, [
      "writeReply",
      "updateArticleByNo",
      "getlistReply",
    ]),
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      if (!this.reply.content) {
        msg = "내용 입력해주세요";
        err = false;
      }

      if (!err) alert(msg);
      else this.registReply();
    },
    onReset(event) {
      event.preventDefault();
      this.reply.replyNo = 0;
      this.reply.content = "";
      this.$router.push({ name: "BoardList" });
    },
    registReply() {
      const params = {
        boardNo: this.article.boardNo,
        writer: this.reply.writer,
        content: this.reply.content,
        recommand: this.reply.recommand,
      };
      // console.log(params);
      alert("댓글이 등록되었습니다.");
      this.writeReply(params);
      this.getlistReply(this.article.boardNo);
    },
  },
  computed: {
    ...mapGetters(replyStore, ["getReply"]),
    ...mapState(boardStore, ["article"]),
  },
};
</script>

<style scoped>
.btn-group {
  display: flex;
  flex-direction: row;
  align-items: center;
  align-content: center;
  justify-content: flex-end;
}
</style>
