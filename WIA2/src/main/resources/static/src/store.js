import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const Users = {
    state: {
        users: []
    },
    mutations: {
        setUsers (state, payload) {
            state.users = payload
        }
    },
    getters: {
        getUsers(state) {
           return state.users
        },
        getUserById(state, id){
            return state.users + id;
        }
    },
    actions: {
        async setUsers(context) {
            try {
                const response = await Vue.axios.get('http://localhost:8090/users')
                context.commit('setUsers', response.data)
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
        evenements: []
    },
    mutations: {
        setEvenement(state, payload){
            state.evenements = payload
        }
    },
    getters: {
        getEvenements(state){
            return state.evenements
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
        }
    }
}

const Groups = {
    state: {
        groups: []
    },
    mutations: {
        setGroups(state, payload){
            state.groups = payload
        }
    },
    getters: {
        getGroups(state){
            return state.groups
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
        async setEvenementsRecommandations(context){
            try {
                const currentUser = "1";
                const url = "http://localhost:8090/eventsRecommandations?userId=" + currentUser
                const response = await Vue.axios.get(url)
                context.commit('setEvenementsRecommandations', response.data)
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
    }
})

export default store

