<template>
  <div>
    <div class="card">

      <div class="card-content">
        <div class="media">
          <div class="media-left">
            <i class="fas fa-utensils"></i>
          </div>
          <div class="media-content">
            <p class="title is-4">{{restaurant.name}} </p>
            <p class="subtitle is-6">{{restaurant.adress}}</p>
          </div>
        </div>

        <div class="content">
          <p>Services:</p>
          <ul>
            <li v-for="service in restaurant.services" :key="service">{{service}}</li>
          </ul>
          <p>Autres:</p>
          <ul>
            <li v-for="amenities in restaurant.amenities" :key="amenities">{{amenities}}</li>
          </ul>
          <br>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Restaurants-CardView",
  props: {
    restaurant: Object
  }
}
</script>

<style scoped>

</style>
