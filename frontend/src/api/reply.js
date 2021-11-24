import { apiInstance } from "./index.js";

const api = apiInstance();

function listReply(boardNo, success, fail) {
  api.get(`/board/reply/${boardNo}`).then(success).catch(fail);
}

function writeReply(reply, success, fail) {
  api.post(`/board/reply`, JSON.stringify(reply)).then(success).catch(fail);
}

function modifyReply(article, success, fail) {
  api.put(`/board/reply`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteReply(replyNo, success, fail) {
  api.delete(`/board/reply/${replyNo}`).then(success).catch(fail);
}

function isLiked(param, success, fail) {
  return api
    .post(`/board/reply/like/`, JSON.stringify(param))
    .then(success)
    .catch(fail);
}

function likeChanged(param, success, fail) {
  return api
    .put(`/board/reply/like/`, JSON.stringify(param))
    .then(success)
    .catch(fail);
}

function replyCnt(boardNo, success, fail) {
  return api.get(`/board/reply/cnt/${boardNo}`).then(success).catch(fail);
}

export {
  listReply,
  writeReply,
  modifyReply,
  deleteReply,
  isLiked,
  likeChanged,
  replyCnt,
};
