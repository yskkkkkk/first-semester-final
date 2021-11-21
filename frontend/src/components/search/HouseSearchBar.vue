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
import { mapState, mapActions, mapMutations } from "vuex";
const houseStore = "houseStore";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      price: 50000,
      floor: 25,
      key: null,
      keys: [
        { value: null, text: "선택" },
        { value: "dong", text: "동" },
        { value: "name", text: "아파트 이름" },
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
    ...mapActions(houseStore, ["getSearchList"]),
    ...mapMutations(houseStore, ["CLEAR_HOUSE", "CLEAR_HOUSES_LIST"]),
    searchBtn() {
      if (this.key == null) {
        alert("검색 분류를 선택해주세요");
      } else if (this.val == "") {
        alert("검색어를 입력해주세요.");
      } else {
        // 검색
        this.getSearchList({
          key: this.key,
          value: this.val,
          order: this.selected,
        });
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
    ...mapState(houseStore, ["houses"]),
  },
  watch: {
    selected: function () {
      this.CLEAR_HOUSE();
      this.CLEAR_HOUSES_LIST();
      this.getSearchList({
        key: this.key,
        value: this.val,
        order: this.selected,
      });
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
