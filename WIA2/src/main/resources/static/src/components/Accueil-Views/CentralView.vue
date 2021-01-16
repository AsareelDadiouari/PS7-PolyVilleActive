<template >
  <div class="carousel">
    <p class="subtitle is-3">Les dernières publications</p>
    <div>
      <b-carousel-list :data="publications" :items-to-show="2">
        <template slot="item" slot-scope="publication">
          <Publication v-on:selected="sendClickedPublicationIndex($event)" :publication="publication"/>
        </template>
      </b-carousel-list>
    </div>
    <b-modal :width="550" class="myModal"  v-model="isComponentModalActive" :has-modal-card="true"   scroll="keep">
      <PublicationDetails :publication="selectedPublication"/>
    </b-modal>
  </div>
</template>

<script>
import PublicationDetails from "@/components/Publication/Publication-details";
import Publication from "@/components/Publication/Publication";

export default {
  name: "CentralView",
  components: {PublicationDetails, Publication},
  data() {
    return {
      isComponentModalActive: false,
      selectedPublication: Object,
    }
  },
  created() {
    this.$store.dispatch('setPublications')
  },
  computed: {
    publications() {
      return this.$store.getters.getPublications;
    }
  },
  methods: {
    info(value) {
      this.test = value
    },
    sendClickedPublicationIndex(publication){
      this.isComponentModalActive = true
      this.selectedPublication = publication
    }
  }
}
</script>

<style scoped>
.carousel {
  width: 50%;
  margin-left: 50px;
}

.red {
  background-color: red;
}

.white {
  color: white;
}
</style>
