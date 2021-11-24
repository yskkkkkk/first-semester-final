<template>
  <b-tr
    @click="confirmStatus(article)"
    :variant="article.isNotice == '1' ? 'info' : 'null'"
  >
    <b-td v-if="article.isNotice == '0'" class="text-center">
      {{ articleIndex }}
    </b-td>
    <b-td v-else class="text-center">
      <b-icon icon="chat-right-dots"></b-icon>
    </b-td>
    <b-th class="text-left">
      <b-icon v-if="article.isExposing == '0'" icon="lock-fill"></b-icon>
      {{ article.title }}
    </b-th>
    <b-td>{{ article.writer }}</b-td>
    <b-td class="text-center">{{ article.regTime }}</b-td>
    <b-td class="text-center">{{ article.readCount }}</b-td>
  </b-tr>
</template>

<script>
// import moment from "moment";
import { mapState, mapMutations, mapActions } from "vuex";
const boardStore = "boardStore";
const memberStore = "memberStore";

export default {
  name: "BoardListRow",
  props: {
    article: Object,
    index: Number,
    length: Number,
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    articleIndex: function () {
      return this.length - this.index;
    },
    // changeDateFormat() {
    //   return moment(new Date(this.regtime)).format("YY.MM.DD hh:mm:ss");
    // },
  },
  methods: {
    ...mapMutations(boardStore, ["SET_ARTICLE"]),
    ...mapActions(boardStore, ["increaseHit"]),
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
};
</script>

<style></style>
