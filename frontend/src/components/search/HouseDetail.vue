<template>
  <b-container v-if="house" class="bv-example-row">
    <b-row>
      <b-col
        ><h3>{{ house.houseinfo.aptName }}</h3></b-col
      >
    </b-row>
    <b-row class="mb-2 mt-1">
      <b-col
        ><b-img
          :src="require('@/assets/apt/apt' + house.houseinfo.img + '.jpg')"
          fluid-grow
          rounded
          style="max-height: 400px"
        ></b-img
      ></b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="secondary"
          >일련번호 : {{ house.aptCode }}</b-alert
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="primary"
          >아파트 이름 : {{ house.houseinfo.aptName }}
        </b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="info"
          >법정동 : {{ house.houseinfo.dongName }}
        </b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="warning">층수 : {{ house.floor }}층</b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="danger"
          >거래금액 :
          {{
            (parseInt(house.dealAmount.replace(",", "")) * 10000) | price
          }}원</b-alert
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="secondary">면적 : {{ house.area }}m²</b-alert>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseDetail",
  computed: {
    ...mapState(houseStore, ["house"]),
    // house() {
    //   return this.$store.state.house;
    // },
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style></style>
