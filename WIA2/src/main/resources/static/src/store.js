import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const Users = {
    state: {
        users: [],
        user: Object
    },
    mutations: {
        setUsers (state, payload) {
            state.users = payload
        },
        setUser(state, payload) {
            state.user = payload
        }
    },
    getters: {
        getUsers(state) {
           return state.users
        },
        getUser(state) {
            return state.user
        },
        getUserById(state, id){
            return state.users + id;
        }
    },
    actions: {
        async setUsers(context) {
            try {
                const response = await Vue.axios.get('http://localhost:8090/users')
                console.log(response.data)
                context.commit('setUsers', response.data)
            } catch (err) {
                console.log(err)
            }
        },
        async setUser(context, payload) {
            try {
                const response = await Vue.axios.get('http://localhost:8090/user?userId=' + payload.userId)
                context.commit('setUser', response.data)
            } catch (err) {
                console.log(err)
            }
        }

    }
}

const Stores = {
    state: {
        stores: []

    },
    mutations: {
        setStores (state, payload) {
            state.stores = payload
        }
    },
    getters: {
        getStores(state) {
           return state.stores
        }
    },
    actions: {
        async setStores(context) {
            try {
                const response = await Vue.axios.get('http://localhost:8090/stores')
                context.commit('setStores', response.data)
            } catch (err) {
                console.log(err)
            }
        }

    }
}

const Restaurants = {
    state: {
        restaurants: []

    },
    mutations: {
        setRestaurants (state, payload) {
            state.restaurants = payload
        }
    },
    getters: {
        getRestaurants(state) {
            return state.restaurants
        }
    },
    actions: {
        async setRestaurants(context) {
            try {
                const response2 = await Vue.axios.get('http://localhost:8090/restaurants')
                context.commit('setRestaurants', response2.data)
            } catch (err) {
                console.log(err)
            }
        }

    }
}

const Evenements = {
    state: {
        evenements: [],
        participe: false
    },
    mutations: {
        setEvenement(state, payload){
            state.evenements = payload
        },
        setParticipe(state, payload){
            console.log(payload)
            state.participe = payload
        }
    },
    getters: {
        getEvenements(state){
            return state.evenements
        },
        getParticipe(state){
            return state.participe
        }
    },
    actions: {
        async setEvenement(context){
            try {
                const response = await Vue.axios.get('http://localhost:8090/events')
                context.commit('setEvenement', response.data)
            } catch (err) {
                console.log(err)
            }
        },

        async joinEvenement(context, payload){
            try {
                const response = await Vue.axios.post('http://localhost:8090/events/' + payload.eventId, payload.userId)
                context.commit('setParticipe', response.data)
            } catch (e){
                context.commit('setParticipe',e)
            }
        },
        async checkParticipation(context, payload){
            try {
                const response = await Vue.axios.get('http://localhost:8090/events/' + payload.eventId + '/user/' + payload.userId)
                context.commit('setParticipe', response.data)
            } catch (e){
                context.commit('setParticipe', e)
            }
        }
    }
}

const Groups = {
    state: {
        groups: [],
        recommendedGroups: []
    },
    mutations: {
        setGroups(state, payload) {
            state.groups = payload
        },
        setRecommendedGroups(state, payload) {
            state.recommendedGroups = payload
        }
    },
    getters: {
        getGroups(state){
            return state.groups
        },
        getRecommendedGroups(state){
            return state.recommendedGroups
        }
    },
    actions: {
        async setGroups(context){
            try {
                const response = await Vue.axios.get('http://localhost:8090/groups')
                context.commit('setGroups', response.data)
            } catch (err) {
                console.log(err)
            }
        },
        async setRecommendedGroups(context, payload){
            try {
                const response = await Vue.axios.get('http://localhost:8090/recommendedGroups?userId=' + payload.userId)
                console.log(response.data)
                context.commit('setRecommendedGroups', response.data)
            } catch (err) {
                console.log(err)
            }
        },
        async addMember(context, payload) {
            try {
                await Vue.axios.post('http://localhost:8090/groups?groupId=' + payload.groupId + '&userId=' + payload.userId)
                const response = await Vue.axios.get('http://localhost:8090/groups')
                context.commit('setGroups', response.data)

            } catch (err) {
                console.log(err)
            }
        }
    }
}

const EvenementsRecommandations = {
    state: {
        evenementsRecommandations: []
    },
    mutations: {
        setEvenementsRecommandations(state, payload){
            state.evenementsRecommandations = payload
        }
    },
    getters: {
        getEvenementsRecommandations(state){
            return state.evenementsRecommandations
        }
    },
    actions: {
        async setEvenementsRecommandations(context, payload){
            try {
                const currentUser = payload.userId;
                const url = "http://localhost:8090/eventsRecommandations?userId=" + currentUser
                const response = await Vue.axios.get(url)
                context.commit('setEvenementsRecommandations', response.data)
            } catch (err) {
                console.log(err)
            }
        }
    }
}

const Publications = {
    state: {
        publications: []
    },
    mutations: {
        setPublications(state, payload){
            state.publications = payload
        }
    },
    getters: {
        getPublications(state){
            return state.publications
        }
    },
    actions: {
        async setPublications(context){
            try {
                const url = "http://localhost:8090/publications"
                const response = await Vue.axios.get(url)
                context.commit('setPublications', response.data)
            } catch (err) {
                console.log(err)
            }
        }
    }
}

const store = new Vuex.Store({
    modules: {
        users: Users,
        stores: Stores,
        evenements: Evenements,
        restaurants: Restaurants,
        groups: Groups,
        evenementsRecommandations: EvenementsRecommandations,
        publication: Publications,
    }
})

export default store

