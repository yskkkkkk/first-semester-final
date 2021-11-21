<template>
  <div>
    <h5>관련뉴스</h5>
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      background="#ababab"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <!-- Slide with blank fluid image to maintain slide aspect ratio -->
      <a
        :href="article.link"
        target="_blank"
        v-for="(article, index) in articles"
        :key="index"
      >
        <b-carousel-slide
          :caption="article.title"
          img-blank
          img-alt="Blank image"
        >
          <div>
            <div>{{ article.writer }}</div>
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
export default {
  data() {
    return {
      slide: 0,
      sliding: null,
      articles: [
        {
          title: "title1 title1 title1 title1 title1 ",
          writer: "writer1",
          content:
            "content1 content1 content1 content1 content1 content1 content1 content1 content1 content1 content1 ",
          link: "https://www.naver.com/",
        },
        {
          title: "title2 title2 title2 title2 title2 ",
          writer: "writer2",
          content:
            "content2 content2 content2 content2 content2 content2 content2 content2 content2 content2 content2 ",
          link: "https://www.daum.net/",
        },
        {
          title: "title3 title3 title3 title3 title3 ",
          writer: "writer3",
          content:
            "content3 content3 content3 content3 content3 content3 content3 content3 content3 content3 content3 ",
          link: "https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp",
        },
      ],
    };
  },
  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
};
</script>
