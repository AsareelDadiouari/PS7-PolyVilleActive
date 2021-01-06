<template><div>
  <div class="container">
    <h1 class="title is-1">Commerces</h1>
    <div class="search-bar" slot="end">
      <SearchBar @onInputChange="getSearchValue($event)"/>
    </div>
    <div class="columns is-multiline">
      <div class="column is-4" v-for="currentStore in stores" :key="currentStore.id">
        <Commerce-simple-view :store="currentStore"/>
      </div>
    </div>
  </div>
  <br>
<Footer/>
</div>
</template>

<script>
import CommerceSimpleView from './Commerce-SimpleView.vue';
import Footer from "@/components/Footer";
import SearchBar from "@/components/SearchBar";

export default {
  name: "Commerce-Page",
  components: {SearchBar, CommerceSimpleView, Footer},
  data() {
    return {
      search: ''
    }
  },
  created() {
    this.$store.dispatch('setStores')
  },
  computed: {
    stores() {
      return this.$store.getters.getStores.filter( (data) => {
        return data.name_fr.toLowerCase().includes(this.search.toLowerCase())
      })
    }
  },
  methods: {
    getSearchValue(val){
      this.search = val
    }
  }
}
</script>

<style scoped>

.centrage {
  text-align: center;
  padding-bottom: 50px;
  padding-top: 20px;
}
</style>
