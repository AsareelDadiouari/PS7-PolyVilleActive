<template>
  <div>
    <div v-for="index in 10" :key="index" class="column is-one-fifth">
      <EvenementSimpleView/>
    </div>
  </div>
</template>

<script>
import EvenementSimpleView from "@/components/Evenements/Evenement-SimpleView";
export default {
  name: "Evenement-Container",
  components: {EvenementSimpleView}
}
</script>

<style scoped>

</style>
