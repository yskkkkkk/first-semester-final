<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col v-if="replylist.length > 0" class="px-0">
        <!-- 하위 component인 ListRow에 데이터 전달(props) -->
        <reply-list-row
          v-for="(reply, index) in replylist"
          :key="index"
          :replyprops="reply"
        />
      </b-col>
      <b-col v-else class="text-center" style="padding: 0">
        <b-card>아직 댓글이 없습니다.</b-card></b-col
      >
    </b-row>
  </b-container>
</template>

<script>
import ReplyListRow from "@/components/reply/child/ReplyListRow";
import { mapActions, mapState } from "vuex";
const replyStore = "replyStore";

export default {
  name: "ReplyList",
  components: {
    ReplyListRow,
  },
  created() {
    this.getlistReply(this.$route.params.boardNo);
  },
  methods: {
    ...mapActions(replyStore, ["getlistReply"]),
  },
  computed: {
    ...mapState(replyStore, ["replylist"]),
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>
