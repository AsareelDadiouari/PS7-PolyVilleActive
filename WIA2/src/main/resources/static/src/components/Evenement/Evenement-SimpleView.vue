<template>
  <div>
    <div class="evenement" @click="isComponentModalActive = true">
      <figure class="image is-64x64">
        <img :src="evenementRecommandations.image" alt="Image evenement"/>
      </figure>
      <div class="event-desc">
        <div class="title is-5">{{ evenementRecommandations.name }}</div>
        <time style="color: grey">{{evenementRecommandations.start}}-{{evenementRecommandations.end}}</time>
      </div>
    </div>
    <b-modal :width="550" class="myModal" v-model="isComponentModalActive" :has-modal-card="true"  scroll="keep">
      <EvenementModal :evenement="evenementRecommandations"/>
    </b-modal>

  </div>
</template>

<script>
import EvenementModal from "@/components/Evenement/Evenement-Modal";
export default {
  name: "Evenement-SimpleView",
  components: {EvenementModal},
  data() {
    return {
      isComponentModalActive: false
    }
  },
  props: {
    evenementRecommandations: Object
  }
}
</script>

<style scoped>
.evenement {
  display: flex;
  border-bottom: groove;
  padding: 1px 1px 1px 1px;
  width: 250px;
  transition: 0.3s;
}

.evenement:hover {
  background-color: #dbd9d9;
  cursor: pointer;
  color: white;
}


.event-desc {

}
</style>
