<template>
  <div>
    <nav class="level profiles">
      <div class="level-item has-text-centered">
        <Profil v-for="currentUser in users" :key="currentUser.id" :user="currentUser"/>
      </div>
    </nav>
  </div>
</template>

<script>

import Profil from './Profil.vue'

export default {
  name: "Profils",
  components:  {
    Profil
  },
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
