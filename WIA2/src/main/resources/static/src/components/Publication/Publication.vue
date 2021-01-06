<template>
    <div class="card">
        <div class="card-content">
            <div class="content">
            <p class="title is-6">{{ publication.title }}</p>
            <p class="subtitle is-7">Par {{publication.authorName}}</p>
            <p>
                {{publication.description}}
            </p>
            <div class="field is-grouped">
                <p class="content">
                <a @click="sendClickedPublicationIndex()"><span>
                {{publication.comments.length -1}} commentaires
                </span></a>
                </p>
                <p class="control" style="margin-left: auto">
                <span style="margin-right: 5px">{{publication.likes.length}}</span>
                <button v-if="!alreadyLike" @click="like()" class="button is-small is-danger is-outlined">
                    <b-icon size="is-small" icon="heart"/>
                </button>
                <button v-else @click="unlike()" class="button is-small is-danger">
                    <b-icon size="is-small" icon="heart"/>
                </button>
                </p>
            </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Publication",
    props: {
        publication: Object
    },
    computed: {
        alreadyLike() {
            var response = false;
            this.publication.likes.forEach(element => {
                var id = element.split("/")
                if (id[id.length - 1] == this.$route.params.id) {
                response = true;
                return;
                }
            });
            return response;
        }
    },
    methods: {
        like() {
            this.$store.dispatch('likePublication', {
                id : this.publication.id,
                userId: this.$route.params.id
            })
        },
        unlike() {
            this.$store.dispatch('unlikePublication', {
                id : this.publication.id,
                userId: this.$route.params.id
            })
        },
        sendClickedPublicationIndex(){
            this.$emit("selected", this.publication);
        }
    }
}
</script>