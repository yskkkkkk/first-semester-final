import { apiInstance } from "./index.js";

const api = apiInstance();

function openGraph(url) {
  const param = {
    param: url,
  };
  return api
    .get(`/getOpenGraph`, { params: param })
    .then((res) => {
      return res;
    })
    .catch((err) => {
      console.log(err);
    });
}

export { openGraph };
