<template>
  <div class="card">
    <div class="card-content">
      <div class="media">
        <div class="media-content">
          <div class="title is-4">{{group.name}}</div>
          <div class="subtitle is-7">{{group.members.length}} membres - 20 publications par jour</div>
          <div class="content">
            <p>Intérêts :</p>
            <ul>
              <li v-for="interest in group.interests" :key="interest"> {{interest}} </li>
            </ul>
            <p>Types :</p>
            <ul>
              <li v-for="type in group.types" :key="type"> {{type}} </li>
            </ul>
          </div>
          <br>
          <div class="substile is-6" maxlength="240">{{group.description}}</div>
          <br>
        </div>
        <a class="image">
            <figure class="image is-2by1">
              <img src="https://bulma.io/images/placeholders/96x96.png" alt="Placeholder image">
            </figure>
        </a>
        <!-- <span class="vertical-line"></span> -->
        <button class="button is-primary" @click="addMember" >Rejoindre</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Groupe-CardView",
  props: {
    group: Object
  },
  methods: {
    addMember() {
      this.$store.dispatch("addMember", {groupId: this.group.id, userId: 1})
    }
  }
}
</script>

<style scoped>
.image {
  height: 200px;
  width: 400px;
  float: left;
  margin: auto;
  margin-right: 80px;
}

.button {
  margin: auto;
  float: right;
}

.card {
  width: 95%;
  height: auto;
  border-width: 1.5px;
  border-style: groove;
  margin-left: auto;
  margin-right: auto;
  vertical-align: middle;
  overflow: hidden;
}

.vertical-line {
  border-left: 2px solid #000;
  display: inline-block;
  height: 200px;
  margin: 0 20px;
}
</style>
