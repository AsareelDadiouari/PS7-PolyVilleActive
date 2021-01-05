<template>
  <div>
    <div class="container">
      <div class="has-text-centered">
        <img width="100" src="../assets/polytrip.png" alt="photo">
        <h1 class="title">PolyTrip</h1>
      </div>
      <div class="columns">
        <div id="left">
          <div class="card" style="margin-top: 100px">
            <div class="card-content">
              <p class="title">
                Faites moi découvrir Polyville !
              </p>
            </div>
          </div>

          <div id="pickers" class="card" style="margin-top: 50px;">
            <div class="card-content">
              <div class="field">
                <b-checkbox>Lieux les mieux notés</b-checkbox>
              </div>
              <div class="field">
                <b-checkbox v-model="dateIsClicked">Lieux par date</b-checkbox>
              </div>
            </div>

          </div>
          <b-datepicker
              v-if="dateIsClicked"
              v-model="selected"
              :show-week-number="showWeekNumber"
              :locale="locale"
              placeholder="Click to select..."
              icon="calendar-today"
              trap-focus>
          </b-datepicker>
        </div>

        <div class="right has-text-centered" style="margin-left: auto;">
          <div class="card"  >
            <div class="card-content">
              <p class="title">
                Visite proposée
              </p>
              <div class="content" style="overflow: scroll; height: 600px">
                <b-notification ref="element" :closable="false">
                  <div  v-for="(lieu, index) in listeLieux" :key="index">
                    <div v-for="(item, indx) in lieu" :key="indx">
                      <div v-if="index < 2">
                        <b-collapse class="card" animation="slide" aria-id="contentIdForA11y3" :open="false">
                          <div
                              slot="trigger"
                              slot-scope="props"
                              class="card-header"
                              role="button"
                              aria-controls="contentIdForA11y3">
                            <p class="card-header-title ">
                              {{item.name}}
                            </p>
                            <a class="card-header-icon">
                              <b-icon
                                  :icon="props.open ? 'menu-down' : 'menu-up'">
                              </b-icon>
                            </a>
                          </div>
                          <div class="card-content">
                            <div style="height: 400px; width: 500px" class="card-content">
                                <l-map :zoom="zoom" :center="[item.latitude, item.longitude]">
                                  <LTileLayer :url="url"></LTileLayer>
                                  <LMarker :lat-lng="[item.latitude,item.longitude]"></LMarker>
                                </l-map>
                            </div>
                          </div>
                          <footer class="card-footer">
                            <b-button id="passer" class="card-footer-item">Passer au lieu suivant</b-button>
                          </footer>
                        </b-collapse>
                      </div>
                      <div v-else>
                        <b-collapse class="card" animation="slide" aria-id="contentIdForA11y3" :open="false">
                          <div
                              slot="trigger"
                              slot-scope="props"
                              class="card-header"
                              role="button"
                              aria-controls="contentIdForA11y3">
                            <p class="card-header-title">
                              {{item.name_fr}}
                            </p>
                            <a class="card-header-icon">
                              <b-icon
                                  :icon="props.open ? 'menu-down' : 'menu-up'">
                              </b-icon>
                            </a>
                          </div>
                          <div class="card-content">
                            <div style="height: 400px; width: 500px"  class="content">
                              <l-map :zoom="zoom" :center="[item.latitude, item.longitude]">
                                <LTileLayer :url="url"></LTileLayer>
                                <LMarker :lat-lng="[item.latitude,item.longitude]"></LMarker>
                              </l-map>
                            </div>
                          </div>
                          <footer class="card-footer">
                            <b-button id="passer" class="card-footer-item">Passer au lieu suivant</b-button>
                          </footer>
                        </b-collapse>
                      </div>
                    </div>
                  </div>
                </b-notification>
                <b-button id="commencer" style="width: 100%; margin-bottom: 25px">Commencer la visite</b-button>
              </div>
            </div>
          </div>

        </div>
      </div>


    </div>
    <Footer />
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import { LMap, LTileLayer, LMarker } from "vue2-leaflet";

export default {
  name: "PolyTrip",
  components: {Footer,LMap,
    LTileLayer,
    LMarker},
  data() {
    return {
      selected: new Date(),
      showWeekNumber: false,
      locale: undefined, // Browser locale
      dateIsClicked: false,
      lieux: '',
      url: "https://{s}.tile.osm.org/{z}/{x}/{y}.png",
      zoom: 16,
      //center: [43.6999,7.27927],
      bounds: null
    }
  },
  beforeMount() {
    let start_time = new Date().getTime();

    this.$http.get('http://localhost:8090/visites').then( (res) => {
      this.lieux = res.data

      const loadingComponent = this.$buefy.loading.open({
        container: this.$refs.element.$el
      })

      setTimeout( () => {
        loadingComponent.close()
      }, new Date().getTime() - start_time)

    })

  },
  created() {

  },
  computed: {
    listeLieux(){
      const liste = [];
      liste.push(this.lieux['listEvents'])
      liste.push(this.lieux['listPatrimoines'])
      liste.push(this.lieux['listStore'])

      return liste
    }
  }
}
</script>

<style scoped>
#commencer {

}

#commencer:hover {
  background-color: #5fb7d2;
  color: white;
}

#passer:hover {
  background-color: #a982e7;
  color: white;

}

#passer {

}
</style>