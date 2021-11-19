<template>
  <div class="home">
    <Jumbotron></Jumbotron>
    <div style="padding: 20px">
      <select-location></select-location>
      <b-row style="justify-content: center">
        <b-col><kakao-map></kakao-map></b-col>
        <b-col><apart-list></apart-list></b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import Jumbotron from "@/components/home/jumbotron.vue";
import SelectLocation from "@/components/home/SelectLocation.vue";
import KakaoMap from "@/components/home/KakaoMap.vue";
import ApartList from "@/components/home/ApartList.vue";
export default {
  name: "Home",
  components: { Jumbotron, SelectLocation, KakaoMap, ApartList },
};
</script>
