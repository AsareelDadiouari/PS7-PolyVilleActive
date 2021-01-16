<template>
  <div class="contact">
      <div class="contactimg">
      <figure class="image is-64x64">
          <img src="https://img.icons8.com/ios/50/000000/user.png"/>
        </figure>
        </div>
    <div class="contact-desc">
      <div class="title is-5">{{contact.firstname}} {{contact.lastname}}</div>
      <div class="subtitle is-6">{{contact.role}}</div>
    </div>
      <figure class="image is-32x32">
          <div class="addimg">
          <a>
    <img src="https://img.icons8.com/ios/50/000000/add-user-male.png"/>
    </a>
    </div>
      </figure>
  </div>
</template>

<script>
export default {
  name: "Contact-SimpleView",
  props: {
    contact: Object
  }
}
</script>

<style scoped>
.contact {
    width: 240px;
    height: auto;
  display: flex;
  border-bottom: groove;
  padding: 1px 1px 1px 1px;
}

.contactimg {
    padding-right: 5px;
}

.contact-desc {
    padding-right: 10px;
    margin-top: 5px;
}

.addimg {
    margin-top: 25px;
}

</style>
