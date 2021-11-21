<template>
  <b-row class="mt-4 mb-4 text-center">
    <b-col class="xs-12 ml-mr-auto">
      <b-form-select
        v-model="sidoCode"
        :options="sidos"
        @change="gugunList"
      ></b-form-select>
    </b-col>
    <b-col class="xs-12 ml-mr-auto">
      <b-form-select
        v-model="gugunCode"
        :options="guguns"
        @change="dongList"
      ></b-form-select>
    </b-col>
    <b-col class="xs-12 ml-mr-auto">
      <b-form-select
        v-model="dongCode"
        :options="dongs"
        @change="searchApt"
      ></b-form-select>
    </b-col>
  </b-row>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

/*
  namespaced: true를 사용했기 때문에 선언해줍니다.
  index.js 에서 modules 객체의 '키' 이름입니다.

  modules: {
    키: 값
    memberStore: memberStore,
    houseStore: houseStore
  }
*/
const houseStore = "houseStore";

export default {
  name: "SelectLocation",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
    };
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs"]),
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
    this.CLEAR_HOUSES_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
      "getHouseListByDong",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      "CLEAR_HOUSES_LIST",
    ]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      // 시 선택했을 때
      this.CLEAR_HOUSES_LIST();
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.gugunCode = null;
      this.dongCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      // 구/군 선택했을 때
      this.CLEAR_HOUSES_LIST();
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      console.log(this.gugunCode + " " + this.dongCode);

      if (this.gugunCode) {
        this.getDong(this.gugunCode);
        this.getHouseList(this.gugunCode);
      }
    },
    searchApt() {
      // 동 선택했을 때
      this.CLEAR_HOUSES_LIST();
      console.log(this.gugunCode + " " + this.dongCode);
      // if (!this.dongCode) this.getHouseList(this.gugunCode);
      this.getHouseListByDong({
        gugunCode: this.gugunCode,
        dongName: this.dongCode,
      });
    },
  },
};
</script>

<style></style>
