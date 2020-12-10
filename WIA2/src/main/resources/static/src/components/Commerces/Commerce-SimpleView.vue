<template>
  <div>
    <div class="card">
      <a><div class="card-image">
        <figure class="image  is-3by2">
          <img :src="require('../../assets/Persona4.png')" alt="Placeholder image"> <!-- {{commerce.picture}} -->
        </figure>
      </div></a>
      <div class="card-content">
        <div class="media">
          <div class="media-content">
            <p class="title is-4">Nom du commerce</p> <!-- {{commerce.nom}} -->
            <p class="subtitle is-5">Type de commerce - Ouvert / Pas ouvert</p> <!-- {{commerce.type}} {{commerce.isavailable}}-->
            <p class="subtitle is-6">Adresse</p> <!-- {{commerce.addresse}} -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Commerce-SimpleView",
  props: {
      commerce: Object,
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
