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
      v-if="houses && houses.length > 0"
      v-slot="{ ariaDescribedby }"
    >
      <b-form-radio-group
        id="search-radios"
        v-model="selected"
        :aria-describedby="ariaDescribedby"
        name="radio-btn-outline"
        buttons
      >
        <b-form-radio
          v-show="newOrder"
          value="newDesc"
          :button-variant="
            selected == 'newAsc' ? 'secondary' : 'outline-secondary'
          "
          >최신순↓</b-form-radio
        >
        <b-form-radio
          v-show="!newOrder"
          value="newAsc"
          :button-variant="
            selected == 'newDesc' ? 'secondary' : 'outline-secondary'
          "
          >최신순↑</b-form-radio
        >
        <b-form-radio
          v-show="priceOrder"
          value="priceDesc"
          :button-variant="
            selected == 'priceAsc' ? 'secondary' : 'outline-secondary'
          "
          >가격순↓</b-form-radio
        >
        <b-form-radio
          v-show="!priceOrder"
          value="priceAsc"
          :button-variant="
            selected == 'priceDesc' ? 'secondary' : 'outline-secondary'
          "
          >가격순↑</b-form-radio
        >
        <b-form-radio
          v-show="areaOrder"
          value="areaDesc"
          :button-variant="
            selected == 'areaAsc' ? 'secondary' : 'outline-secondary'
          "
          >면적순↓</b-form-radio
        >
        <b-form-radio
          v-show="!areaOrder"
          value="areaAsc"
          :button-variant="
            selected == 'areaDesc' ? 'secondary' : 'outline-secondary'
          "
          >면적순↑</b-form-radio
        >
      </b-form-radio-group>

      <b-row class="pt-3">
        <b-col cols="6">
          <b-form-input
            v-model="aptName"
            placeholder="아파트 이름으로 검색해보세요!"
          ></b-form-input>
        </b-col>
      </b-row>
    </b-form-group>
  </div>
</template>

<script>
import _ from "lodash";
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";
const houseStore = "houseStore";
const wordStore = "wordStore";

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
      selected: "newAsc",
      newOrder: false, // true이면 내림차순, false이면 오름차순
      priceOrder: true,
      areaOrder: true,
      val: "",
      aptName: "",
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
      "CLEAR_HOUSES_FILTER",
      "SET_HOUSE_FILTER",
    ]),
    ...mapActions(wordStore, ["addWord"]),
    searchBtn() {
      if (this.key == null) {
        this.makeToast("앗!", "검색 분류를 선택해주세요", "warning");
        // alert("검색 분류를 선택해주세요");
      } else if (this.val == "") {
        this.makeToast("앗!", "검색어를 입력해주세요.", "warning");
        // alert("검색어를 입력해주세요.");
      } else {
        this.CLEAR_DONGS_CODE();
        // 해당되는 동이 있나 확인, 있다면 dongCode 반환
        this.getDongCode(this.val);
        // 검색할 때마다 wordCloud에 단어 추가
        this.addWord(this.val);
      }
    },
    apartSearch() {
      this.addWord(this.aptName);
      // 아파트 검색 창에서 엔터 누르면 워드 클라우드에 추가
    },
    makeToast(title, msg, variant) {
      this.$bvToast.toast(msg, {
        title: title,
        variant: variant,
        solid: true,
      });
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
      switch (this.selected) {
        case "newDesc":
        case "newAsc":
          this.newOrder = !this.newOrder;
          break;
        case "priceDesc":
        case "priceAsc":
          this.priceOrder = !this.priceOrder;
          break;
        case "areaDesc":
        case "areaAsc":
          this.areaOrder = !this.areaOrder;
          break;
      }
      this.CLEAR_HOUSE();
      // 가져온 houses를 js 내부에서 정렬 - getters
      this.SET_HOUSE_LIST(this.getHouseOrder(this.selected));
      if (this.aptName == "") this.CLEAR_HOUSES_FILTER();
      else this.SET_HOUSE_FILTER(this.aptName);
    },
    dongcodes: function () {
      if (this.dongcodes.length > 0) {
        this.getHouseListByDong({
          gugunCode: this.dongcodes[0].dongCode.substring(0, 5),
          dongName: this.val,
        });
      }
    },
    aptName: _.debounce(function (newVal) {
      if (newVal == "") this.CLEAR_HOUSES_FILTER();
      else {
        this.addWord(this.aptName);
        this.SET_HOUSE_FILTER(this.aptName);
      }
    }, 1000),
    //   lodash_debounce 사용: 1초동안 입력 안하면 addWord 실행
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
