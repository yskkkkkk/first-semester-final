<template>
  <b-tr
    @click="BoardView(article)"
    :variant="article.isNotice == '1' ? 'info' : 'null'"
  >
    <b-td class="text-center">{{ article.boardNo }}</b-td>
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
import { mapMutations, mapActions } from "vuex";
const boardStore = "boardStore";

export default {
  name: "BoardListRow",
  props: {
    article: Object,
  },
  computed: {
    // changeDateFormat() {
    //   return moment(new Date(this.regtime)).format("YY.MM.DD hh:mm:ss");
    // },
  },
  methods: {
    ...mapMutations(boardStore, ["SET_ARTICLE"]),
    ...mapActions(boardStore, ["increaseHit"]),
    BoardView(article) {
      // !!!!!!! 로그인 구현되면 유저 아이디 값으로 변경 !!!!!!!!
      // 비공개 글일 때 글쓴이와 유저 정보가 다르면 글 볼 수 없음
      if (article.isExposing == "0" && article.writer != "ssafy") {
        alert("비공개 글입니다.");
      } else {
        this.increaseHit(article.boardNo);
        this.SET_ARTICLE(article);
        this.$router.push({
          name: "BoardView",
          params: { boardNo: article.boardNo },
        });
      }
    },
  },
};
</script>

<style></style>
