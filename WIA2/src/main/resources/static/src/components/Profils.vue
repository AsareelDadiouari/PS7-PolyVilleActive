<template>
  <div>
    <nav class="level profiles">
      <div class="level-item has-text-centered">
        <div class="user" v-for="currentUser in users" :key="currentUser.id">
          <router-link to="/u"><img :src="require('../assets/Persona4.png')" alt="Image Profil"/></router-link>
          <p>{{currentUser.firstname}}</p>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>

export default {
  name: "Profils",
  created() {
    this.$store.dispatch('setUsers')
  },
  computed: {
    users() {
      return this.$store.getters.getUsers
    }
  }
}
</script>

<style scoped>

.profiles {
  margin-top: 300px;
  margin-right: 100px;

}

.user {
  margin-left: 100px;
}

.user > p {
  font-weight: bold;
  padding: 0 10px;
}
</style>
