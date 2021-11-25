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
            <b-form-group label="아이디:" label-for="userId">
              <b-form-input
                id="userId"
                v-model="userId"
                required
                placeholder="아이디를 입력해주세요."
              ></b-form-input>
              <b-alert
                show
                variant="danger"
                v-if="this.duplicatedId && this.userId != ''"
                >이미 사용중인 아이디입니다.</b-alert
              >
              <b-alert
                show
                variant="info"
                v-if="!this.duplicatedId && this.userId != ''"
                >사용 가능한 아이디입니다.</b-alert
              >
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userPw">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="userPw"
                required
                placeholder="영어와 숫자를 조합하여 6자리 이상"
              ></b-form-input>
              <b-alert
                show
                variant="danger"
                v-if="!validPw && this.userPw != ''"
                >비밀번호 형식에 맞지 않습니다.</b-alert
              >
              <b-alert
                show
                variant="info"
                v-else-if="validPw && this.userPw != ''"
                >사용 가능한 비밀번호입니다.</b-alert
              >
            </b-form-group>
            <b-form-group label="비밀번호 확인:" label-for="pwdcheck">
              <b-form-input
                type="password"
                id="pwdcheck"
                v-model="pwdcheck"
                required
                placeholder="한번 더 입력해주세요."
                @keyup.enter="confirm"
              ></b-form-input>
              <b-alert show variant="danger" v-if="notEqualPw"
                >비밀번호가 일치하지 않습니다.</b-alert
              >
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                v-model="email"
                required
                placeholder="ex) ssafy@ssafy.com"
              ></b-form-input>
              <b-alert
                show
                variant="danger"
                v-if="!validEmail && this.email != ''"
                >이메일 형식이 아닙니다.</b-alert
              >
              <b-alert show variant="danger" v-else-if="duplicatedEmail"
                >이미 사용중인 이메일입니다.</b-alert
              >
              <b-alert
                show
                variant="info"
                v-else-if="this.email != '' && validEmail && !duplicatedEmail"
                >사용 가능한 이메일입니다.</b-alert
              >
              <b-form-group label="이름:" label-for="userName">
                <b-form-input
                  id="userName"
                  v-model="userName"
                  required
                  placeholder="happyhouse에서 사용할 이름"
                  @keyup.enter="confirm"
                ></b-form-input>
              </b-form-group>
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
import { app } from "@/main";
import { mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberJoin",
  data() {
    return {
      userId: "",
      userPw: "",
      userName: "",
      pwdcheck: "",
      email: "",
      pwdmsg: "",
      duplicatedEmail: false, // false : 중복된 이메일
      duplicatedId: false, // false : 중복된 이름
    };
  },
  methods: {
    ...mapActions(memberStore, [
      "userJoin",
      "isDuplicatedId",
      "isDuplicatedEmail",
    ]),
    async confirm() {
      const param = {
        userid: this.userId,
        userpwd: this.userPw,
        username: this.userName,
        useremail: this.email,
        pwdcheck: this.pwdcheck,
      };
      await this.userJoin(param);
      app.$bvToast.toast("회원 가입이 되었습니다!", {
        title: "안내",
        variant: "info",
        solid: true,
      });
      // alert("회원 가입이 되었습니다!");
      this.$router.push({ name: "SignIn" });
    },
    checkId() {
      this.isDuplicatedId(this.userId).then((data) => {
        console.log(data);
        if (data != "OK") {
          this.duplicatedId = true;
          console.log("중복된 아이디입니다.");
        } else {
          this.duplicatedId = false;
        }
      });
    },
    checkEmail() {
      this.isDuplicatedEmail(this.email).then((data) => {
        if (data != "OK") {
          this.duplicatedEmail = true;
          console.log("중복된 이메일입니다.");
        } else {
          this.duplicatedEmail = false;
        }
      });
    },
  },
  computed: {
    validEmail() {
      var regExp =
        /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

      if (this.email.match(regExp) != null) return true;
      return false;
    },
    validPw() {
      var chk1 = /^[a-z\d]{6,}$/i; //a-z와 0-9이외의 문자가 있는지 확인
      var chk2 = /[a-z]/i; //적어도 한개의 a-z 확인
      var chk3 = /\d/; //적어도 한개의 0-9 확인
      return (
        chk1.test(this.userPw) &&
        chk2.test(this.userPw) &&
        chk3.test(this.userPw)
      );
    },
    notEqualPw() {
      if (this.pwdcheck == "") return false;
      return this.userPw == this.pwdcheck ? false : true;
    },
  },
  watch: {
    email: function () {
      if (this.validEmail) this.checkEmail();
    },
    userId: function () {
      this.checkId();
    },
  },
};
</script>

<style></style>
