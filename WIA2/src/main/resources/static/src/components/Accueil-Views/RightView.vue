<template>
  <div>
    <div class="container">
      <h1 class="title is-6">Groupes populaires autour de vous</h1>
      <div class="scrollbar">
        <div class="column" v-for="index in 10" :key="index">
          <groupe-simple-view-vue/>
        </div>
      </div>
    </div>
    <br>
    <br>
    <div class="thebutton">
    <router-link to="/groupes"><button class="button is-primary">
      Rejoindre un groupe
      </button></router-link></div>
  </div>

</template>

<script>
import GroupeSimpleViewVue from '../Groupe/Groupe-SimpleView.vue';

export default {
  name: "RightView",
  components: {GroupeSimpleViewVue}
}
</script>

<style scoped>
.container {
  width: auto;
  height: 425px;
  border-radius: 25px;
  box-shadow: 3px 3px 2px #9f9e9e;
  padding-top: 10px;
  position: relative;
  top: 40px;
  right: 320px;
  margin-left: 365px;
}

.scrollbar {
  width: 250px;
  height: 330px;
  overflow: auto;
}

.thebutton {
  margin-left: 80px;
}
</style>
