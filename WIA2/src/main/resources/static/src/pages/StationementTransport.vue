<template>
  <div class="container">
    <div class="carte has-text-centered">
      <h1 class="title is-1">Transports en commun</h1>
      <LMap :zoom="zoom" :center="center">
        <LTileLayer :url="url"></LTileLayer>
        <div v-for="stopBus in bus" :key="stopBus.id">
          <LMarker :lat-lng="[stopBus.latitude,stopBus.longitude]"></LMarker>
        </div>
      </LMap>
    </div>
  </div>
</template>

<script>
import { LMap, LTileLayer, LMarker } from "vue2-leaflet";
export default {
  name: "Map",
  components: {
    LMap,
    LTileLayer,
    LMarker
  },
  data() {
    return {
      url: "https://{s}.tile.osm.org/{z}/{x}/{y}.png",
      zoom: 15,
      center: [43.6999,7.27927],
      bounds: null
    };
  },
  created()
  {
    this.$store.dispatch('setBus')
  },
  computed: {
    bus() {
      return this.$store.getters.getBus
    }
  }
};
</script>

<style scoped>
.carte {
  height: 50vh;
  margin-top: 75px;
  width: 50%;
  justify-content: center;
  align-items: center;
  margin-left: 320px;

}
</style>