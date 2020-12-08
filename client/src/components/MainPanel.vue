<template>
  <div class="container is-fluid">
    <Header /> <!--:picture-path="user.picture" :user-name="user.name"-->
    <router-view></router-view>
  </div>
</template>

<script>
import Header from "@/components/Header";

export default {
  name: "MainPanel",
  components: {Header},
  props: ['user']
}
</script>

<style scoped>

</style>
