<template>
  <b-tr>
    <b-td>{{ article.boardNo }}</b-td>
    <b-th class="text-left" @click="BoardView(article)">
      {{ article.title }}
    </b-th>
    <b-td>{{ article.readCount }}</b-td>
    <b-td>{{ article.writer }}</b-td>
    <b-td>{{ article.regTime }}</b-td>
  </b-tr>
</template>

<script>
// import moment from "moment";
import { mapMutations } from "vuex";
const boardStore = "boardStore";

export default {
  name: "BoardListRow",
  props: {
    article: Object,
  },
  created() {
    // 들어올 때마다 조회수 증가
  },
  computed: {
    // changeDateFormat() {
    //   return moment(new Date(this.regtime)).format("YY.MM.DD hh:mm:ss");
    // },
  },
  methods: {
    ...mapMutations(boardStore, ["SET_ARTICLE"]),
    BoardView(article) {
      this.SET_ARTICLE(article);
      this.$router.push({
        name: "BoardView",
        params: { articleno: article.boardNo },
      });
    },
  },
};
</script>

<style></style>
