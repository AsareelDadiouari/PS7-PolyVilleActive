<template>
  <div>
    <div class="card">
      <header class="card-header">
        <p class="card-header-title">
          {{publication.title}} <br>
        </p>
        <a href="#" class="card-header-icon" aria-label="more options">
      <span class="icon">
        <i class="fas fa-angle-down" aria-hidden="true"></i>
      </span>
        </a>
      </header>
      <div class="card-content">
        <div class="content">
          {{publication.description}}

          <div class="column" style="">
            <strong>Commentaires {{publication.comments.length-1}}</strong>

            <span style="margin-left: 300px">
              <b-icon size="is-small" icon="heart"/>
              {{publication.like}}
            </span>
          </div>

          <div id="coms" v-for="val in publication.comments" :key="val">
            <p v-if="val !== ''" style="border-radius: 10px; border-color: #c3c3c3; border-style: solid; border-width: 5px; margin-top: 5px">
              {{val}}
            </p>
          </div>

          <b-field  label="Commenter"
                    >
            <b-input id="comment" maxlength="200" type="textarea"></b-input>
          </b-field>

          <b-button @click="comment()" style="justify-content: flex-end" type="is-info">Commenter</b-button>
        </div>
      </div>
      <!--<footer class="card-footer">
        <a href="#" class="card-footer-item">Save</a>
        <a href="#" class="card-footer-item">Edit</a>
        <a href="#" class="card-footer-item">Delete</a>
      </footer>-->
    </div>
  </div>
</template>

<script>
export default {
  name: "Publication-details",
  props: {
    publication: Object
  },
  created() {
    console.log(this.publication.id)
  },
  methods: {
    comment(){
      this.$store.dispatch('commenterPublication', {id : this.publication.id, comment: document.getElementById('comment').value})

      const node = document.createElement("p")
      const textNode = document.createTextNode(document.getElementById('comment').value)

      node.style.borderRadius = '10px';
      node.style.borderColor = '#c3c3c3';
      node.style.borderStyle = 'solid';
      node.style.borderWidth = '5px';
      node.style.marginTop = '5px';
      node.append(textNode)

      document.getElementById('coms').appendChild(node)
      document.getElementById('comment').value = ""
    }
  }
}
</script>

<style scoped>

</style>