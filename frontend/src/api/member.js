import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
  // !!!!!! 헤더에 토큰 가져가고, path-variable로 아이디 가져감!!!!!!!!!!!
}

async function checkId(userid, success, fail) {
  await api.post(`/user/id`, userid).then(success).catch(fail);
}

async function checkEmail(useremail, success, fail) {
  await api.post(`/user/email`, useremail).then(success).catch(fail);
}

async function join(user, success, fail) {
  await api.post(`/user/`, JSON.stringify(user)).then(success).catch(fail);
}

async function update(user, success, fail) {
  await api.put(`/user/`, JSON.stringify(user)).then(success).catch(fail);
}

async function remove(params, success, fail) {
  await api.delete(`/user/`, { params: params }).then(success).catch(fail);
}

async function userCheck(user, success, fail) {
  await api.post(`/user/check`, JSON.stringify(user)).then(success).catch(fail);
}

async function temporaryPassword(user, success, fail) {
  await api.put(`/user/pw`, JSON.stringify(user)).then(success).catch(fail);
}

export {
  login,
  findById,
  checkId,
  checkEmail,
  join,
  update,
  remove,
  userCheck,
  temporaryPassword,
};
