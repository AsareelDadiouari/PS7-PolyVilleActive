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
                console.log(response.data)
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
                console.log(response.data)
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
                console.log(response2.data)
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
                console.log(response.data)
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
                console.log(response.data)
                context.commit('setGroups', response.data)
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
        groups: Groups
    }
})

export default store

