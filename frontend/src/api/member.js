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

// function logout(success, fail)

export { login, findById };
