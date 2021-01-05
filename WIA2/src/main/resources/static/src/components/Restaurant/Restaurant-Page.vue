<template>
<div>
  <div class="container">
    <h1 class="subtitle is-1">Liste des restaurants</h1>
    <div class="search-bar" slot="end">
      <SearchBar @onInputChange="getSearchValue($event)"/>
    </div>
    <div class="columns is-multiline">
      <div class="column is-one-quarter" v-for="currentRestaurant in restaurants" :key="currentRestaurant.id">
        <RestaurantCardView  :restaurant="currentRestaurant"/>
      </div>
    </div>
  </div>
<Footer/>
</div>
</template>

<script>
import RestaurantCardView from "@/components/Restaurant/Restaurants-CardView";
import Footer from "@/components/Footer";
import SearchBar from "@/components/SearchBar";


export default {
  name: "Restaurant-Page",
  data() {
    return {
      search: ''
    }
  },
  components:  {
    SearchBar,
    RestaurantCardView, Footer
  },
  created() {
    this.$store.dispatch('setRestaurants')
  },
  computed: {
    restaurants() {
      return this.$store.getters.getRestaurants.filter( (data) => {
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
