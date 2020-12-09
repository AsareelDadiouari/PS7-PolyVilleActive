<template>
  <div class="evennement">
    <div v-bind="applyScroll(index)" v-for="index in 3" :key="index" class="column is-one-fifth">
      <EvenementSimpleView/>
    </div>
  </div>
</template>

<script>
import EvenementSimpleView from "@/components/Evenements/Evenement-SimpleView";

export default {
  name: "Evenement-Container",
  components: {EvenementSimpleView},
  methods : {
    applyScroll(index){
      if (index >= 5){
        document.getElementsByClassName('evennement').style
      }
      console.log(index)
    }
  }
}
</script>

<style scoped>
.evennement {
  overflow-y: scroll;
  max-height: 600px;
}
</style>
