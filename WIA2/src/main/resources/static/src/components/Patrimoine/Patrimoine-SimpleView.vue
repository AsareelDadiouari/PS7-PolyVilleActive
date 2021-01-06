<template>
  <div>
    <div class="card">
      <a><div class="card-image">
        <figure class="image  is-3by2">
          <img :src="patrimoine.image" alt="Placeholder image">
        </figure>
      </div></a>
      <div class="card-content">
        <div class="media">
          <div class="media-content">
            <p class="title is-4">{{ patrimoine.name }}</p>
          </div>
        </div>
        <p class="control" style="margin-left: auto">
          <span style="margin-right: 5px">{{patrimoine.likes.length}}</span>
          <button v-if="!alreadyLike" @click="like()" class="button is-small is-danger is-outlined">
              <b-icon size="is-small" icon="heart"/>
          </button>
          <button v-else @click="unlike()" class="button is-small is-danger">
              <b-icon size="is-small" icon="heart"/>
          </button>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Patrimoine-SimpleView",
  props: {
    patrimoine: Object
  },
  computed: {
      alreadyLike() {
          var response = false;
          this.patrimoine.likes.forEach(element => {
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
        this.$store.dispatch('likePatrimoine', {
            id : this.patrimoine.id,
            userId: this.$route.params.id
        })
        this.patrimoine.likes.push("http://www.ps7-wia2.com/users/" + this.$route.params.id + "")
    },
    unlike() {
        this.$store.dispatch('unlikePatrimoine', {
            id : this.patrimoine.id,
            userId: this.$route.params.id
        })
        this.patrimoine.likes.pop("http://www.ps7-wia2.com/users/" + this.$route.params.id + "")
    }
  }
}
</script>

<style scoped>
.card-image {
    width: 480px;
    height: 320px;
}

.card {
    width: 480px;
    border: groove;
}
</style>
