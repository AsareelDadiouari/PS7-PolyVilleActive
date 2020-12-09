<template>
  <div class="groupe">
    <div class="groupe-desc">
      <div class="title is-6"> Nom du groupe </div> <!--{{groupe.nom}}-->
      <div class="subtitle is-7"> 900 membres - 1 publication par jour</div> <!--{{groupe.membres}} - {{groupe.publications}}-->
    </div>
  </div>
</template>

<script>
export default {
  name: "Groupe-SimpleView"
}
</script>

<style scoped>
.groupe {
  display: flex;
  border-bottom: groove;
  padding: 1px 1px 1px 1px;
}

.groupe-desc {

}
</style>