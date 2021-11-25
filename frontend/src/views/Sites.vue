<template>
  <b-container class="bv-example-row mt-5">
    <div class="mb-5">
      <h3 class="underline-green">
        <b-icon icon="link45deg"></b-icon> 관련 사이트
      </h3>
      <div>카드를 클릭하면 해당 웹사이트로 이동할 수 있습니다.</div>
    </div>

    <b-form-radio-group
      id="search-radios"
      v-model="selected"
      name="radio-btn-outline"
      buttons
    >
      <b-form-radio
        value="all"
        :button-variant="selected == 'all' ? 'secondary' : 'outline-secondary'"
        >모두</b-form-radio
      >
      <b-form-radio
        value="preSale"
        :button-variant="
          selected == 'preSale' ? 'secondary' : 'outline-secondary'
        "
        >분양</b-form-radio
      >
      <b-form-radio
        value="welfare"
        :button-variant="
          selected == 'welfare' ? 'secondary' : 'outline-secondary'
        "
        >복지</b-form-radio
      >
      <b-form-radio
        value="subscription"
        :button-variant="
          selected == 'subscription' ? 'secondary' : 'outline-secondary'
        "
        >청약</b-form-radio
      >
      <b-form-radio
        value="realEstate"
        :button-variant="
          selected == 'realEstate' ? 'secondary' : 'outline-secondary'
        "
        >부동산</b-form-radio
      >
      <b-form-radio
        value="interior"
        :button-variant="
          selected == 'interior' ? 'secondary' : 'outline-secondary'
        "
        >인테리어</b-form-radio
      >
    </b-form-radio-group>

    <b-row class="mb-4" v-if="this.selected == 'all'">
      <b-col lg="4" md="6" sm="12" v-for="(site, index) in sites" :key="index">
        <site-preview :site="site"></site-preview>
      </b-col>
    </b-row>

    <b-row class="mb-4" v-else>
      <b-col
        lg="4"
        md="6"
        sm="12"
        v-for="site in filterSites"
        :key="site.title"
      >
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
  computed: {
    filterSites: function () {
      return this.sites.filter((site) => {
        return site.tag.includes(this.selected);
      });
    },
  },
  data() {
    return {
      selected: "all",
      sites: [
        {
          title: "서울주거포털",
          dsc: "서울시의 주거정책 및 주거 복지 서비스, 분양/임대 정보를 종합적으로 제공합니다.",
          link: "https://housing.seoul.go.kr/",
          tag: ["preSale", "welfare"],
        },
        {
          title: "LH한국토지주택공사",
          dsc: "전국의 토지 주택 분양정보 및 임대정보, 사전 청약 정보를 확인할 수 있습니다.",
          link: "https://www.lh.or.kr/index.do",
          tag: ["preSale", "subscription"],
        },
        {
          title: "HF한국주택금융공사",
          dsc: "보금자리론과 적격대출 공급, 주택보증, 유동화증권 발행 등의 업무를 수행할 수 있습니다.",
          link: "https://www.hf.go.kr/hf/index.do",
          tag: ["preSale"],
        },
        {
          title: "한국부동산원 청약홈",
          dsc: "청약신청, 당첨자발표, 청약정보 등 청약과 관련된 정보를 제공합니다.",
          link: "https://www.applyhome.co.kr/co/coa/selectMainView.do",
          tag: ["subscription"],
        },
        {
          title: "SH 인터넷청약시스템",
          dsc: "다양한 청약 시스템과 공고에 대해 확인할 수 있습니다.",
          link: "https://www.i-sh.co.kr/app/index.do",
          tag: ["subscription"],
        },
        {
          title: "마이홈포털",
          dsc: "다양한 주거복지와 공공 주택에 대한 정보를 제공합니다.",
          link: "https://www.myhome.go.kr/hws/portal/main/getMgtMainPage.do",
          tag: ["welfare"],
        },
        {
          title: "국토교통부 실거래가 공개서비스",
          dsc: "해당 서비스는 부동산 거래신고제를 통해 수집된 실거래 자료를 제공하고 있습니다.",
          link: "http://rt.molit.go.kr/",
          tag: ["realEstate"],
        },
        {
          title: "네이버 부동산",
          dsc: "매매 및 임대 물건정보, 아파트 부동산 시세, 분양정보, 부동산 뉴스를 제공합니다.",
          link: "https://land.naver.com/",
          tag: ["realEstate"],
        },
        {
          title: "다음 부동산",
          dsc: "아파트, 분양, 원룸, 빌라, 오피스텔 등 원하는 정보를 찾을 수 있습니다.",
          link: "https://realty.daum.net/",
          tag: ["realEstate"],
        },
        {
          title: "KB 부동산",
          dsc: "KB국민은행이 만든 부동산 플랫폼으로 매물, 시세, 실거래가를 확인할 수 있습니다.",
          link: "https://kbland.kr/map",
          tag: ["realEstate"],
        },
        {
          title: "MK 부동산",
          dsc: "부동산 정보, 관련 컬럼, 뉴스, 분양, 시세, 매물, 경매 정보 등을 제공합니다.",
          link: "https://mk.co.kr/estate/",
          tag: ["realEstate"],
        },
        {
          title: "직방",
          dsc: "아파트, 분양, 원룸, 빌라, 오피스텔까지 모든 정보를 확인해보세요",
          link: "https://www.zigbang.com/",
          tag: ["realEstate"],
        },
        {
          title: "다방",
          dsc: "원룸, 투룸, 오피스텔, 아파트까지 원하는 방을 확인해보세요",
          link: "https://www.dabangapp.com/",
          tag: ["realEstate"],
        },
        {
          title: "피터팬의 좋은방 구하기",
          dsc: "원룸,투룸부터 오피스텔, 신축빌라, 아파트 등 부동산 직거래, 중개거래를 제공합니다.",
          link: "https://www.peterpanz.com/",
          tag: ["realEstate"],
        },
        {
          title: "집꾸미기",
          dsc: "인테리어와 관련된 정보, 커뮤니티, 스토어가 제공됩니다.",
          link: "https://m.ggumim.co.kr/",
          tag: ["interior"],
        },
        {
          title: "한샘닷컴",
          dsc: "다양한 인테리어 정보와 서비스를 제공받을 수 있습니다.",
          link: "https://www.hanssem.com/main/main.do",
          tag: ["interior"],
        },
        {
          title: "집닥",
          dsc: "공사유형과 주거유형으로 분류된 종합 인테리어 사례 목록를 확인할 수 있습니다. ",
          link: "https://zipdoc.co.kr/product/construction/list",
          tag: ["interior"],
        },
        {
          title: "인젠",
          dsc: "사업, 주거유형에 따른 인테리어 견적 비교 서비스를 제공합니다. ",
          link: "https://interiorgentleman.com/",
          tag: ["interior"],
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
.container {
  height: 100%;
}

#search-radios {
  z-index: 0;
}
</style>
