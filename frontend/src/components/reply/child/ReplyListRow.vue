<template>
  <b-card class="reply" v-if="this.type == 'view'">
    <b-row>
      <span style="font-weight: bold">{{ replyprops.writer }}</span>
      <span>( {{ replyprops.regTime }} )</span>
    </b-row>
    <b-row>
      <span>{{ replyprops.content }}</span>
    </b-row>
    <b-row class="replyBtn">
      <button @click="convertModify">수정</button>
      <button @click="replyRemove">삭제</button>
    </b-row>
  </b-card>

  <!-- 수정 시 -->
  <b-card class="reply" style="padding: 0px" v-else>
    <b-row>
      <b-col cols="10">
        <!-- 로그인 구현 완료되면 유저 이름 넣기 -->
        <b-form-group id="content-group" :label="replyprops.writer">
          <b-form-textarea
            id="content"
            v-model="content"
            placeholder="댓글 입력..."
            rows="2"
          ></b-form-textarea>
        </b-form-group>
      </b-col>
      <b-col cols="2" class="btn-group">
        <div>
          <button @click="confirmReply">수정</button>
          <button @click="replyview">취소</button>
        </div>
      </b-col>
    </b-row>
  </b-card>
</template>

<script>
import { mapActions } from "vuex";
const replyStore = "replyStore";

export default {
  name: "ReplyListRow",
  props: {
    replyprops: Object,
  },
  data() {
    return {
      type: "view",
      content: "",
    };
  },
  methods: {
    ...mapActions(replyStore, ["getlistReply", "modifyReply", "removeReply"]),
    convertModify() {
      this.type = "modify";
      this.content = this.replyprops.content;
    },
    replyview() {
      this.type = "view";
    },
    confirmReply() {
      let err = true;
      let msg = "";
      if (!this.content) {
        msg = "내용을 입력해주세요";
        err = false;
      }
      if (!err) alert(msg);
      else this.replyUpdate();
    },
    replyUpdate() {
      const params = {
        replyNo: this.replyprops.replyNo,
        content: this.content,
        recommand: this.replyprops.recommand,
      };
      this.replyview();
      this.modifyReply(params);
      alert("댓글이 수정되었습니다.");
      this.getlistReply(this.replyprops.boardNo);
    },
    replyRemove() {
      this.removeReply(this.replyprops.replyNo);
      alert("댓글이 삭제되었습니다.");
      this.getlistReply(this.replyprops.boardNo);
    },
  },
  computed: {
    // changeDateFormat() {
    //   return moment(new Date(this.regtime)).format("YY.MM.DD hh:mm:ss");
    // },
  },
};
</script>

<style scoped>
.reply {
  align-items: flex-start;
  text-align: left;
  padding: 0px 10px;
}
.reply span {
  padding-right: 10px;
}
.card-body {
  width: 100%;
}
.replyBtn {
  justify-content: flex-end;
}
.btn-group {
  display: flex;
  flex-direction: row;
  align-items: center;
  align-content: center;
  justify-content: flex-end;
}
</style>
