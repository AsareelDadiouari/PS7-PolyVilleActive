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
           <b-navbar-item :href="'/polyville/' + $route.params.id + '/commerces'">
              Commerces
            </b-navbar-item>
            <b-navbar-item :href="'/polyville/' + $route.params.id + '/restaurants'">
            Restaurants
          </b-navbar-item>
          </b-navbar-dropdown>
          <b-navbar-dropdown class="item" label="Service"><b-navbar-item :href="'/polyville/' + $route.params.id + '/evenements'">
              Événements
            </b-navbar-item>
            <b-navbar-item :href="'/polyville/' + $route.params.id + '/stationnement'">
              Stationnement et transports en commun
            </b-navbar-item>
          </b-navbar-dropdown>
          <b-navbar-item class="item" :href="'/polyville/' + $route.params.id + '/contacts'">
            Contacts
          </b-navbar-item>
          <b-navbar-item class="item" :href="'/polyville/' + $route.params.id + '/groupes'">
            Groupes
          </b-navbar-item>
          <b-navbar-item id="polytrip" :transparent="true" class="item" :href="'/polyville/' + $route.params.id + '/polyTrip'">
            PolyTrip
          </b-navbar-item>
        </template>
      </b-navbar>
      <div class="user">
        <nav class="navbar is-light" role="navigation" aria-label="dropdown navigation">
            <div class="image2">
              <div class="image is-32x32">
          <img :src="require('../assets/Persona4.png')"/>
        </div>
        </div>
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-arrowless">
              {{user.firstname}}
            </a>

    <div class="navbar-dropdown">
      <a class="navbar-item" :href="'/polyville/' + $route.params.id + '/mescontacts'">
        Mes contacts
      </a>
      <a class="navbar-item" :href="'/polyville/' + $route.params.id + '/mesgroupes'">
        Mes groupes
      </a>
      <hr class="navbar-divider">
      <a class="navbar-item" href="/">
        Déconnexion
      </a>
  </div>
  </div>
</nav>
</div>
    </div>
  </div>
</template>

<script>


export default {
  name: "Header",
  components: {},
  props: {
    userName: String,
    picturePath: String
  },
  created() {
    this.$store.dispatch('setUser', {userId: this.$route.params.id})
  },
  computed: {
    user() {
      return this.$store.getters.getUser
    },
  },
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
  margin-left: 275px;
  display: flex;
  color: black;
}

.image2 {
  margin-top: 8px;
}

#polytrip:hover {
  background-color: #2e5ffd;
  color: white;
}

#polytrip {
  transition: all .2s ease-in-out;
  color: #429ccd;
}

</style>
