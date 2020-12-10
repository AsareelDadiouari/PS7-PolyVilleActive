<template>
<div>
  <div class="container">
    <h1 class="subtitle is-1">Liste des évènements</h1>
    <div class="columns is-multiline">
      <div class="column is-one-quarter" v-for="index in 10" :key="index">
        <EvenementsCardView/>
      </div>
    </div>
  </div>
<Footer/>
</div>
</template>

<script>
import EvenementsCardView from "@/components/Evenement/Evenements-CardView";
import Footer from "@/components/Footer";


export default {
  name: "Evenement-Page",
  components: {EvenementsCardView, Footer}
}
</script>

<style scoped>

</style>
