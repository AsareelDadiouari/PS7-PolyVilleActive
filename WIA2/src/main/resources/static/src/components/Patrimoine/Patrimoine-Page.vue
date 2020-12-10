<template><div class="container">
        <div class="centrage">
        <h1 class="title is-1">Patrimoine</h1></div>
        <div class="columns is-multiline">
              <div class="column is-4" v-for="index in 10" :key="index">
                <patrimoine-simple-view/>
                </div>
                </div>
  </div>

</template>

<script>
import PatrimoineSimpleView from './Patrimoine-SimpleView.vue';

export default {
  name: "Patrimoine-Page",
  components: {PatrimoineSimpleView}
}
</script>

<style scoped>
.container {
    height: auto;
    padding-top: 7px;
    border-left: 1px solid;
    border-right: 1px solid;
}

.centrage {
  text-align: center;
  padding-bottom: 50px;
  padding-top: 20px;
}
</style>
