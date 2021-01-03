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
              Stationement et transport en commun
            </b-navbar-item>
          </b-navbar-dropdown>
          <b-navbar-item class="item" href="#">
            Contact
          </b-navbar-item>
          <b-navbar-item class="item" :href="'/polyville/' + $route.params.id + '/groupes'">
            Groupes
          </b-navbar-item>
        </template>
        <div class="search-bar" slot="end">
          <b-field>
            <b-autocomplete placeholder="Search..."
                     type="search"
                     icon="magnify"
                     icon-clickable
                     @icon-click="searchIconClick" @select="option => (selected = option)"
                            :data="data" ref="autocomplete" v-model="name"
                            group-field="type"
                            group-options="items"
                            @typing="getAsyncData"
                            :loading="isFetching"

            >
              <template slot-scope="props">
                <div class="media">
                  <div class="media-left">
                    <img width="32" :src="`https://image.tmdb.org/t/p/w500/${props.option.poster_path}`">
                  </div>
                  <div class="media-content">
                    {{ props.option.title }}
                    <br>
                    <small>
                      Released at {{ props.option.release_date }},
                      rated <b>{{ props.option.vote_average }}</b>
                    </small>
                  </div>
                </div>
              </template>

            </b-autocomplete>
          </b-field>          
        </div>
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
import debounce from 'lodash/debounce'

export default {
  name: "Header",
  props: {
    userName: String,
    picturePath: String
  },
  data() {
    return{
      data: [],
      isFetching: false,
      name: '',
      selected: null
    }
  },
  methods: {
    searchIconClick() {
      console.log("Search Icon is clicked")
    },
    getAsyncData: debounce(function (name) {
      if (!name.length) {
        this.data = []
        return
      }
      this.isFetching = true
      this.$http.get(`http://localhost:8090/events`)
          .then(({ data }) => {
            this.data = []
            data.results.forEach((item) => this.data.push(item))
          })
          .catch((error) => {
            this.data = []
            throw error
          })
          .finally(() => {
            this.isFetching = false
          })
    }, 500)
  },
  created() {
    this.$store.dispatch('setUser', {userId: this.$route.params.id})
  },
  computed: {
    user() {
      return this.$store.getters.getUser
    },
    filteredDataArray() {
      return this.$store.getters.getPublications.filter((option) => {
        return option
            .toString()
            .toLowerCase()
            .indexOf(this.name.toLowerCase()) >= 0
      })
    }
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

</style>
