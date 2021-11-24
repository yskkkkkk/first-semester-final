<template>
  <b-row
    class="m-2"
    @click="selectHouse"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="2" class="text-center align-self-center">
      <b-img
        thumbnail
        rounded
        fluid
        :src="require('@/assets/apt/apt' + house.houseinfo.img + '.jpg')"
      ></b-img>
    </b-col>
    <b-col cols="10" class="align-self-center">
      <b-row>
        <b-col cols="12"> {{ house.houseinfo.aptName }}</b-col>
        <b-col cols="4"
          >{{ house.dealYear }}.{{ house.dealMonth }}.{{ house.dealDay }}
        </b-col>
        <b-col cols="4">{{ house.dealAmount.trim() }}만원</b-col>
        <b-col cols="4">{{ house.area }} m²</b-col>
      </b-row>
    </b-col>
  </b-row>
</template>

<script>
import { mapActions } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseListRow",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    house: Object,
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
    selectHouse() {
      // console.log("listRow : ", this.house);
      // this.$store.dispatch("getHouse", this.house);
      this.detailHouse(this.house);
    },
    colorChange(flag) {
      this.isColor = flag;
    },
  },
};
</script>

<style scoped>
.apt {
  width: 50px;
}
.mouse-over-bgcolor {
  background-color: lightblue;
}
</style>
