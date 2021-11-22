<template>
  <b-tr
    @click="BoardView(article)"
    :variant="article.isNotice == '1' ? 'info' : 'null'"
  >
    <b-td>{{ article.boardNo }}</b-td>
    <b-th class="text-left">
      {{ article.title }}
    </b-th>
    <b-td>{{ article.readCount }}</b-td>
    <b-td>{{ article.writer }}</b-td>
    <b-td>{{ article.regTime }}</b-td>
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
