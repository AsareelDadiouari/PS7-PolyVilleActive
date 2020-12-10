<template><div class="container">
        <div class="centrage">
        <h1 class="title is-1">Commerces</h1></div>
        <div class="columns is-multiline">
              <div class="column is-4" v-for="index in 10" :key="index">
                <commerce-simple-view/>
                </div>
                </div>
  </div>

</template>

<script>
import CommerceSimpleView from './Commerce-SimpleView.vue';

export default {
  name: "Commerce-Page",
  components: {CommerceSimpleView}
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
