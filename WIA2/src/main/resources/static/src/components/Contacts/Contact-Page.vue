<template>
<div>
  <div class="container">
    <h1 class="subtitle is-1">Mes contacts</h1>
    <div class="columns is-multiline">
      <div class="column is-one-quarter" v-for="index in 10" :key="index">
            <contact-card-view/>
      </div>
    </div>
  </div>
  <Footer/>
</div>
</template>

<script>
import Footer from "@/components/Footer";
import ContactCardView from './Contact-CardView.vue';


export default {
  name: "Contact-Page",
  components: { Footer, ContactCardView},
}
</script>

<style scoped>

</style>
