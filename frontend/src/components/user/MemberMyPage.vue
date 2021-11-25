<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-jumbotron>
          <template #header>My Page</template>

          <template #lead> 내 정보 확인페이지입니다. </template>

          <hr class="my-4" />

          <div class="mt-4" id="mypage-wrapper">
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">아이디</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.userId }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이름</b-col
              ><b-col cols="4" align-self="start">{{
                userInfo.userName
              }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이메일</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.email }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">가입일</b-col
              ><b-col cols="4" align-self="start">{{
                userInfo.joinDate
              }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
          </div>
          <hr class="my-4" />

          <b-button variant="primary" class="mr-1" @click="moveUpdate"
            >정보수정</b-button
          >
          <!-- <b-button variant="danger" @click="deleteMember">회원탈퇴</b-button> -->
          <b-button variant="danger" @click="confirmDelete">회원탈퇴</b-button>
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { app } from "@/main";
import { mapState, mapActions, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberMyPage",
  data() {
    return {
      deleteResult: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userDelete"]),
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    moveUpdate() {
      this.$router.push({ name: "Update" });
    },
    confirmDelete() {
      this.deleteResult = "";
      this.$bvModal.msgBoxConfirm("정말 탈퇴하시겠습니까?").then((value) => {
        this.deleteResult = value;
      });
    },
    deleteMember() {
      this.userDelete(this.userInfo.userNo);
      app.$bvToast.toast("탈퇴 되었습니다.", {
        title: "안내",
        variant: "info",
        solid: true,
      });
      // alert("탈퇴 되었습니다.");
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/") this.$router.push({ name: "Home" });
    },
  },
  watch: {
    deleteResult: function (val) {
      if (val == true) {
        this.deleteMember();
      }
    },
  },
};
</script>

<style scoped>
#mypage-wrapper div.row {
  padding-bottom: 10px;
}
.container {
  min-height: 800px;
}
</style>
