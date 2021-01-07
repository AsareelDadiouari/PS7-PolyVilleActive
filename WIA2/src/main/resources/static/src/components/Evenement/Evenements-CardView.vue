<template>
  <div>
    <div class="card">
      <div class="card-image">
        <figure class="image is-3by2">
          <img :src="evenement.image" alt="Placeholder image">
        </figure>
      </div>
      <div class="card-content">
        <div class="media">
          <div class="media-left">
            <figure class="image is-48x48">
              <img src="https://static.xx.fbcdn.net/rsrc.php/v3/ys/r/8wTx0Eu2vRq.png" alt="Placeholder image">
            </figure>
          </div>
          <div class="media-content">
            <time style="color: red">Début:{{evenement.start}} Fin:{{evenement.end}}</time>
            <p class="title is-4">{{evenement.name}}</p> <!--  -->
            <p class="subtitle is-6">{{evenement.categories[0]}}</p>
          </div>
        </div>

        <div class="content">
          {{evenement.decription.substring(0,70)}}... <a><span @click="isComponentModalActive = true">Voir plus</span></a>
        </div>
        <p class="control" style="margin-left: auto">
          <span style="margin-right: 5px">{{evenement.likes.length}}</span>
          <button v-if="!alreadyLike" @click="like()" class="button is-small is-danger is-outlined">
              <b-icon size="is-small" icon="heart"/>
          </button>
          <button v-else @click="unlike()" class="button is-small is-danger">
              <b-icon size="is-small" icon="heart"/>
          </button>
        </p>
      </div>
    </div>

    <b-modal :width="550" class="myModal" v-model="isComponentModalActive" :has-modal-card="true"  scroll="keep">
      <EvenementModal :evenement="evenement"/>
    </b-modal>

  </div>
</template>

<script>
import EvenementModal from "@/components/Evenement/Evenement-Modal";
export default {
  name: "Evenements-CardView",
  components: {EvenementModal},
  data() {
    return {
      isComponentModalActive: false
    }
  },
  props: {
    evenement: Object
  },
  computed: {
      alreadyLike() {
          var response = false;
          this.evenement.likes.forEach(element => {
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
          this.$store.dispatch('likeEvenement', {
              id : this.evenement.id,
              userId: this.$route.params.id
          })
          this.evenement.likes.push("http://www.ps7-wia2.com/users/" + this.$route.params.id + "")
      },
      unlike() {
          this.$store.dispatch('unlikeEvenement', {
              id : this.evenement.id,
              userId: this.$route.params.id
          })
          this.evenement.likes.pop("http://www.ps7-wia2.com/users/" + this.$route.params.id + "")
      }
  }
}
</script>

<style scoped>
.card {
  height: auto;
}

</style>
