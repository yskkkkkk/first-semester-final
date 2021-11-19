<template>
  <div class="home">
    <Jumbotron></Jumbotron>
    <select-location></select-location>
  </div>
</template>

<script>
import Jumbotron from "@/components/home/jumbotron.vue";
import SelectLocation from "@/components/home/SelectLocation.vue";
export default {
  name: "Home",
  components: { Jumbotron, SelectLocation },
};
</script>
