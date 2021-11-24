import jwt_decode from "jwt-decode";
import {
  login,
  checkId,
  checkEmail,
  join,
  update,
  remove,
  issuancePassword,
} from "@/api/member.js";
import { findById } from "../../api/member";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    GOOD_RESULT: () => {
      // console.log();
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          // console.log(response);
          if (response.data.message === "success") {
            // 성공 시 토큰을 가져옴
            let token = response.data["access-token"];
            // data 내부에 있는 access-token을 가져옴 (배열 아님 : 대시 때문에 이렇게 가져온거)
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      // 토큰을 디코드 함
      findById(
        // 토큰 내부에 있던 userid 가져옴
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    isDuplicatedId: ({ commit }, userId) => {
      return checkId(
        userId,
        ({ data }) => {
          commit("GOOD_RESULT");
          return data;
        },
        ({ data }) => {
          return data;
        }
      );
    },
    isDuplicatedEmail: ({ commit }, userEmail) => {
      return checkEmail(
        userEmail,
        ({ data }) => {
          commit("GOOD_RESULT");
          return data;
        },
        ({ data }) => {
          return data;
        }
      );
    },
    userJoin: ({ commit }, user) => {
      const params = {
        userId: user.userid,
        userPw: user.userpwd,
        userName: user.username,
        email: user.useremail,
      };
      join(
        params,
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    userUpdate({ commit }, user) {
      update(
        user,
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    userDelete({ commit }, userId) {
      const params = {
        userId: userId,
      };
      remove(
        params,
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    changePassword({ commit }, email) {
      issuancePassword(
        email,
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default memberStore;
