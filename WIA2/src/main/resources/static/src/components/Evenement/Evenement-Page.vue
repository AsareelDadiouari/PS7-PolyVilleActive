<template>
<div>
  <div class="container">
    <h1 class="subtitle is-1">Liste des évènements</h1>
    <div class="search-bar" slot="end">
      <SearchBar @onInputChange="getSearchValue($event)"/>
    </div>
    <div class="columns is-multiline">
      <div class="column is-one-quarter" v-for="(evenement, index) in evenements" :key="index">
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
import SearchBar from "@/components/SearchBar";

export default {
  name: "Evenement-Page",
  components: {SearchBar, EvenementsCardView, Footer},
  data() {
    return {
      search: ''
    }
  },
  created() {
    this.$store.dispatch('setEvenement')

  },
  computed: {
    evenements() {
      return this.$store.getters.getEvenements.filter( (data) => {
        return data.name.toLowerCase().includes(this.search.toLowerCase())
      })
    }
  },
  methods: {
    getSearchValue(val){
      this.search = val
    }
  }
}
</script>

<style scoped>

</style>
