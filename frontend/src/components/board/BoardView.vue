<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="listArticle">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyArticle"
          class="mr-2"
          >글수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="removeArticle"
          >글삭제</b-button
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
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
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

export default {
  name: "BoardView",
  components: { ReplyList, ReplyWriteForm },
  created() {
    setTimeout(() => {
      this.getArticleByNo(this.$route.params.boardNo);
    }, 100);
  },
  methods: {
    ...mapActions(boardStore, [
      "getArticleByNo",
      "deleteArticleByNo",
      "getlistArticle",
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
      if (confirm("정말로 삭제하시겠습니까?")) {
        this.deleteArticleByNo(this.$route.params.boardNo);
        this.getlistArticle(); // 삭제하고 리스트 새로 받아오기
        alert("삭제 되었습니다.");
        this.listArticle();
      }
    },
  },
  computed: {
    ...mapState(boardStore, ["article"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
    // changeDateFormat() {
    //   return moment(new Date(this.article.regtime)).format(
    //     "YYYY.MM.DD hh:mm:ss"
    //   );
    // },
  },
};
</script>

<style scoped></style>
