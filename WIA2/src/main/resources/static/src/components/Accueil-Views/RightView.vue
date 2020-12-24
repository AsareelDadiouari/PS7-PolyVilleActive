<template>
  <div>
    <div class="container">
      <h1 class="title is-6">Groupes populaires autour de vous</h1>
      <div v-if="recommendedGroups.length > 0" class="scrollbar">
        <div class="column" v-for="currentGroup in recommendedGroups" :key="currentGroup.id">
          <groupe-simple-view-vue :group="currentGroup"/>
        </div>
      </div>
      <div v-else-if="groups.length > 0" class="scrollbar">
        <span>Vous n'avez plus de groupes recommandés !</span>
        <div class="column" v-for="currentGroup in groups" :key="currentGroup.id">
          <groupe-simple-view-vue :group="currentGroup"/>
        </div>
      </div>
      <div v-else>
        <span>Vous avez rejoint tous les groupes !</span>
      </div>
    </div>
    <br>
    <br>
    <div class="thebutton">
    <router-link :to="'/polyville/' + $route.params.id + '/recommended/groupes'"><button class="button is-primary">
      Rejoindre un groupe
      </button></router-link></div>
  </div>

</template>

<script>
import GroupeSimpleViewVue from '../Groupe/Groupe-SimpleView.vue';

export default {
  name: "RightView",
  components: {GroupeSimpleViewVue},
  created() {
    this.$store.dispatch('setRecommendedGroups', {userId: this.$route.params.id})
    this.$store.dispatch('setGroups', {userId: this.$route.params.id})
  },
  computed: {
    recommendedGroups() {
      return this.$store.getters.getRecommendedGroups
    },
    groups() {
      return this.$store.getters.getGroups
    },
  }
}
</script>

<style scoped>
.container {
  width: 300px;
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
  margin-left: 110px;
}
</style>
