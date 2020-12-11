<template>
  <div>
    <div class="grand">
      <p class="title is-6">Événements recommandés</p>
      <div class="evennement">
        <div v-for="currentEvenementsRecommandations in evenementsRecommandations" :key="currentEvenementsRecommandations.id" class="column is-one-fifth">
          <EvenementSimpleView :evenementRecommandations="currentEvenementsRecommandations"/>
        </div>
      </div>
    </div>
    <br>
    <br>
    <div class="thebutton">
    <router-link to="/evenements"><button class="button is-primary">
      Voir plus d'événements
      </button></router-link></div>
  </div>

</template>

<script>
import EvenementSimpleView from "@/components/Evenement/Evenement-SimpleView";

export default {
  name: "LeftView",
  components: {EvenementSimpleView},
  created() {
    this.$store.dispatch('setEvenementsRecommandations')
  },
  computed: {
    evenementsRecommandations() {
      return this.$store.getters.getEvenementsRecommandations
    }
  }
}
</script>

<style scoped>
.evennement {
  width: auto;
  height: 330px;
  overflow: auto;
}

.grand {
  width: auto;
  height: 425px;
  border-radius: 25px;
  box-shadow: 3px 3px 2px #9f9e9e;
  padding-top: 10px;
  position: relative;
  overflow: auto;
  top: 40px;
}

.thebutton {
  text-align: center;
}
</style>
