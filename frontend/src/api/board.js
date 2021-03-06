import { apiInstance } from "./index.js";

const api = apiInstance();

function listArticle(param, success, fail) {
  api.post(`/board/list`, JSON.stringify(param)).then(success).catch(fail);
}

function writeArticle(article, success, fail) {
  api.post(`/board/write`, JSON.stringify(article)).then(success).catch(fail);
}

function getArticle(articleno, success, fail) {
  api.get(`/board/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  api.put(`/board/update`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  api.delete(`/board/${articleno}`).then(success).catch(fail);
}

function increaseHit(boardNo, success, fail) {
  api.put(`/board/readCount/${boardNo}`).then(success).catch(fail);
}

function isLiked(param, success, fail) {
  return api
    .post(`/board/like/`, JSON.stringify(param))
    .then(success)
    .catch(fail);
}

function likeChanged(param, success, fail) {
  return api
    .put(`/board/like/`, JSON.stringify(param))
    .then(success)
    .catch(fail);
}

export {
  listArticle,
  writeArticle,
  getArticle,
  modifyArticle,
  deleteArticle,
  increaseHit,
  isLiked,
  likeChanged,
};
