<template>
  <div class="modal-card" style="width: auto">

    <div class="tile is-parent">
      <article class="tile is-child notification is-dark">
        <p class="title is-3 is-black">{{evenement.name}}</p>
        <p class="title is-5">Details</p>

        <div class="columns">
          <div class="column">
            <i class="fas fa-user-friends"></i>
            {{evenement.users.length}} personnes participent
          </div>
        </div>

        <div class="columns">
          <div class="column">
            <i class="fas fa-map-marker-alt"></i>
            {{evenement.address}}
          </div>
        </div>

        <div class="columns">
          <div class="column">
            <i class="fas fa-clock"></i>
            {{evenement.start}} - {{evenement.end}}
          </div>
        </div>

        <div class="columns">
          <div class="column">
            <i class="fa fa-list-alt" aria-hidden="true"></i>
            {{evenement.categories[0]}}
          </div>
        </div>

        <div class="columns">
          <div class="column">
            <i class="fas fa-globe-africa"></i>
            {{evenement.profiles[0]}}
          </div>
        </div>

        <div class="columns">
          <div class="column">
            {{evenement.decription}}
          </div>
        </div>

        <b-button :disabled="dis" id="btn" v-if="!etat" @click="addParticipant()" class="is-white">Participer</b-button>
        <b-button disabled v-else class="is-white">Vous particpez déja à ce évenement</b-button>

      </article>
    </div>
  </div>

</template>

<script>
export default {
  name: "Evenement-Modal",
  props: {
    evenement: Object
  },
  data() {
    return {
      etat: this.$store.getters.getParticipe,
      dis: false
    }
  },
  created() {
    this.$store.dispatch('checkParticipation', {
      eventId: this.evenement.id,
      userId: this.$route.params.id
    })
  },
  methods: {
    addParticipant(){
      this.$store.dispatch('joinEvenement', {
        eventId: this.evenement.id,
        userId: this.$route.params.id
      })
      document.getElementById('btn').innerText = 'Vous particpez à ce évenement'
      this.dis = true
    },
  }
}
</script>

<style scoped>
.modal-card {
  overflow: auto;
}
</style>