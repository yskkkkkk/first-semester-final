<template>
  <b-container class="bv-example-row mt-5">
    <div class="mb-5">
      <h3 class="underline-green">
        <b-icon icon="link45deg"></b-icon> 관련 사이트
      </h3>
      <div>카드를 클릭하면 해당 웹사이트로 이동할 수 있습니다.</div>
    </div>
    <b-row>
      <b-col lg="4" md="6" sm="12" v-for="(site, index) in sites" :key="index">
        <site-preview :site="site"></site-preview>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import SitePreview from "../components/sites/sitePreview.vue";
export default {
  name: "Sites",
  components: { SitePreview },
  data() {
    return {
      sites: [
        {
          title: "서울주거포털",
          dsc: "서울시의 주거정책 및 주거 복지 서비스, 분양/임대 정보를 종합적으로 제공합니다.",
          link: "https://housing.seoul.go.kr/",
        },
        {
          title: "LH한국토지주택공사",
          dsc: "전국의 토지 주택 분양정보 및 임대정보, 사전 청약 정보를 확인할 수 있습니다.",
          link: "https://www.lh.or.kr/index.do",
        },
        {
          title: "한국부동산원 청약홈",
          dsc: "청약신청, 당첨자발표, 청약정보 등 청약과 관련된 정보를 제공합니다.",
          link: "https://www.applyhome.co.kr/co/coa/selectMainView.do",
        },
        {
          title: "국토교통부 실거래가 공개서비스",
          dsc: "해당 서비스는 부동산 거래신고제를 통해 수집된 실거래 자료를 제공하고 있습니다.",
          link: "http://rt.molit.go.kr/",
        },
        {
          title: "네이버 부동산",
          dsc: "매매 및 임대 물건정보, 아파트 부동산 시세, 분양정보, 부동산 뉴스를 제공합니다.",
          link: "https://land.naver.com/",
        },
        {
          title: "MK 부동산",
          dsc: "부동산 정보, 관련 컬럼, 뉴스, 분양, 시세, 매물, 경매 정보 등을 제공합니다.",
          link: "https://mk.co.kr/estate/",
        },
        {
          title: "직방",
          dsc: "아파트, 분양, 원룸, 빌라, 오피스텔까지 모든 정보를 확인해보세요",
          link: "https://www.zigbang.com/",
        },
      ],
    };
  },

  methods: {},
};
</script>

<style scoped>
.underline-green {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(56, 233, 40, 0.3) 30%
  );
}
</style>
