import {
  sidoList,
  gugunList,
  dongList,
  houseList,
  searchList,
  dongCode,
} from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "시를 선택하세요" }],
    guguns: [{ value: null, text: "구/군을 선택하세요" }],
    dongs: [{ value: null, text: "동을 선택하세요" }],
    houses: [],
    housesfilter: [],
    house: null,
    dongcodes: [],
  },

  getters: {
    getHouseOrder: (state) => (order) => {
      console.log(order);
      let result = [];
      if (order == "new") {
        result = state.houses.sort(function (a, b) {
          return (
            Number(b.dealMonth.concat(b.dealDay)) -
            Number(a.dealMonth.concat(a.dealDay))
          );
        });
      } else if (order == "price") {
        result = state.houses.sort(function (a, b) {
          return (
            Number(a.dealAmount.trim().replace(",", "")) -
            Number(b.dealAmount.trim().replace(",", ""))
          );
        });
      } else if (order == "area") {
        result = state.houses.sort(function (a, b) {
          return Number(a.area.trim()) - Number(b.area.trim());
        });
      }
      return result;
    },
  },

  mutations: {
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongName, text: dong.dongName });
      });
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ value: null, text: "시를 선택하세요" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ value: null, text: "구/군을 선택하세요" }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ value: null, text: "동을 선택하세요" }];
    },
    CLEAR_HOUSE: (state) => {
      state.house = null;
    },
    CLEAR_DONGS_CODE: (state) => {
      state.dongcodes = [];
    },
    CLEAR_HOUSES_LIST: (state) => {
      state.houses = [];
    },
    SET_HOUSE_LIST: (state, houses) => {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE: (state, house) => {
      state.house = house;
    },
    SET_DONGS_CODE: (state, codes) => {
      state.dongcodes = codes;
    },
    SET_HOUSE_FILTER: (state, name) => {
      let result = state.houses.filter((house) =>
        house.houseinfo.aptName.includes(name)
      );
      state.housesfilter = result;
    },
    CLEAR_HOUSES_FILTER: (state) => {
      state.housesfilter = [];
    },
  },

  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          // console.log(data);
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      const params = {
        sido: sidoCode,
      };
      gugunList(
        // 인자값으로 처리함
        // api/house.js를 보면 params, success, fail로 나뉘어져 있음
        // 성공했을 때 커밋하고, 실패했을 때 에러 출력하도록
        params,
        ({ data }) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDong: ({ commit }, gugunCode) => {
      const params = {
        gugun: gugunCode,
      };
      dongList(
        params,
        ({ data }) => {
          commit("SET_DONG_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDongCode: ({ commit }, dongName) => {
      const params = {
        dongName: dongName,
      };
      dongCode(
        params,
        (response) => {
          commit("SET_DONGS_CODE", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseList: ({ commit }, gugunCode) => {
      const params = {
        dong: gugunCode + "00000",
      };
      houseList(
        params,
        (response) => {
          console.log(response);
          commit("SET_HOUSE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseListByDong: ({ commit }, payload) => {
      const params = {
        dong: payload.gugunCode + "00000",
      };
      houseList(
        params,
        (response) => {
          var ret = [];
          for (let item of response.data) {
            if (item.houseinfo.dongName.trim().includes(payload.dongName))
              ret.push(item);
          }
          console.log(ret);
          commit("SET_HOUSE_LIST", ret);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    detailHouse: ({ commit }, house) => {
      commit("SET_DETAIL_HOUSE", house);
    },
    getSearchList: ({ commit }, payload) => {
      const params = {
        key: payload.key,
        value: payload.value,
        order: payload.order,
      };
      searchList(
        params,
        ({ data }) => {
          commit("SET_HOUSE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // searchByApart: ({ commit }, payload) => {
    //   const params = {
    //     key: "apart",
    //     value: payload.AptName,
    //   };
    //   searchApt(
    //     params,
    //     ({ data }) => {
    //       commit("SET_HOUSE_LIST", data);
    //     },
    //     (error) => {
    //       console.log(error);
    //     }
    //   );
    // },
  },
};

export default houseStore;
