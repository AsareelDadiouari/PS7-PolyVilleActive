<template>
    <div class="card">
      <!-- <a><div class="card-image">
        <figure class="image  is-3by2">
          <img :src="require('../../assets/Persona4.png')" alt="Placeholder image">
        </figure>
      </div></a> -->
      <div class="card-content">
        <div class="media">
          <div class="media-left">
            <i class="fas fa-store"></i>
          </div>
          <div class="media-content">
            <p class="title is-4">{{store.name_fr}}</p>
            <p class="subtitle is-6">{{store.address}}</p>
            <p class="subtitle is-6" v-if="store.opening != ''">Ouvertures : {{store.opening}}</p>
          </div>
        </div>
        <div class="content">
          <p>Services:</p>
          <ul>
            <li v-for="categorie in store.categories" :key="categorie">{{categorie}}</li>
          </ul>
          <br>
          <p class="control" style="margin-left: auto">
            <span style="margin-right: 5px">{{store.likes.length}}</span>
            <button v-if="!alreadyLike" @click="like()" class="button is-small is-danger is-outlined">
                <b-icon size="is-small" icon="heart"/>
            </button>
            <button v-else @click="unlike()" class="button is-small is-danger">
                <b-icon size="is-small" icon="heart"/>
            </button>
          </p>
        </div>
      </div>
      <button @click="like(publication)" class="button is-small is-danger is-outlined">
        <b-icon size="is-small" icon="heart"/>
      </button>
    </div>
</template>

<script>
export default {
  name: "Commerce-SimpleView",
  props: {
      store: Object,
  },
  computed: {
      alreadyLike() {
          var response = false;
          this.store.likes.forEach(element => {
              var id = element.split("/")
              if (id[id.length - 1] == this.$route.params.id) {
              response = true;
              return;
              }
          });
          return response;
      }
  },
  methods: {
    like() {
        this.$store.dispatch('likeStore', {
            id : this.store.id,
            userId: this.$route.params.id
        })
    },
    unlike() {
        this.$store.dispatch('unlikeStore', {
            id : this.store.id,
            userId: this.$route.params.id
        })
    }
  }
}
</script>

<style scoped>

</style>
