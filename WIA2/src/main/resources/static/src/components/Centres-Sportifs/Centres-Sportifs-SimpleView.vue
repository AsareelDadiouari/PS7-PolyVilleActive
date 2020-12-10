<template>
  <div>
    <div class="card">
      <a><div class="card-image">
        <figure class="image  is-3by2">
          <img :src="require('../../assets/Persona4.png')" alt="Placeholder image"> <!-- {{sport.picture}} -->
        </figure>
      </div></a>
      <div class="card-content">
        <div class="media">
          <div class="media-content">
            <p class="title is-4">Nom du centre</p> <!-- {{sport.nom}} -->
            <p class="subtitle is-6">Courte description du centre</p> <!-- {{sport.description}} -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Centres-Sportifs-SimpleView",
  props: {
    sport: Object
  }
}
</script>

<style scoped>
.card-image {
    width: 480px;
    height: 320px;
}

.card {
    width: 480px;
    border: groove;
}
</style>
