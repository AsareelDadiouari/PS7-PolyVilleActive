<template>
  <div>
    <div class="container">
      <div class="has-text-centered">
        <img alt="photo" src="../assets/polytrip.png" width="75">
        <h1 class="title">PolyTrip</h1>
      </div>
      <div v-if="!this.visitStarted" ref="visiteTemplate" class="columns">
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
              <!--<div class="field">
                <b-checkbox>Lieux les mieux notés</b-checkbox>
              </div>-->
              <div class="field">
                Choisir la date :
              </div>
            </div>

          </div>
          <b-datepicker
              v-model="selected"
              :locale="locale"
              :show-week-number="showWeekNumber"
              icon="calendar-today"
              placeholder="Click to select..."
              trap-focus>
          </b-datepicker>
          <b-button id="selectDate" @click.native="genererListe" style="width: 100%; margin-bottom: 25px">Générer une visite</b-button>
        </div>

        <div  class="right has-text-centered" style="margin-left: auto;">
          <div class="card">
            <div class="card-content">
              <p class="title">
                Visite proposée
              </p>
              <div  class="content" style="overflow: scroll; height: 600px">
                <b-notification ref="element" :closable="false">
                  <div v-for="(lieu, index) in listeLieux" :key="index">
                    <div v-for="(item, indx) in lieu" :key="indx">
                      <div v-if="index < 2">
                        <b-collapse :open="false" animation="slide" aria-id="contentIdForA11y3" class="card">
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
                            <div class="card-content">
                              <img width="300" :src="item.image" alt="img" >
                              <!--<l-map :center="[item.latitude, item.longitude]" :zoom="zoom">
                                <LTileLayer :url="url"></LTileLayer>
                                <LMarker :lat-lng="[item.latitude,item.longitude]">
                                  <LPopup :content="item.address"></LPopup>
                                </LMarker>
                              </l-map>-->
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
                              {{ item.name }}
                            </p>
                            <a class="card-header-icon">
                              <b-icon
                                  :icon="props.open ? 'menu-down' : 'menu-up'">
                              </b-icon>
                            </a>
                          </div>
                          <div class="card-content">
                            <div class="content" >
                              {{item.address}}
                              <!--<l-map :center="[item.latitude, item.longitude]" :zoom="zoom">
                                <LTileLayer :url="url"></LTileLayer>
                                <LMarker :lat-lng="[item.latitude,item.longitude]">
                                  <LPopup :content="item.address">{{ item.image }}</LPopup>
                                </LMarker>
                              </l-map>-->
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

                <b-button id="commencer" @click.native="startVisite" style="width: 100%; margin-bottom: 25px">Commencer la visite</b-button>
              </div>
            </div>
          </div>

        </div>
      </div>

    </div>
    <div class="container is-fluid" style="margin-right: 500px" v-if="this.visitStarted">
      <PolyTripStart @onRetourFromVisite="getRetourVal($event)" :tout-leslieux=listeLieux />
    </div>
    <Footer/>
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import PolyTripStart from "@/pages/PolyTrip-start";

export default {
  name: "PolyTrip",
  components: {
    PolyTripStart,
    Footer
  },
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
      bounds: null,
      visitStarted: false
    }
  },
  computed: {
    listeLieux() {
      return this.$store.getters.getLieux
    }
  },
  methods: {
    selectDate() {
      let month = parseInt(this.selected.getMonth() + 1, 10)
      let day = parseInt(this.selected.getDate(), 10)
      if (month < 10) {
        month = "0" + month
      }
      if (day < 10) {
        day = "0" + day
      }
      return this.selected.getFullYear() + "-" + month + "-" + day
    },
    genererListe() {
      this.$store.dispatch('setLieux', {
        date: this.selectDate()
      })
    },
    startVisite(){
      this.visitStarted = !this.visitStarted
    },
    getRetourVal(val){
      this.visitStarted = val
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