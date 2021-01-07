<template>
<div>
  <div class="container">
    <h1 class="subtitle is-1">Mes contacts</h1>
    <div class="columns is-multiline">
      <div class="column is-one-quarter" v-for="currentContact in contacts" :key="currentContact.id">
            <Contact-card-view :contact="currentContact"/>
      </div>
    </div>
  </div>
  <Footer/>
</div>
</template>

<script>
import Footer from "@/components/Footer";
import ContactCardView from './Contact-CardView.vue';


export default {
  name: "Contact-Page",
  components: { Footer, ContactCardView},
  created() {
    this.$store.dispatch('setContacts', {userId: this.$route.params.id})
  },
  computed: {
    contacts() {
      return this.$store.getters.getContacts
    }
  },
}
</script>

<style scoped>

</style>
