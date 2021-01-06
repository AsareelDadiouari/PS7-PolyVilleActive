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
              :locale="locale"
              :show-week-number="showWeekNumber"
              icon="calendar-today"
              placeholder="Click to select..."
              trap-focus>
          </b-datepicker>
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
                              {{ item.name_fr }}
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
      <PolyTripStart @onRetourFromVisite="getRetourVal($event)" :tout-leslieux="this.lieux" />
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
  created() {
    this.$http.get('http://localhost:8090/visites').then((res) => {
      this.lieux = res.data
    })
  },
  computed: {
    listeLieux() {
      const liste = [];

      liste.push(this.lieux['listEvents'])
      liste.push(this.lieux['listPatrimoines'])
      liste.push(this.lieux['listStore'])

      return liste
    }
  },
  methods: {
    startVisite(){
      this.$store.dispatch('sendClickedDate', {selectedDate: this.selected})
      this.visitStarted = !this.visitStarted
    },
    getRetourVal(val){
      this.visitStarted = val
    }
  },
  watch: {
    lieux: {
      handler() {
        if (this.dateIsClicked) {
          console.log(this.selected)
          this.lieux['listEvents'] = this.lieux['listEvents'].sort((a, b) => {
            const date1 = Date.parse(a.start)
            const date2 = Date.parse(b.start)
            return date1 < this.selected && date2 > this.selected
          })
          return this.lieux['listEvents']
        }
        return this.lieux['listEvents']
      },
      deep: true
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