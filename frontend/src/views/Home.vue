<template>
  <div class="home">
    <Jumbotron></Jumbotron>
    <div style="padding: 20px">
      <select-location></select-location>
      <b-row style="justify-content: center">
        <b-col><kakao-map></kakao-map></b-col>
        <b-col><apart-list></apart-list></b-col>
      </b-row>
      <br />
      <b-row style="justify-content: center">
        <b-col><news-carousel></news-carousel></b-col>
        <b-col><word-cloud></word-cloud></b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import Jumbotron from "@/components/home/jumbotron.vue";
import SelectLocation from "@/components/home/SelectLocation.vue";
import KakaoMap from "@/components/home/KakaoMap.vue";
import ApartList from "@/components/home/ApartList.vue";
import NewsCarousel from "@/components/home/NewsCarousel.vue";
import WordCloud from "@/components/home/WordCloud.vue";
export default {
  name: "Home",
  components: {
    Jumbotron,
    SelectLocation,
    KakaoMap,
    ApartList,
    NewsCarousel,
    WordCloud,
  },
};
</script>
