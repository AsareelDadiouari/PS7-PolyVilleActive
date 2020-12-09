<template>
  <div class="top container is-fluid">
    <div class="level-item has-text-centered container head">
      <b-navbar class=" navbar is-light">
        <template class="items" slot="burger">
          <b-navbar-item class="item" href="#">
            Accueil
          </b-navbar-item>
          <b-navbar-dropdown class="item" label="Tourisme">
            <b-navbar-item href="#">
              Centre culturels
            </b-navbar-item>
            <b-navbar-item href="#">
              Centre sportifs
            </b-navbar-item>
            <b-navbar-item href="#">
              Commerces
            </b-navbar-item>
            <b-navbar-item href="#">
              Présentation et accès
            </b-navbar-item>
          </b-navbar-dropdown>
          <b-navbar-dropdown class="item" label="Service">
            <b-navbar-item href="#">
              Evènements
            </b-navbar-item>
            <b-navbar-item href="#">
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
        <img :src="picturePath"/>
        <p>{{ userName }}</p>
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

</style>
