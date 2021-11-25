<template>
  <a :href="site.link" target="_blank">
    <b-card
      text-variant="dark"
      :header="site.title"
      border-variant="gray"
      class="my-3"
    >
      <div class="crop-wrapper">
        <b-img :src="image"></b-img>
      </div>
      <br />
      <div>{{ site.dsc }}</div>
    </b-card>
  </a>
</template>

<script>
import { openGraph } from "@/api/opengraph.js";
import noImg from "@/assets/noimg.jpg";
export default {
  name: "sitePreview",
  data() {
    return {
      image: "",
    };
  },
  props: {
    site: Object,
  },
  created() {
    openGraph(this.site.link).then((res) => {
      if (res.data.image) this.image = res.data.image;
      else this.image = noImg;
    });
  },
};
</script>

<style scoped>
.crop-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

.crop-wrapper img {
  width: 150px;
  height: 150px;
  object-fit: contain;
  border-radius: 10px;
  padding: 10px;
  border: 1px solid rgb(219, 219, 219);
}
</style>
