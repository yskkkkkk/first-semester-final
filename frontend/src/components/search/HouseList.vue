<template>
  <b-container
    v-if="houses && houses.length != 0 && housesfilter.length != 0"
    class="bv-example-row mt-3"
  >
    <house-list-row
      v-for="(house, index) in housesfilter"
      :key="index"
      :house="house"
    />
  </b-container>
  <b-container
    v-else-if="houses && houses.length != 0 && housesfilter.length == 0"
    class="bv-example-row my-3"
  >
    <house-list-row
      v-for="(house, index) in houses"
      :key="index"
      :house="house"
    />
  </b-container>
  <b-container v-else class="bv-example-row my-3">
    <b-row>
      <b-col><b-alert show>데이터가 존재하지 않습니다.</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import HouseListRow from "@/components/search/HouseListRow.vue";
import { mapState, mapMutations } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseList",
  components: {
    HouseListRow,
  },
  data() {
    return {};
  },
  created() {
    this.CLEAR_HOUSE();
    this.CLEAR_HOUSES_LIST();
    this.CLEAR_HOUSES_FILTER();
  },
  computed: {
    ...mapState(houseStore, ["houses", "housesfilter"]),
  },
  methods: {
    ...mapMutations(houseStore, [
      "CLEAR_HOUSE",
      "CLEAR_HOUSES_LIST",
      "CLEAR_HOUSES_FILTER",
    ]),
  },
};
</script>

<style scoped>
.container {
  display: block;
  max-height: 700px;
  overflow-y: scroll;
}
</style>
