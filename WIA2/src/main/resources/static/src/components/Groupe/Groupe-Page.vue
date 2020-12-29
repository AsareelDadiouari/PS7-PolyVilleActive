<template>
    <div>
        <div class="container">
            <br>
            <div class="">
                <h1 class="title is-9"> Listes des Groupes</h1>
            </div>
            <br>
            <div v-if="groups.length > 0" class="columns is-multiline">
                <div class="column is-one-quarter" v-for="currentGroup in groups" :key="currentGroup.id">
                    <GroupeCardViewVue :group="currentGroup" :join="false"/>
                </div>
            </div>
            <div v-else>
                <h3>Vous avez rejoint tous les groupes !</h3>
            </div>
            <br>
        </div>
        <Footer/>    
    </div>

</template>

<script>
import GroupeCardViewVue from './Groupe-CardView.vue';
import Footer from "@/components/Footer";

export default {
  name: "Groupe-Page",
  components: {GroupeCardViewVue, Footer},
  created() {
    this.$store.dispatch('setGroups', {userId: this.$route.params.id})
  },
  computed: {
    groups() {
      return this.$store.getters.getGroups
    }
  }
}
</script>

<style scoped>
.centrage {
  text-align: center;
  vertical-align: middle;
}

.scrollbar {
  height: 550px;
  padding-right: auto;
  overflow: auto;
}
</style>
