<template>
  <div class="container">
      <h1 class="title is-6">Groupes populaires autour de vous</h1>
      <div class="scrollbar">
      <div class="column" v-for="index in 10" :key="index">
        <groupe-simple-view-vue/>
    </div>
    </div>
  </div>

</template>

<script>
import GroupeSimpleViewVue from './Groupe-SimpleView.vue';
export default {
  name: "Groupe-PageReduit",
  components: {GroupeSimpleViewVue}
}
</script>

<style scoped>
.container {
    width: 250px;
    height: 395px;
    border-radius: 25px;
    box-shadow: 3px 3px 2px #9f9e9e;
    padding-top: 7px;
    position: relative;
    top: 40px;
    right: 320px;
  margin-left: 365px;
}

.scrollbar {
    width: 250px;
    height: 310px;
    overflow: auto;
}
</style>
