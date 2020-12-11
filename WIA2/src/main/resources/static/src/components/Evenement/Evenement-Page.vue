<template>
<div>
  <div class="container">
    <h1 class="subtitle is-1">Liste des évènements</h1>
    <div class="columns is-multiline">
      <div class="column is-one-quarter" v-for="(evenement) in evenements" :key="evenement">
            <EvenementsCardView  :evenement="evenement"/>
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
  components: { EvenementsCardView, Footer},
  created() {
    this.$store.dispatch('setEvenement')
  },
  computed: {
    evenements() {
      return this.$store.getters.getEvenements
    }
  }
}
</script>

<style scoped>

</style>
