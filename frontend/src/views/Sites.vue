<template>
  <b-container class="bv-example-row mt-5">
    <h3 class="underline-green mb-5">
      <b-icon icon="link45deg"></b-icon> 관련 사이트
    </h3>
    <b-row>
      <b-col lg="4" md="6" sm="12" v-for="(site, index) in sites" :key="index">
        <b-card text-variant="dark" border-variant="dark" class="my-3">
          <h5>{{ site.title }}</h5>
          <b-img :src="require('@/assets/logo.png')"></b-img>
          <div>doddod</div>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "Sites",
  data() {
    return {
      sites: [
        {
          title: "사이트 1",
          link: "link",
        },
        {
          title: "사이트 2",
          link: "link",
        },
        {
          title: "사이트 3",
          link: "link",
        },
        {
          title: "사이트 4",
          link: "link",
        },
      ],
    };
  },
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
