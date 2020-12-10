<template><div>
  <div class="container">
        <div class="centrage">
        <h1 class="title is-1">Centres Sportifs</h1></div>
        <div class="columns is-multiline">
              <div class="column is-4" v-for="index in 10" :key="index">
                <centres-sportifs-simple-view/>
                </div>
                </div>
  </div>
<Footer/>
</div>
</template>

<script>
import CentresSportifsSimpleView from './Centres-Sportifs-SimpleView.vue';
import Footer from "@/components/Footer";

export default {
  name: "Centres-Sportifs--Page",
  components: {CentresSportifsSimpleView, Footer}
}
</script>

<style scoped>
.container {
    height: auto;
    padding-top: 7px;
    border-left: 1px solid;
    border-right: 1px solid;
}

.centrage {
  text-align: center;
  padding-bottom: 50px;
  padding-top: 20px;
}
</style>
