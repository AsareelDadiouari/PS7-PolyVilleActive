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
              <div v-for="(lieu, index) in listeLieux2" :key="index">
                <div v-for="(item, indx) in lieu" :key="indx">
                  <div v-if="index < 2">
                    <b-collapse :open="indx === 0 && index === 0" animation="slide" aria-id="contentIdForA11y3" class="card">
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
                        <b-button id="passer" class="card-footer-item">Passer au lieu suivant</b-button>
                      </footer>
                    </b-collapse>
                  </div>
                  <div v-else>
                    <b-collapse :open="false" animation="slide" aria-id="contentIdForA11y3" class="card">
                      <div
                          slot="trigger"
                          slot-scope="props"
                          aria-controls="contentIdForA11y3"
                          class="card-header"
                          role="button">
                        <p class="card-header-title">
                          {{ item.name_fr }}
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
        {{getLieuxArray.length}}
        <b-button @click="retour" class="is-primary is-fullwidth">Terminer la visite</b-button>
      </div>

    </div>

    <div style="width: 100%; "  class="container is-fluid map">
      <LMap id="map" :center="center" :zoom="zoom">
        <LTileLayer :url="url"></LTileLayer>
        <div v-for="(lieu, index) in listeLieux2" :key="index">
          <div v-for="(item, idx) in lieu" :key="idx">
            <div v-if="index < 2">
              <LMarker :lat-lng="[item.latitude, item.longitude]">
                <LPopup >{{item.name}}</LPopup>
                <!--<l-polyline :lat-lngs="center" color="blue"></l-polyline>-->
              </LMarker>
            </div>
            <div v-if="index >= 2">
              <LMarker :lat-lng="[item.latitude, item.longitude]">
                <LPopup >{{item.name_fr}}</LPopup>
                <!--<l-polyline :lat-lngs="center" color="blue"></l-polyline>-->
              </LMarker>
            </div>
          </div>

        </div>

      </LMap>
    </div>
  </div>
</template>

<script>
import { LMap, LTileLayer, LMarker, LPopup,   } from 'vue2-leaflet';

export default {
  name: "PolyTrip-start",
  components: {LPopup, LMap, LMarker, LTileLayer, },
  props: {
    toutLeslieux: Object
  },
  data() {
    return {
      lieux: this.toutLeslieux,
      url: "https://{s}.tile.osm.org/{z}/{x}/{y}.png",
      zoom: 16,
      center: [43.6999,7.27927],
      bounds: null,
      indexLieu: 0,
      indexTypeLieu: 0,
      //currentLieu: [this.getLieuxArray[this.indexTypeLieu][this.indexLieu].latitude, this.getLieuxArray[this.indexTypeLieu][this.indexLieu].longitude,]
    }
  },
  created() {

  },
  computed: {
    listeLieux2() {
      const liste = [];

      liste.push(this.lieux['listEvents'])
      liste.push(this.lieux['listPatrimoines'])
      liste.push(this.lieux['listStore'])
      return liste
    }
  },
  methods: {
    retour(){
      this.$emit('onRetourFromVisite', false)
    },
    getLieuxArray() {
      const liste = [];

      liste.push(this.lieux['listEvents'])
      liste.push(this.lieux['listPatrimoines'])
      liste.push(this.lieux['listStore'])
      console.log(liste[0][0].latitude)
      return liste
    },
    passerSuivant(){

    }
  }
}
</script>

<style scoped>
.start-visit {

}
</style>