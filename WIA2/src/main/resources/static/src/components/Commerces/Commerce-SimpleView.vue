<template>
  <div>
    <div class="card">
      <!-- <a><div class="card-image">
        <figure class="image  is-3by2">
          <img :src="require('../../assets/Persona4.png')" alt="Placeholder image">
        </figure>
      </div></a> -->
      <div class="card-content">
        <div class="media">
          <div class="media-left">
            <i class="fas fa-store"></i>
          </div>
          <div class="media-content">
            <p class="title is-4">{{store.name_fr}}</p>
            <p class="subtitle is-6">{{store.address}}</p>
            <p class="subtitle is-6" v-if="store.opening != ''">Ouvertures : {{store.opening}}</p>
          </div>
        </div>
        <div class="content">
          <p>Services:</p>
          <ul>
            <li v-for="categorie in store.categories" :key="categorie">{{categorie}}</li>
          </ul>
          <br>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Commerce-SimpleView",
  props: {
      store: Object,
  }
}
</script>

<style scoped>

</style>
