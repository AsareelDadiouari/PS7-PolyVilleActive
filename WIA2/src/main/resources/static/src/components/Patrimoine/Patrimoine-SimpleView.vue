<template>
  <div>
    <div class="card">
      <a><div class="card-image">
        <figure class="image  is-3by2">
          <img :src="patrimoine.image" alt="Placeholder image"> <!-- {{patrimoine.picture}} -->
        </figure>
      </div></a>
      <div class="card-content">
        <div class="media">
          <div class="media-content">
            <p class="title is-4">{{ patrimoine.name }}</p> <!-- {{lieu.nom}} -->
               </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Patrimoine-SimpleView",
  props: {
    patrimoine: Object
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
