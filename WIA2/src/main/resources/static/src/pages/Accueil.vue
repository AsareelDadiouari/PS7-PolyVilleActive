<template>
  <div>
    <nav class="Accueil">
      <div>
        <LeftView/>
      </div>
      <CentralView/>
      <div>
        <RightView/>
      </div>
    </nav>
    <Footer/>
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import CentralView from "@/components/Accueil-Views/CentralView";
import LeftView from "@/components/Accueil-Views/LeftView";
import RightView from "@/components/Accueil-Views/RightView";

export default {
  name: "Accueil",
  components: {RightView, LeftView, CentralView, Footer},
}
</script>

<style scoped>

.Accueil {
  overflow: hidden;
  position: static;
  display: flex;
  width: 100%;
  justify-content: space-between;
  margin-left: 150px;
}


</style>
