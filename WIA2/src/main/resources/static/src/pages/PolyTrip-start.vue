<template>
  <div style="margin-top: 10px" class="columns">
    <div  class="container start-visit">
      <div class="card" style="margin-top: 10px;  width: 100%">
        <div class="card-content ">
          <p class="title">
            Prochain lieu le plus proche à visiter
          </p>
        </div>
        <div class="card">
          <div class="card-content">
            <div  class="content" style="overflow: scroll; height: 600px">
              <div v-for="(lieu, index) in toutLeslieux" :key="index">
                <div v-for="(item, indx) in lieu" :key="indx">
                  <div v-if="index < 2">
                    <b-collapse ref="collapse" :open="indx === 0 && index === 0" animation="slide" aria-id="contentIdForA11y3" class="card">
                      <div
                          slot="trigger"
                          slot-scope="props"
                          aria-controls="contentIdForA11y3"
                          class="card-header"
                          role="button">
                        <p class="card-header-title ">
                          {{ item.name }}
                        </p>
                        <a class="card-header-icon">
                          <b-icon
                              :icon="props.open ? 'menu-down' : 'menu-up'">
                          </b-icon>
                        </a>
                      </div>
                      <div class="card-content">
                        <div class="card-content has-text-centered">
                          <img width="300" :src="item.image" alt="img" >
                        </div>
                      </div>
                      <footer class="card-footer">
                        <b-button @click.native="passerSuivant" id="passer" class="card-footer-item">Passer au lieu suivant</b-button>
                      </footer>
                    </b-collapse>
                  </div>
                  <div v-else>
                    <b-collapse ref="collapse" :open="indx === 0 && index === 0" animation="slide" aria-id="contentIdForA11y3" class="card">
                      <div
                          slot="trigger"
                          slot-scope="props"
                          aria-controls="contentIdForA11y3"
                          class="card-header"
                          role="button">
                        <p class="card-header-title">
                          {{ item.name }}
                        </p>
                        <a class="card-header-icon">
                          <b-icon
                              :icon="props.open ? 'menu-down' : 'menu-up'">
                          </b-icon>
                        </a>
                      </div>
                      <div class="card-content has-text-centered">
                        <div class="content" >
                          {{item.address}}
                        </div>
                      </div>
                      <footer class="card-footer">
                        <b-button @click.native="passerSuivant" id="passer" class="card-footer-item">Passer au lieu suivant</b-button>
                      </footer>
                    </b-collapse>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <b-button id="terminer" ref="terminer" @click="retour" class="is-primary is-fullwidth">Terminer la visite</b-button>
      </div>

    </div>
    <div style="width: 100%; "  class="container is-fluid map">
      <LMap id="map" :center="[this.toutLeslieux.listLieux[indexLieu].latitude, this.toutLeslieux.listLieux[indexLieu].longitude]" :zoom="zoom">

        <LTileLayer :url="url"></LTileLayer>

        <LRoutingMachine  :waypoints="waypoints"/>

      </LMap>
    </div>
  </div>
</template>

<script>
import { LMap, LTileLayer  } from 'vue2-leaflet';
import LRoutingMachine from "@/components/LRoutingMachine";

export default {
  name: "PolyTrip-start",
  components: {LRoutingMachine, LMap, LTileLayer, },
  props: {
    toutLeslieux: Object
  },
  data() {
    return {
      url: "https://{s}.tile.osm.org/{z}/{x}/{y}.png",
      zoom: 16,
      center: [43.6999,7.27927],
      bounds: null,
      indexLieu: 0,
      waypoints: []
    }
  },
  created() {
    this.toutLeslieux['listLieux'].forEach( (val) => {
      this.waypoints.push({
        "lat": val.latitude,
        "lng": val.longitude
      })
    })
  },
  mounted() {
    this.$refs.collapse[0].isOpen = true

  },
  methods: {
    retour(){
      this.$emit('onRetourFromVisite', false)
    },
    passerSuivant(){
      this.$refs.collapse[this.indexLieu].isOpen = false

      if (this.indexLieu < this.$refs.collapse.length - 1){
        this.indexLieu++;
        this.$refs.collapse[this.indexLieu].isOpen = true
      }

      if (this.indexLieu + 1 === this.$refs.collapse.length)
        document.getElementById('terminer').style.backgroundColor = '#31b84f'
    },

  }
}
</script>

<style scoped>
#terminer {
  background-color: grey;
}
</style>