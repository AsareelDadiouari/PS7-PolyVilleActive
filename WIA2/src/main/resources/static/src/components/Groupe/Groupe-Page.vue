<template>
  <div class="container">
    <div class="img">
      <img src="https://img.icons8.com/pastel-glyph/64/000000/cancel--v1.png"/></div>
    <div class="centrage">
      <h1 class="title is-9">Groupes suggérés</h1></div>
    <br>
    <div class="scrollbar">
      <div class="column" v-for="currentGroup in groups" :key="currentGroup.id">
        <groupe-card-view-vue :group="currentGroup"/>
      </div>
    </div>
  </div>

</template>

<script>
import GroupeCardViewVue from './Groupe-CardView.vue';

export default {
  name: "Groupe-Page",
  components: {GroupeCardViewVue},
  created() {
    this.$store.dispatch('setGroups')
  },
  computed: {
    groups() {
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
</style>
