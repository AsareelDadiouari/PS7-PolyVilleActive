<template>
  <div>
    <p>
      {{ info }}
    </p>
    <router-view></router-view>
  </div>
</template>

<script>

import Axios from 'axios'

export default {
  name: 'App',
  data () {
    return { info: null}
  },
  mounted() {
    Axios.get("http://localhost:8090/user?firstname=Jean&lastname=Bob")
    .then(response => (this.info = response.data))
  },
  components: {},
}
</script>

<style>

</style>
