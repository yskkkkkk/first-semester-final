import {
  listArticle,
  writeArticle,
  getArticle,
  modifyArticle,
  deleteArticle,
  increaseHit,
  isLiked,
  likeChanged,
} from "@/api/board.js";

const boardStore = {
  namespaced: true,
  state: {
    article: null,
    articles: [],
    filterArticles: "null",
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
    GOOD_RESULT: () => {
      // console.log("SUCCESS");
    },
    SET_FILTER_ARTICLE: (state, res) => {
      state.filterArticles = res;
    },
    CLEAR_FILTER_ARTICLE: (state) => {
      state.filterArticles = "null";
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
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    deleteArticleByNo: ({ commit }, boardNo) => {
      deleteArticle(
        boardNo,
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    updateArticleByNo: ({ commit }, article) => {
      const params = {
        boardNo: article.boardNo,
        content: article.content,
        isExposing: article.isExposing,
        isNotice: article.isNotice,
        title: article.title,
      };

      modifyArticle(
        params,
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    increaseHit: ({ commit }, articleNo) => {
      increaseHit(
        articleNo,
        () => {
          commit("GOOD_RESULT");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    SearchArticle: ({ commit }, params) => {
      listArticle(
        params,
        ({ data }) => {
          // console.log(data);
          commit("SET_FILTER_ARTICLE", data);
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

export default boardStore;
