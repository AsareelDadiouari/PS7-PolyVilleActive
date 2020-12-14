<template>
  <div class="groupe">
    <div class="groupe-desc">
      <div class="title is-6">{{group.name}}</div>
      <div class="subtitle is-7"> {{group.members.lenght || 0 }} membres - publications</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Groupe-SimpleView",
  props: {
    group: Object
  }
}
</script>

<style scoped>
.groupe {
  display: flex;
  border-bottom: groove;
  padding: 1px 1px 1px 1px;
  transition: 0.3s;
}

.groupe:hover {
  background-color: #dbd9d9;
  cursor: pointer;
  color: white;
}
</style>
