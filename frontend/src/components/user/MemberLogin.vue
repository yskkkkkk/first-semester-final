<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>로그인</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userId"
                required
                placeholder="아이디 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userPw"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="confirm"
              >로그인</b-button
            >
            <b-button
              type="button"
              variant="success"
              class="m-1"
              @click="movePage"
              >회원가입</b-button
            >
            <b-button
              type="button"
              variant="danger"
              class="m-1"
              v-b-modal.modal-prevent-closing
              >비밀번호찾기</b-button
            >
            <b-modal
              id="modal-prevent-closing"
              ref="modal"
              title="임시 비밀번호 발급"
              @show="resetModal"
              @hidden="resetModal"
              @ok="tmpPW"
            >
              <b-form-group
                label="가입 시 기입한 이메일을 입력해주세요."
                label-for="email"
              >
                <b-form-input
                  id="useremail"
                  v-model="user.email"
                  required
                  placeholder="ex) ssafy@ssafy.com"
                  @keyup.enter="tmpPW"
                ></b-form-input>
              </b-form-group>
            </b-modal>
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
  name: "MemberLogin",
  data() {
    return {
      user: {
        userId: null,
        userPw: null,
        email: "",
      },
    };
  },
  computed: {
    // store도 나뉘어져 있기 때문에 어떤 곳에서 가져왔는지 명시를 해줘야 함
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(memberStore, [
      "userConfirm",
      "getUserInfo",
      "changePassword",
    ]),
    async confirm() {
      await this.userConfirm(this.user);
      console.log(this.user);
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
    movePage() {
      this.$router.push({ name: "SignUp" });
    },
    async tmpPW() {
      this.changePassword(this.user.email).then(function (data) {
        if (data == "OK")
          alert("임시비밀번호가 발송되었습니다. 로그인 후 꼭 변경해주세요.");
        else alert("정보가 없는 이메일입니다.");
      });
      this.$bvModal.hide("modal-prevent-closing");
    },
    resetModal() {
      this.email = "";
    },
  },
};
</script>

<style></style>
