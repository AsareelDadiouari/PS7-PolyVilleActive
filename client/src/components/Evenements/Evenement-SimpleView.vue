<template>
  <div>
    <div class="evenement">
      <figure class="image is-64x64">
        <img :src="require('../../assets/Persona4.png')" alt="Image evenement"/>
      </figure>
      <div class="event-desc">
        <div class="title is-4"> Nom de l'évenement</div>
        <div class="subtitle is-6"> Description de l'évenement</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Evenement-SimpleView"
}
</script>

<style scoped>
.evenement {
  display: flex;
  border-radius: 10px;
  box-shadow: 3px 3px 2px #9f9e9e;
  width: 250px;
}

.event-desc {

}
</style>
