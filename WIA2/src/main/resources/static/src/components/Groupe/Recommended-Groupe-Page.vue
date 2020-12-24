<template>
  <div class="container">
    <div class="img">
      <router-link :to="'/polyville/' + $route.params.id"><img src="https://img.icons8.com/pastel-glyph/64/000000/cancel--v1.png"/></router-link>
    </div>
    <div class="centrage">
      <h1 class="title is-9">Groupes suggérés</h1></div>
    <br>
    <div v-if="recommendedGroups.length > 0" class="scrollbar">
      <div class="column" v-for="currentGroup in recommendedGroups" :key="currentGroup.id">
        <RecommendedGroupeCardViewVue :group="currentGroup" :recommended="true"/>
      </div>
    </div>
    <div v-else-if="groups.length > 0" class="scrollbar">
      <h2>Vous n'avez plus de groupes recommandés, voici la liste de tous les groupes !</h2>
      <div class="column" v-for="currentGroup in groups" :key="currentGroup.id">
        <RecommendedGroupeCardViewVue :group="currentGroup" :recommended="false"/>
      </div>
    </div>
    <div v-else class="scrollbar">
      <h2>Vous avez rejoint tous les groupes !</h2>
    </div>
  </div>

</template>

<script>
import RecommendedGroupeCardViewVue from './Recommended-Groupe-CardView.vue';

export default {
  name: "Recommended-Groupe-Page",
  components: {RecommendedGroupeCardViewVue},
  created() {
    this.$store.dispatch('setRecommendedGroups', {userId: this.$route.params.id})
    this.$store.dispatch('setGroups', {userId: this.$route.params.id})
  },
  computed: {
    recommendedGroups() {
      return this.$store.getters.getRecommendedGroups
    },
    groups () {
      return this.$store.getters.getGroups
    }
  }
}
</script>

<style scoped>
.container {
  height: 700px;
  width: 90%;
  margin: auto;
  padding-top: 7px;
  border: solid #9f9e9e;
  position: relative;
  top: 40px;
}

.centrage {
  text-align: center;
  vertical-align: middle;
}

.scrollbar {
  height: 550px;
  padding-right: auto;
  overflow: auto;
}

.img {
  text-align: right;
  padding-right: 5px;
}

h2 {
  margin-left: 20px;
  font-size: 20px;
}
</style>
