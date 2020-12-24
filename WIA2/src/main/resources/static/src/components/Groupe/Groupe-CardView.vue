<template v-if="isMember">
  <div class="card">
    <div class="card-image">
        <figure class="image is-3by2">
          <img src="https://bulma.io/images/placeholders/96x96.png" alt="Placeholder image">
        </figure>
      </div>
    <div class="card-content">
      <div class="media">
        <div class="media-content">
          <div class="title is-4">{{group.name}}</div>
          <div class="subtitle is-7">{{group.members.length}} membres</div>
        </div>
        <button v-if="!join" class="button is-primary" @click="addMember" >Rejoindre</button>
        <button v-else class="button is-primary" @click="removeMember" >Quitter</button>
      </div>
      <div class="content">
        <p>Intérêts :</p>
        <ul>
          <li v-for="interest in group.interests" :key="interest"> {{interest}} </li>
        </ul>
        <p>Types :</p>
        <ul>
          <li v-for="type in group.types" :key="type"> {{type}} </li>
        </ul>
        <br>
        <div class="substile is-6">{{group.description}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Groupe-CardView",
  props: {
    group: Object,
    join: Boolean
  },
  methods: {
    addMember() {
      this.$store.dispatch("addMember", {request: 'setGroups', groupId: this.group.id, userId: this.$route.params.id, to: "Groups"})
    },
    removeMember() {
      this.$store.dispatch("removeMember", {request: 'setGroups', groupId: this.group.id, userId: this.$route.params.id})
    }
  }
}
</script>

<style scoped>
</style>
