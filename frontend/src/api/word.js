import { apiInstance } from "./index.js";

const api = apiInstance();

function listWord(success, fail) {
  api.get(`/word/list`).then(success).catch(fail);
}

function registWord(params, success, fail) {
  api.post(`/word/regist`, JSON.stringify(params)).then(success).catch(fail);
}

export { listWord, registWord };
