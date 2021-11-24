<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-row>
          <b-col>
            <h5 class="fontbolder" style="display: inline">댓글</h5>
            ({{ getReplyCnt }}개)
          </b-col>
        </b-row>
        <br />
        <b-row>
          <b-col cols="10">
            <div class="pb-2 fontbolder">
              {{ this.userInfo.userName }} ({{ this.userInfo.userId }})
            </div>
            <!-- 로그인 구현 완료되면 유저 이름 넣기 -->
            <b-form-group id="content-group">
              <b-form-textarea
                id="content"
                v-model="reply.content"
                placeholder="댓글 입력..."
                rows="2"
              ></b-form-textarea>
            </b-form-group>
          </b-col>
          <b-col cols="2" class="btn-group">
            <b-button
              type="submit"
              variant="outline-primary"
              size="sm"
              v-if="this.type === 'register'"
              >작성</b-button
            >
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
import { app } from "@/main";
import { mapState, mapActions, mapGetters } from "vuex";
const replyStore = "replyStore";
const boardStore = "boardStore";
const memberStore = "memberStore";

export default {
  name: "ReplyWriteForm",
  data() {
    return {
      reply: {
        boardNo: 0,
        replyNo: 0,
        writer: "",
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
        writer: this.userInfo.userName,
        content: this.reply.content,
        recommand: this.reply.recommand,
      };
      app.$bvToast.toast("댓글이 등록되었습니다.", {
        title: "안내",
        variant: "info",
        solid: true,
      });
      // alert("댓글이 등록되었습니다.");
      this.writeReply(params);
      setTimeout(() => {
        this.getlistReply(this.article.boardNo);
      }, 100);
    },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(replyStore, ["replylist"]),
    ...mapGetters(replyStore, ["getReply"]),
    ...mapState(boardStore, ["article"]),
    getReplyCnt: function () {
      return this.replylist.length;
    },
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
.fontbolder {
  font-weight: bolder;
}
</style>
