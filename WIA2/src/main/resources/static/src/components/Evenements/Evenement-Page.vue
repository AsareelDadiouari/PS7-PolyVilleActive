<template>
  <div class="container">
    <h1 class="subtitle is-1">Liste des évènements</h1>
    <div class="columns is-multiline">
      <div class="column is-one-quarter" v-for="index in 10" :key="index">
        <EvenementsCardView/>
      </div>
    </div>
  </div>

</template>

<script>
import EvenementsCardView from "@/components/Evenements/Evenements-CardView";

export default {
  name: "Evenement-Page",
  components: {EvenementsCardView}
}
</script>

<style scoped>

</style>
