<template>
  <div class="container">
    <div class="img">
      <img src="https://img.icons8.com/pastel-glyph/64/000000/cancel--v1.png"/></div>
    <div class="centrage">
      <h1 class="title is-1">Nom du centre sportif</h1></div>
    <br>
    <div class="img2">
    <figure class="image  is-16by9">
          <img :src="require('../../assets/Persona4.png')" alt="Placeholder image"> <!-- {{evenement.picture}} -->
    </figure>
    <br>
    <p class="subtitle is-4">Description du centre + informations</p>
    </div>
    </div>
</template>

<script>

export default {
  name: "Centres-Sportifs-FullSingle"
}
</script>

<style scoped>
.container {
  height: 1200px;
  width: 90%;
  margin: auto;
  padding-top: 7px;
  border: solid #9f9e9e;
  position: relative;
  top: 40px;
}

.centrage {
  text-align: center;
  vertical-align: middle;
}

.img {
  text-align: right;
  padding-right: 5px;
}

.img2 {
    height: 320px;
}
</style>
