<template>
  <div class="carousel">
    <p class="subtitle is-3">Les dernières publications</p>

    <div>
      <b-carousel-list :data="publications" :items-to-show="2">
        <template slot="item" slot-scope="publication">
          <div class="card">
            <div class="card-content">
              <div class="content">
                <p class="title is-6">{{ publication.title }}</p>
                <p class="subtitle is-7">Par {{publication.authorName}}</p>
                <p>
                  {{publication.description}}
                </p>
                <div class="field is-grouped">
                  <p class="content">
                    <a><span>
                    {{publication.comments.length -1}} commentaires
                    </span></a>
                  </p>
                  <p class="control" style="margin-left: auto">
                    {{publication.like}}
                    <button @click="like(publication.id)" class="button is-small is-danger is-outlined">
                      <b-icon size="is-small" icon="heart"/>
                    </button>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </template>
      </b-carousel-list>
    </div>
  </div>
</template>

<script>
export default {
  name: "CentralView",
  data() {
    return {
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
    like(publicatonId) {
      this.$store.dispatch('likePublication', {
        id : publicatonId
      })
    }
  }
}
</script>

<style scoped>
.carousel {
  width: 50%;
  margin-left: 50px;
}
</style>
