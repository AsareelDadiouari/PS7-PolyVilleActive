<template>
  <section>
    <b-field>
      <b-input
          open-on-focus
          placeholder="Rechercher..."
          size="is-medium"
          @input.native="inputEmitter($event.target.value)"
      >
      </b-input>
    </b-field>
  </section>
</template>

<script>

export default {
  name: 'SearchBar',
  methods: {
    inputEmitter(data){
      this.$emit('onInputChange', data)
    }
  }
}
</script>