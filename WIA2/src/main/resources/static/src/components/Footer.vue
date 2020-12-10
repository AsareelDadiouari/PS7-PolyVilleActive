<template>
  <div class="foot">
    <footer class="footer">
      <div class="container">
        <div class="columns">
          <div class="column is-3 is-offset-2">
            <h2><strong>Tourisme</strong></h2>
            <ul>
              <li>
              <a href="/patrimoine">Patrimoine</a></li>
              <li>
              <a href="/centres-sportifs">Centres sportifs</a></li>
              <li>
              <a href="/commerces">Commerces</a></li>
              <li><a href="#">Présentation et accès</a></li>
            </ul>
          </div>
          <div class="column is-3">
            <h2><strong>Services</strong></h2>
            <ul>
              <li>
              <a href="/evenements">Événements</a></li>
              <li><a href="#">Stationement et transport en commun</a></li>
            </ul>
          </div>
          <div class="column is-4">
            <h2><strong>Newsletter</strong></h2>
            <ul>
              <li style="color: black">Pour vous inscrire à la newsletter, entrez votre email.</li>
              <div class="mail-part">
                <b-input type="email" maxlength="30" placeholder="Email" icon="email"/>
                <b-button>Ok</b-button>
              </div>
            </ul>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "Footer",
}
</script>

<style scoped>
.foot {
  background-color: #2c2c4a;
  color: white;
}

.mail-part {
  display: flex;
}

.component {
  color: white;
}

</style>
