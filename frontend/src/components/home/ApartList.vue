<template>
  <div>
    <!-- 로딩중일 때 보이도록 -->
    <b-skeleton-table
      :rows="1"
      :columns="4"
      :table-props="{ bordered: false, striped: false }"
      v-if="loading"
    ></b-skeleton-table>
    <!-- 로딩이 끝나면 화면 출력 -->
    <div v-else>
      <!-- 데이터가 있을 때 -->
      <table
        v-if="houses.length > 0"
        class="table mt-2"
        id="resultTable"
        style="text-align: center"
      >
        <colgroup>
          <col width="10%" />
          <col />
          <col />
          <col width="17%" />
          <col width="20%" />
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>아파트이름</th>
            <th class="text-center">주소</th>
            <th>건축년도</th>
            <th>거래금액</th>
          </tr>
        </thead>
        <tbody id="searchResult">
          <tr
            v-for="(house, index) in houses"
            :key="index"
            @click="currhouse(house)"
          >
            <td>{{ index + 1 }}</td>
            <td>{{ house.houseinfo.aptName }}</td>
            <td>{{ house.houseinfo.dongName }} {{ house.houseinfo.jibun }}</td>
            <td>{{ house.houseinfo.buildYear }}년</td>
            <td>{{ house.houseinfo.recentPrice.trim() }}만원</td>
          </tr>
        </tbody>
      </table>

      <!-- 데이터가 없을 때 -->
      <div v-else-if="dongs.length > 1 && houses.length == 0">
        <b-alert show variant="danger">데이터가 존재하지 않습니다.</b-alert>
      </div>

      <!-- 주소를 선택안했을 때 -->
      <div v-else>
        <b-alert show>주소를 선택해주세요!</b-alert>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
const houseStore = "houseStore";

export default {
  name: "ApartList",
  data() {
    return {
      loading: false,
    };
  },
  watch: {
    houses: function (newVal, oldVal) {
      // console.log(newVal + " " + oldVal);
      if (newVal.length != 0 && newVal.length != oldVal.length) {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
        }, 1200);
      }
    },
    guguns() {
      this.loading = false;
    },
    dongs() {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
      }, 1000);
    },
  },
  created() {
    this.CLEAR_HOUSE();
  },
  computed: {
    ...mapState(houseStore, ["houses", "guguns", "dongs"]),
  },
  methods: {
    ...mapMutations(houseStore, ["CLEAR_HOUSE", "SET_DETAIL_HOUSE"]),
    currhouse(house) {
      this.SET_DETAIL_HOUSE(house);
    },
  },
};
</script>

<style scoped>
table {
  display: block;
  height: 400px;
  overflow-y: scroll;
}
[v-cloak] > * {
  display: none;
}
</style>
