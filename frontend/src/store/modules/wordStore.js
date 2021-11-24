import { listWord, registWord } from "@/api/word.js";

const wordStore = {
  namespaced: true,
  state: {
    wordlist: [],
  },
  mutations: {
    SET_WORD_LIST: (state, list) => {
      state.wordlist = list;
    },
    CLEAR_WORD_LIST: (state) => {
      state.wordlist = [];
    },
    GOOD_RESULT: () => {
      // console.log("success");
    },
  },
  actions: {
    getlistWord({ commit }) {
      listWord(
        ({ data }) => {
          let res = [];
          var i = 1;
          for (let row of data) {
            if (i <= 2) res.push({ text: row.word, size: 50 });
            else if (3 <= i && i <= 5) res.push({ text: row.word, size: 40 });
            else if (6 <= i && i <= 8) res.push({ text: row.word, size: 30 });
            else res.push({ text: row.word, size: 20 });
            i++;
          }
          commit("SET_WORD_LIST", res);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    addWord({ commit }, word) {
      const param = {
        concerns: word,
      };
      // console.log(param);
      registWord(
        param,
        ({ data }) => {
          commit("GOOD_RESULT", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default wordStore;
