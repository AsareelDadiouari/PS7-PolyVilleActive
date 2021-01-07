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
    <router-link :to="'/polyville/' + $route.params.id + '/evenements'"><button class="button is-primary">
      Voir plus d'événements
      </button></router-link></div>
      <div class="grand">
        <p class="title is-6">Contacts recommandés</p>
        <div class="contact">
          <div v-for="currentContact in contactsRecommended" :key="currentContact.id" class="column">
            <Contact-simple-view :contact="currentContact"/>
            </div>
          </div>
      </div>
      <br>
      <br>
  </div>

</template>

<script>
import EvenementSimpleView from "@/components/Evenement/Evenement-SimpleView";
import ContactSimpleView from '../Contacts/Contact-SimpleView.vue';

export default {
  name: "LeftView",
  components: {EvenementSimpleView, ContactSimpleView},
  created() {
    this.$store.dispatch('setEvenementsRecommandations', {userId: this.$route.params.id})
    this.$store.dispatch('setContactsRecommended', {userId: this.$route.params.id})
  },
  computed: {
    evenementsRecommandations() {
      return this.$store.getters.getEvenementsRecommandations
    },
    contactsRecommended() {
      return this.$store.getters.getContactsRecommended
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

.contact {
  width: auto;
  height: auto;
  overflow: auto;
}

.thebutton {
  text-align: center;
}
</style>
