<template>
  <div>
    <h5>인기 급상승 검색어</h5>
    <div id="word-cloud" v-if="wordlist.length > 0"></div>
    <div v-else>
      <b-alert variant="warning" show>아직 검색된 단어가 없습니다! </b-alert>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";
const wordStore = "wordStore";

export default {
  // data() {
  //   return {
  //     words: [
  //       { text: "종부세", size: 20 },
  //       { text: "부동산", size: 20 },
  //       { text: "신축", size: 30 },
  //       { text: "종부세", size: 30 },
  //       { text: "아파트", size: 30 },
  //       { text: "청약", size: 40 },
  //       { text: "집값", size: 40 },
  //       { text: "보험", size: 40 },
  //       { text: "재건축", size: 50 },
  //       { text: "인프라", size: 50 },
  //     ],
  //   };
  // },
  created() {
    this.CLEAR_WORD_LIST();
    this.getlistWord();
  },
  mounted() {
    setTimeout(() => {
      this.genLayout();
    }, 500);
  },
  computed: {
    ...mapState(wordStore, ["wordlist"]),
  },
  methods: {
    ...mapActions(wordStore, ["getlistWord"]),
    ...mapMutations(wordStore, ["CLEAR_WORD_LIST"]),
    genLayout() {
      const cloud = require("d3-cloud");
      cloud()
        .words(this.wordlist)
        .padding(1)
        .rotate(() => {
          return ~~(Math.random() * 2) * 90;
        })
        // .font("Impact")
        .fontSize(function (d) {
          return d.size;
        })
        .on("end", this.end)
        // .spiral("archimedean")
        .start()
        .stop();
    },
    end(words) {
      const d3 = require("d3");
      const width = 500;
      const height = 400;
      d3.select("#word-cloud")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
        .style("background", "white")
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")") // g를 중심에서 단어들을 그리기 때문에 g를 svg 중심으로 이동
        .selectAll("text")
        .data(words)
        .enter()
        .append("text")
        .style("fill", () => this.randomRGB())
        .style("font-size", (d) => {
          return d.size + "px";
        })
        // .style("font-family", "Impact")
        .attr("text-anchor", "middle")
        .attr("transform", (d) => {
          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text((d) => d.text);
    },
    randomRGB() {
      const colors = [
        "rgb(255, 99, 132)",
        "rgb(255, 99, 132)",
        "rgb(54, 162, 235)",
        "rgb(255, 206, 86)",
        "rgb(75, 192, 192)",
        "rgb(153, 102, 255)",
        "rgb(255, 159, 64)",
      ];
      var rand = Math.floor(Math.random() * colors.length);
      return colors[rand];
    },
  },
};
</script>

<style></style>
