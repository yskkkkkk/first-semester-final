<template>
  <div id="app">
    <navi-bar />
    <router-view />
    <!-- <b-navbar toggleable type="dark" variant="dark">
      <b-navbar-brand>Happy House</b-navbar-brand>

      <b-button @click="moveToTop" class="mr-3">
        <b-icon icon="chevron-bar-up"></b-icon>
      </b-button>
    </b-navbar> -->
  </div>
</template>

<script>
import NaviBar from "./components/layout/NaviBar.vue";

export default {
  name: "App",
  components: {
    NaviBar,
  },
  // methods: {
  //   moveToTop() {
  //     window.scrollTo(0, 0);
  //   },
  // },
};
</script>

<style>
@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 400;
  font-style: normal;
}

#App,
div {
  font-family: "Pretendard-Regular";
}

#App,
html {
  width: 100%;
}
</style>
