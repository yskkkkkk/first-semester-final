<template>
  <b-container class="bv-example-row mt-3">
    <b-row style="display: flex; justify-content: center">
      <b-col cols="8">
        <b-alert variant="secondary" show><h3>정보 수정</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="userInfo.userId"
                required
                placeholder="아이디를 입력해주세요."
                @keyup.enter="confirm"
                disabled
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
              <b-alert show variant="danger" v-if="isJoinError"
                >비밀번호가 일치하지 않습니다.</b-alert
              >
            </b-form-group>
            <b-form-group label="이름:" label-for="username">
              <b-form-input
                id="username"
                v-model="userName"
                required
                placeholder="happyhouse에서 사용할 이름"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="useremail">
              <b-form-input
                id="useremail"
                v-model="user.email"
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
              >변경</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { app } from "@/main";
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberJoin",
  data() {
    return {
      user: {
        userId: null,
        userpwd: null,
        userName: null,
        modifyDate: null,
        email: null,
        pwdcheck: null,
      },
      userName: "",
    };
  },
  created() {
    this.userName = this.userInfo.userName;
    this.user = this.userInfo;
  },
  methods: {
    ...mapActions(memberStore, ["userUpdate"]),
    confirm() {
      let err = true;
      let msg = "";

      if (this.user.userpwd == "") {
        msg = "비밀번호를 입력해주세요";
        err = false;
      } else if (this.isJoinError) {
        msg = "비밀번호가 일치하지 않습니다.";
        err = false;
      } else if (this.userName == "") {
        msg = "이름을 입력해주세요";
        err = false;
      } else if (this.user.email == "") {
        msg = "이메일을 입력해주세요";
        err = false;
      }

      if (!err) this.makeToast("앗!", msg, "warning");
      else {
        this.user.userName = this.userName;
        //console.log(this.user);
        //console.log(this.user.userpwd);
        this.userUpdate(this.user);
        app.$bvToast.toast("정보가 변경되었습니다.", {
          title: "안내",
          variant: "info",
          solid: true,
        });
        // alert("정보가 변경되었습니다.");
        this.$router.push({ name: "MyPage" });
      }
    },
    makeToast(title, msg, variant) {
      this.$bvToast.toast(msg, {
        title: title,
        variant: variant,
        solid: true,
      });
    },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    isJoinError() {
      if (this.user.pwdcheck == null) return false;
      return this.user.userpwd == this.user.pwdcheck ? false : true;
    },
  },
};
</script>

<style></style>
