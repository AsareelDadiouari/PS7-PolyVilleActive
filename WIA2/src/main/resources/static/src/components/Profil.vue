<template>
  <div class="user">
    <router-link :to="'/u/' + user.id"><img :src="require('../assets/Persona4.png')" alt="Image Profil"/></router-link>
    <p>{{user.firstname}}</p>
  </div>
</template>

<script>

export default {
  name: "Profils",
  props: {
      user: Object
  }
}
</script>

<style scoped>

.user {
  margin-left: 100px;
}

.user > p {
  font-weight: bold;
  padding: 0 10px;
}
</style>
