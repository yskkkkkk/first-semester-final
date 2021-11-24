<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="listArticle">목록</b-button>
      </b-col>
      <b-col class="text-right" v-if="article.writer == userInfo.userName">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyArticle"
          class="mr-2"
          >글 수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="removeArticle"
          >글 삭제</b-button
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h5 class='mt-2'>
            [${article.boardNo}] ${article.title}
          </h5>
          <br/>
          <v-row style='width: 100%; display: flex; justify-content: space-between'>
            <v-col cols='4'>
              <span style='font-weight: bolder; margin-right: 10px;'>작성자</span> <span>${article.writer}</span>
            </v-col>
            <v-col cols='4'>
              <span style='font-weight: bolder; margin-right: 10px;'>조회수</span> <span>${article.readCount}</span>
            </v-col>
            <v-col cols='4'>
              <span style='font-weight: bolder; margin-right: 10px;'>작성시간</span> <span>${article.regTime}</span>
            </v-col>
          </v-row>`"
          class="mb-2"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
            <br />
            <div class="likeBtn">
              <b-button
                pill
                @click="likeClicked"
                :variant="this.isliked ? 'info' : 'outline-info'"
              >
                <b-icon
                  :icon="this.isliked ? 'heart-fill' : 'heart'"
                  class="mr-1"
                ></b-icon
                >{{ getlikeCnt }}
              </b-button>
            </div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-card>
          <reply-write-form type="register"></reply-write-form>
        </b-card>
        <reply-list></reply-list>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
// import moment from "moment";
import ReplyWriteForm from "@/components/reply/child/ReplyWriteForm.vue";
import ReplyList from "@/components/reply/ReplyList.vue";
import { mapActions, mapState } from "vuex";
const boardStore = "boardStore";
const memberStore = "memberStore";

export default {
  name: "BoardView",
  components: { ReplyList, ReplyWriteForm },
  data() {
    return {
      isliked: null,
    };
  },
  created() {
    setTimeout(() => {
      this.getArticleByNo(this.$route.params.boardNo);
      const params = {
        boardNo: this.$route.params.boardNo,
        userNo: this.userInfo.userNo,
      };
      this.getIsLiked(params).then((ret) => (this.isliked = ret));
    }, 100);
  },
  methods: {
    ...mapActions(boardStore, [
      "getArticleByNo",
      "deleteArticleByNo",
      "getlistArticle",
      "getIsLiked",
      "toggleLike",
    ]),
    listArticle() {
      this.$router.push({ name: "BoardList" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "BoardUpdate",
        params: { boardNo: this.$route.params.boardNo, type: "modify" },
      });
    },
    removeArticle() {
      if (confirm("정말 삭제하시겠습니까?")) {
        this.deleteArticleByNo(this.$route.params.boardNo);
        setTimeout(() => {
          this.getlistArticle(); // 삭제하고 리스트 새로 받아오기
        }, 100);
        alert("삭제 되었습니다.");
        this.listArticle();
      }
    },
    likeClicked() {
      const params = {
        boardNo: this.$route.params.boardNo,
        userNo: this.userInfo.userNo,
      };
      this.toggleLike(params).then((res) => (this.isliked = res));

      setTimeout(() => {
        this.getArticleByNo(this.$route.params.boardNo);
      }, 100);
    },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(boardStore, ["article"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
    getlikeCnt: function () {
      if (
        this.article.recommand == null ||
        this.article.recommand == "" ||
        this.article.recommand == 0
      )
        return 0;
      else {
        const cnt = this.article.recommand.split(",");
        return cnt.length - 1;
      }
    },
    getContentBr: function () {
      return this.article.content.replace("\n", "<br />");
    },
  },
};
</script>

<style scoped>
.likeBtn {
  display: flex;
  justify-content: flex-end;
}
</style>
