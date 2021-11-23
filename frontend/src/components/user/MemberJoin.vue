<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원가입</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isJoinError"
              >비밀번호확인이 맞지않습니다.</b-alert
            >
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userid"
                required
                placeholder="아이디를 입력해주세요."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userpwd"
                required
                placeholder="영어와 숫자를 조합하여 6자리 이상"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호 확인:" label-for="pwdcheck">
              <b-form-input
                type="password"
                id="pwdcheck"
                v-model="user.pwdcheck"
                required
                placeholder="한번 더 입력해주세요."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이름:" label-for="username">
              <b-form-input
                id="username"
                v-model="user.username"
                required
                placeholder="happyhouse에서 사용할 이름"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="useremail">
              <b-form-input
                id="useremail"
                v-model="user.useremail"
                required
                placeholder="ex) ssafy@ssafy.com"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="confirm"
              >회원가입</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberJoin",
  data() {
    return {
      user: {
        userid: null,
        userpwd: null,
        username: null,
        useremail: null,
      },
    };
  },
  computed: {
    // store도 나뉘어져 있기 때문에 어떤 곳에서 가져왔는지 명시를 해줘야 함
    ...mapState(memberStore, ["isLogin", "isJoinError"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      // this.user = 내가 입력한 로그인 정보
      // this.userConfirm에서 토큰을 받아온 상태
      // 정상적으로 로그인을 했다면 isLogin이 true인 상태
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        // 받아온 간단한 정보로 다시 getUserInfo 호출
        this.$router.push({ name: "Home" });
      }
    },
  },
};
</script>

<style></style>
