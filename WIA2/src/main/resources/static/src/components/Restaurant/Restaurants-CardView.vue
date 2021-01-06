<template>
  <div>
    <div class="card">

      <div class="card-content">
        <div class="media">
          <div class="media-left">
            <i class="fas fa-utensils"></i>
          </div>
          <div class="media-content">
            <p class="title is-4">{{restaurant.name}} </p>
            <p class="subtitle is-6">{{restaurant.adress}}</p>
          </div>
        </div>

        <div class="content">
          <p>Services:</p>
          <ul>
            <li v-for="service in restaurant.services" :key="service">{{service}}</li>
          </ul>
          <p>Autres:</p>
          <ul>
            <li v-for="amenities in restaurant.amenities" :key="amenities">{{amenities}}</li>
          </ul>
          <br>
          <p class="control" style="margin-left: auto">
            <span style="margin-right: 5px">{{restaurant.likes.length}}</span>
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
  </div>
</template>

<script>
export default {
  name: "Restaurants-CardView",
  props: {
    restaurant: Object
  },
  computed: {
      alreadyLike() {
          var response = false;
          this.restaurant.likes.forEach(element => {
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
          this.$store.dispatch('likeRestaurant', {
              id : this.restaurant.id,
              userId: this.$route.params.id
          })
      },
      unlike() {
          this.$store.dispatch('unlikeRestaurant', {
              id : this.restaurant.id,
              userId: this.$route.params.id
          })
      }
  }
}
</script>

<style scoped>

</style>
