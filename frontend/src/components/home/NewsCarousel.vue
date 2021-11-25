<template>
  <div>
    <h5>관련뉴스</h5>
    <b-carousel
      id="news-carousel"
      v-model="slide"
      :interval="5000"
      controls
      indicators
      background="#ababab"
      img-width="1024"
      max-height="400"
      img-height="400"
      style="text-shadow: 1px 1px 2px #333"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <!-- Slide with blank fluid image to maintain slide aspect ratio -->
      <a
        :href="article.url"
        target="_blank"
        v-for="(article, index) in newslist"
        :key="index"
      >
        <b-carousel-slide :caption="article.title" v-if="article.img != ''">
          <!-- 이미지 있을 때 -->
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              :src="article.img"
              alt="image slot"
              style="opacity: 0.3"
            />
          </template>
          <div>
            <div>{{ article.media }}</div>
            <p>
              {{ article.content }}
            </p>
          </div>
        </b-carousel-slide>
        <b-carousel-slide
          v-else
          :caption="article.title"
          img-blank
          img-alt="Blank image"
        >
          <!-- 이미지 없을 때 -->
          <div>
            <div>{{ article.media }}</div>
            <p>
              {{ article.content }}
            </p>
          </div>
        </b-carousel-slide>
      </a>
    </b-carousel>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";
const newsStore = "newsStore";
export default {
  data() {
    return {
      slide: 0,
      sliding: null,
      articles: this.newslist,
    };
  },
  methods: {
    ...mapActions(newsStore, ["getNewsList"]),
    ...mapMutations(newsStore, ["CLEAR_NEWS_LIST"]),
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
  created() {
    if (this.newslist.length == 0) {
      this.CLEAR_NEWS_LIST();
      this.getNewsList();
    }
  },
  computed: {
    ...mapState(newsStore, ["newslist"]),
  },
};
</script>

<style scoped>
.image-overlay {
  width: 100%;
  height: 100;
  position: absolute;
  background-color: #f85752;
  opacity: 0.5;
}
.carousel-caption div {
  text-shadow: 2px 2px 5px black;
}
#news-carousel {
  min-width: 400px;
  max-width: 615px;
  z-index: 0 !important;
}
</style>
