import {
  listArticle,
  writeArticle,
  getArticle,
  // modifyArticle,
  // deleteArticle,
} from "@/api/board.js";

const boardStore = {
  namespaced: true,
  state: {
    article: null,
    articles: [],
  },
  getters: {
    getArticle: (state) => {
      return state.article;
    },
  },
  mutations: {
    SET_ARTICLES: (state, list) => {
      state.articles = list;
    },
    CLEAR_ARTICLES: (state) => {
      state.articles = [];
    },
    SET_ARTICLE: (state, article) => {
      state.article = article;
    },
    CLEAR_ARTICLE: (state) => {
      state.article = null;
    },
    ADD_ARTICLE: (state, article) => {
      state.articles.push(article);
    },
  },
  actions: {
    getlistArticle: ({ commit }) => {
      const params = {
        title: "",
        writer: "",
      };
      listArticle(
        params,
        ({ data }) => {
          commit("SET_ARTICLES", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getArticleByNo: ({ commit }, boardNo) => {
      getArticle(
        boardNo,
        ({ data }) => {
          console.log(data);
          commit("SET_ARTICLE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    writeArticle: ({ commit }, article) => {
      const params = {
        content: article.content,
        isExposing: article.isExposing,
        isNotice: article.isNotice,
        title: article.title,
        writer: article.writer,
      };
      writeArticle(
        params,
        ({ data }) => {
          console.log(data);
          commit("ADD_ARTICLE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default boardStore;
