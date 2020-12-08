<template>
  <div>
    <nav class="level profiles">
      <div class="level-item has-text-centered">
        <div class="user">
          <img :src="require('../assets/Persona4.png')" alt="Image Profil"/>
          <p>Elisa</p>
        </div>
        <div class="user">
          <img :src="require('../assets/Persona4.png')" alt="Image Profil"/>
          <p>Andréa</p>
        </div>
        <div class="user">
          <img :src="require('../assets/Persona4.png')" alt="Image Profil"/>
          <p>Vincent</p>
        </div>
        <div class="user">
          <img :src="require('../assets/Persona4.png')" alt="Image Profil"/>
          <p>Alexis</p>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: "Profils"
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
