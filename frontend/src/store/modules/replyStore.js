import {
  listReply,
  writeReply,
  modifyReply,
  deleteReply,
  isLiked,
  likeChanged,
} from "@/api/reply.js";

const replyStore = {
  namespaced: true,
  state: {
    reply: null,
    replylist: [],
  },
  getters: {
    getReply: (state) => {
      return state.reply;
    },
  },
  mutations: {
    SET_REPLY: (state, reply) => {
      state.reply = reply;
    },
    CLEAR_REPLY: (state) => {
      state.reply = null;
    },
    SET_REPLY_LIST: (state, list) => {
      state.replylist = list;
    },
    CLEAR_REPLY_LIST: (state) => {
      state.replylist = [];
    },
    GOOD_RESULT: () => {
      // console.log("SUCCESS");
    },
  },
  actions: {
    getlistReply({ commit }, boardNo) {
      listReply(
        boardNo,
        ({ data }) => {
          commit("SET_REPLY_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    writeReply({ commit }, reply) {
      writeReply(
        reply,
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    modifyReply({ commit }, reply) {
      const params = {
        replyNo: reply.replyNo,
        content: reply.content,
        recommand: reply.recommand,
      };
      // console.log(params);
      modifyReply(
        params,
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    removeReply({ commit }, replyNo) {
      deleteReply(
        replyNo,
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getIsLiked({ commit }, param) {
      return isLiked(
        param,
        ({ data }) => {
          commit("GOOD_RESULT");
          return data;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    toggleLike({ commit }, param) {
      return likeChanged(
        param,
        ({ data }) => {
          commit("GOOD_RESULT");
          return data;
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default replyStore;
