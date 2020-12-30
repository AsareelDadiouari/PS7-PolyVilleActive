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
        <b-button :disabled="dis" id="btn" v-if="!this.$store.getters.getParticipe" @click="addParticipant()" class="is-white">Participer</b-button>
        <b-button disabled v-else class="is-white">Vous particpez déja à ce évenement</b-button>
        <LMap :zoom="zoom" :center="center">
          <LTileLayer :url="url"></LTileLayer>
          <div v-for="stopBus in bus" :key="stopBus.id">
          <LMarker :lat-lng="[stopBus.latitude,stopBus.longitude]"></LMarker>
          </div>
        </LMap>
      </article>
    </div>
  </div>

</template>

<script>
import { LMap, LTileLayer, LMarker } from "vue2-leaflet";
export default {
  name: "Evenement-Modal",
  components: {
    LMap,
    LTileLayer,
    LMarker
  },
  props: {
    evenement: Object
  },
  data() {
    return {
      dis: false,
      url: "https://{s}.tile.osm.org/{z}/{x}/{y}.png",
      zoom: 15,
      center: [this.evenement.latitude,this.evenement.longitude],
      bounds: null
    }
  },
  created() {
    //this.$store.dispatch('setBusRecommandations', {evenement: this.evenement})
    this.$store.dispatch('checkParticipation', {
      eventId: this.evenement.id,
      userId: this.$route.params.id
    })
  },
  computed: {
    bus() {
      return this.$store.getters.getBusRecommandations
    }
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