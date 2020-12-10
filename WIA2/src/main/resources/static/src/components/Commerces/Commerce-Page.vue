<template><div>
  <div class="container">
        <div class="centrage">
        <h1 class="title is-1">Commerces</h1></div>
        <div class="columns is-multiline">
              <div class="column is-4" v-for="currentStore in stores" :key="currentStore.id">
                <commerce-simple-view :commerce="currentStore"/>
              </div>
        </div>
  </div>
<Footer/>
</div>
</template>

<script>
import CommerceSimpleView from './Commerce-SimpleView.vue';
import Footer from "@/components/Footer";

export default {
  name: "Commerce-Page",
  components: {CommerceSimpleView, Footer},
  created() {
    this.$store.dispatch('setStores')
  },
  computed: {
    stores() {
      return this.$store.getters.getStores
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
