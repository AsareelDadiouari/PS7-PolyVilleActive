<template>
  <div>
    <div class="evenement">
      <figure class="image is-64x64">
        <img :src="require('../../assets/Persona4.png')" alt="Image evenement"/>
      </figure>
      <div class="event-desc">
        <div class="title is-5"> Nom de l'événement</div>
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
  border-bottom: groove;
  padding: 1px 1px 1px 1px;
  width: 250px;
}

.event-desc {

}
</style>
