import { apiInstance } from "./index.js";

const api = apiInstance();

function NewsList(success, fail) {
  api.get(`/news/list`).then(success).catch(fail);
}

export { NewsList };
