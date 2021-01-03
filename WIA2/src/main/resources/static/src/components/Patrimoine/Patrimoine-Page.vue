<template>
  <div>
    <div class="container">
        <div class="centrage">
          <h1 class="title is-1">Patrimoine </h1></div>
        <div class="columns is-multiline">
          <div class="column is-one-quarter" v-for="currentPatrimoine in patrimoines" :key="currentPatrimoine.id">
            <patrimoine-simple-view :patrimoine="currentPatrimoine"/>
          </div>
  </div>
        </div>
  <Footer/>
  </div>
</template>

<script>
import PatrimoineSimpleView from './Patrimoine-SimpleView.vue';
import Footer from "@/components/Footer";

export default {
  name: "Patrimoine-Page",
  components: {PatrimoineSimpleView, Footer},
  created() {
    this.$store.dispatch('setPatrimoines')

  },
  computed: {
    patrimoines() {
      return this.$store.getters.getPatrimoines
    }
  }
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
