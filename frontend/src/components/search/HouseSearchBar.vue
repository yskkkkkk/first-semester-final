<template>
  <div>
    <b-row class="mt-4 mb-4 text-center">
      <b-col>
        <b-form-select v-model="key" :options="keys"></b-form-select>
      </b-col>
      <b-col cols="7">
        <b-form-input v-model="val"></b-form-input>
      </b-col>
      <b-col cols="2">
        <b-button id="searchBtn" @click="searchBtn">검색</b-button>
      </b-col>
    </b-row>

    <b-form-group
      v-if="houses && houses.length != 0"
      v-slot="{ ariaDescribedby }"
    >
      <b-form-radio-group
        id="search-radios"
        v-model="selected"
        :options="options"
        :aria-describedby="ariaDescribedby"
        button-variant="outline-primary"
        name="radio-btn-outline"
        buttons
      ></b-form-radio-group>
    </b-form-group>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";
const houseStore = "houseStore";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      price: 50000,
      floor: 25,
      key: "dong",
      keys: [
        { value: "dong", text: "동" },
        // { value: "name", text: "아파트 이름" },
      ],
      selected: "new",
      options: [
        { text: "최신순", value: "new" },
        { text: "가격순", value: "price" },
        { text: "면적순", value: "area" },
      ],
      val: "",
    };
  },
  methods: {
    ...mapActions(houseStore, [
      "getSearchList",
      "getHouseListByDong",
      "getDongCode",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_HOUSE",
      "CLEAR_HOUSES_LIST",
      "CLEAR_DONGS_CODE",
      "SET_HOUSE_LIST",
    ]),
    searchBtn() {
      if (this.key == null) {
        alert("검색 분류를 선택해주세요");
      } else if (this.val == "") {
        alert("검색어를 입력해주세요.");
      } else {
        this.CLEAR_DONGS_CODE();
        // 해당되는 동이 있나 확인, 있다면 dongCode 반환
        this.getDongCode(this.val);
      }
    },
  },
  filters: {
    priceFilter(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  computed: {
    ...mapState(houseStore, ["houses", "dongcodes"]),
    ...mapGetters(houseStore, ["getHouseOrder"]),
  },
  watch: {
    selected: function () {
      this.CLEAR_HOUSE();
      // this.getSearchList({
      //   key: this.key,
      //   value: this.val,
      //   order: this.selected,
      // });
      // 가져온 houses를 js 내부에서 정렬 - getters
      this.SET_HOUSE_LIST(this.getHouseOrder(this.selected));
    },
    dongcodes: function () {
      if (this.dongcodes.length > 0) {
        this.getHouseListByDong({
          gugunCode: this.dongcodes[0].dongCode.substring(0, 5),
          dongName: this.val,
        });
      }
    },
  },
};
</script>

<style scoped>
#searchBtn {
  width: 100%;
}
.input-title {
  font-size: 19px;
  font-weight: bolder;
}
.input-value {
  font-weight: bolder;
  color: rgb(40, 69, 196);
}
</style>
