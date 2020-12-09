<template>
 <div class="card">
      <div class="card-content">
        <div class="media">
          <div class="media-content">
              <a class="image">
                <figure class="image is-2by1">
                    <img src="https://bulma.io/images/placeholders/96x96.png" alt="Placeholder image">
                </figure>
                </a>
            <div class="title is-4">Nom du groupe</div>
            <div class="subtitle is-7">125K membres - 20 publications par jour</div> <!--{{groupe.membres}} - {{groupe.publications}}-->
            <br>
            <div class="substile is-6" maxlength="240">Description du groupe. Exemple : Toutes vos fêtes de Nice !
                (Bal, Fêtes, Concert, Feu d'artifice, Soirée Discothèque)
                Pour publier vos fêtes ou vos événements, envoyez nous votre programmation avec flyer à notre addresse mail !</div>
            </div>
    <span class="vertical-line"></span>
            <button class="button is-primary">Rejoindre</button>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: "Groupe-CardView",
  props: {
    groupe: Object
  }
}
</script>

<style scoped>
.image {
    height: 200px;
    width: 400px;
    float: left;
    margin-right: 12px;
}

.button {
    margin: auto;
    float: right;
}

.card {
    width: 95%;
    height: 250px;
    border-width: 1.5px;
    border-style: groove;
    margin-left: auto;
    margin-right: auto;
    vertical-align: middle;
    overflow: hidden;
}

.vertical-line{
        border-left: 2px solid #000;
        display: inline-block;
        height: 200px;
        margin: 0 20px;
      }
</style>