import { NewsList } from "@/api/news.js";

const newsStore = {
  namespaced: true,
  state: {
    newslist: [],
  },
  getters: {},
  mutations: {
    SET_NEWS_LIST: (state, list) => {
      state.newslist = list;
    },
    CLEAR_NEWS_LIST: (state) => {
      state.newslist = [];
    },
  },
  actions: {
    getNewsList: ({ commit }) => {
      NewsList(({ data }) => {
        // console.log(data);
        commit("SET_NEWS_LIST", data);
      }),
        (error) => {
          console.log(error);
        };
    },
  },
};

export default newsStore;
