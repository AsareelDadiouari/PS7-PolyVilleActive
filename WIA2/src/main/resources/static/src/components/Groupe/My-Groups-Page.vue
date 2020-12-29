<template>
    <div>
        <div class="container">
            <br>
            <div class="">
                <h1 class="title is-9"> Mes Groupes</h1>
            </div>
            <br>
            <div v-if="mygroups.length > 0" class="columns is-multiline">
                <div class="column is-one-quarter" v-for="currentGroup in mygroups" :key="currentGroup.id">
                    <GroupeCardViewVue :group="currentGroup" :join="true"/>
                </div>
            </div>
            <div v-else>
                <h3>Vous n'avez pas rejoint de groupes !</h3>
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
  name: "My-Groups-Page",
  components: {GroupeCardViewVue, Footer},
  created() {
    this.$store.dispatch('setMyGroups', {userId: this.$route.params.id})
  },
  computed: {
    mygroups() {
      return this.$store.getters.getMyGroups
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
