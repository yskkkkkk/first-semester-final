<template>
  <b-container class="bv-example-row mt-5">
    <h3 class="underline-hotpink"><b-icon icon="journals"></b-icon> 게시판</h3>
    <div class="mb-5">자유롭게 정보를 공유할 수 있습니다.</div>
    <router-view></router-view>
  </b-container>
</template>
<script>
export default {
  name: "Board",
};
</script>
<style scoped>
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 27, 139, 0.3) 30%
  );
}

.container {
  height: 100%;
}
</style>
