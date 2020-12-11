<template>
<div>
  <div class="container">
    <h1 class="subtitle is-1">Liste des restaurants</h1>
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


export default {
  name: "Restaurant-Page",
  components:  {
    RestaurantCardView, Footer
  },
  created() {
    this.$store.dispatch('setRestaurants')

  },
  computed: {
    restaurants() {
      return this.$store.getters.getRestaurants
    }
  }
}
</script>

<style scoped>

</style>
