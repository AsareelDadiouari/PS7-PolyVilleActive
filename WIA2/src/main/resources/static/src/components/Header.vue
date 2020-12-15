<template>
  <div class="top container is-fluid">
    <div class="level-item has-text-centered container head">
      <b-navbar class=" navbar is-light">
        <template class="items" slot="burger">
          <b-navbar-item class="item" :href="'/polyville/' + $route.params.id">
            Accueil
          </b-navbar-item>
          <b-navbar-dropdown class="item" label="Tourisme">
            <b-navbar-item :href="'/polyville/' + $route.params.id + '/patrimoine'">
              Patrimoine
            </b-navbar-item>
            <b-navbar-item :href="'/polyville/' + $route.params.id + '/centres-sportifs'">
              Centres sportifs
            </b-navbar-item><b-navbar-item :href="'/polyville/' + $route.params.id + '/commerces'">
              Commerces
            </b-navbar-item>
            <b-navbar-item :href="'/polyville/' + $route.params.id + '/restaurants'">
            Restaurants
          </b-navbar-item>
            <b-navbar-item href="#">
              Présentation et accès
            </b-navbar-item>
          </b-navbar-dropdown>
          <b-navbar-dropdown class="item" label="Service"><b-navbar-item :href="'/u/' + $route.params.id + '/evenements'">
              Événements
            </b-navbar-item>
            <b-navbar-item :href="'/polyville/' + $route.params.id + '/stationnement'">
              Stationement et transport en commun
            </b-navbar-item>
          </b-navbar-dropdown>
          <b-navbar-item class="item" href="#">
            Contact
          </b-navbar-item>
        </template>
        <div class="search-bar" slot="end">
          <b-field>
            <b-input placeholder="Search..."
                     type="search"
                     icon="magnify"
                     icon-clickable
                     @icon-click="searchIconClick">
            </b-input>
          </b-field>
        </div>
      </b-navbar>
      <div class="user">
        <div class="image is-32x32">
          <img :src="require('../assets/Persona4.png')"/>
        </div>
        <div class="name">
        <p>{{user.firstname}}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    userName: String,
    picturePath: String
  },
  methods: {
    searchIconClick(){
      console.log("Search Icon is clicked")
    },
  },
  created() {
    this.$store.dispatch('setUser', {userId: this.$route.params.id})
  },
  computed: {
    user() {
      return this.$store.getters.getUser
    }
  }
}
</script>

<style scoped>
.item {
  margin-left: 100px;
}

.top {
  background-color: whitesmoke;
}

.search-bar {
  margin-left: 200px;
  margin-top: 5px;
}

.user {
  margin-left: 200px;
  display: flex;
  color: black;
}

.name {
  padding-left: 8px;
  margin: auto;
}

</style>
