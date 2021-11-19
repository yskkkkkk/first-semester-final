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
        <b-button @click="searchBtn">검색</b-button>
      </b-col>
    </b-row>
    <b-row class="px-3">
      <div>
        <span class="input-title">매매 가격</span><br />
        0원 ~ <span class="input-value">{{ price | priceFilter }}</span> 만원
      </div>
      <b-form-input
        id="priceRange"
        type="range"
        min="500"
        max="50000"
        step="500"
        v-model="price"
      ></b-form-input
      ><br /><br />
    </b-row>
    <b-row class="px-3">
      <div>
        <span class="input-title">층 수</span><br />
        1층 ~ <span class="input-value">{{ floor }}</span> 층
      </div>
      <b-form-input
        id="priceRange"
        type="range"
        min="1"
        max="50"
        step="1"
        v-model="floor"
      ></b-form-input
      ><br />
    </b-row>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";
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
      val: "",
    };
  },
  created() {},
  methods: {
    ...mapMutations(["setKey", "setValue"]),
    searchBtn() {
      if (this.key == null) {
        alert("검색 분류를 선택해주세요");
      } else if (this.val == "") {
        alert("검색어를 입력해주세요.");
      } else {
        console.log(this.key + " " + this.val);
        this.setKey(this.key);
        this.setValue(this.val);
        this.$emit("newPosts");
      }
    },
  },
  computed: { ...mapGetters(["searchPost"]) },
  filters: {
    priceFilter(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style scoped>
button {
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
