<template>
  <div>
    <div class="grand">
      <p class="title is-6">Evenements recommandés</p>
      <div class="evennement">
        <div v-bind="applyScroll(index)" v-for="index in 10" :key="index" class="column is-one-fifth">
          <EvenementSimpleView/>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import EvenementSimpleView from "@/components/Evenement/Evenement-SimpleView";

export default {
  name: "LeftView",
  components: {EvenementSimpleView},
  methods: {
    applyScroll(index) {
      if (index >= 5) {
        document.getElementsByClassName('evennement').style
      }
      console.log(index)
    }
  }
}
</script>

<style scoped>
.evennement {

  width: auto;
  height: 310px;
  overflow: auto;
}

.grand {
  width: auto;
  height: 395px;
  border-radius: 25px;
  box-shadow: 3px 3px 2px #9f9e9e;
  padding-top: 7px;
  position: relative;
  max-height: 395px;
  margin-top: 25px;
  overflow: auto;
  top: 40px;
}
</style>
