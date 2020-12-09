<template>
  <div>
    <nav class="Accueil">
      <div>
        <EvenementContainer/>
      </div>
      <CentralView/>
      <div>
        <GroupePageReduit/>
      </div>
    </nav>
    <Footer/>
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import EvenementContainer from "@/components/Evenement/Evenement-Container";
import CentralView from "@/components/CentralView";
import GroupePageReduit from "@/components/Groupe/Groupe-PageReduit";

export default {
  name: "Accueil",
  components: {GroupePageReduit, CentralView, EvenementContainer, Footer},
}
</script>

<style scoped>

.Accueil {
  overflow: hidden;
  position: static;
  display: flex;
  width: 100%;
  justify-content: space-between;
  margin-left: 100px;
}


</style>
