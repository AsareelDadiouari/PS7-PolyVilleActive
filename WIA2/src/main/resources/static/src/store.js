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

const Magasins = {
    state: {
        magasins: []

    },
    mutations: {

    },
    getters: {

    },
    actions: {

    }
}

const Restaurants = {
    state: {
        restautants: []
    },
    mutations: {

    },
    getters: {

    },
    actions: {

    }
}

const Evenements = {
    state: {
        evenements: []
    },
    mutations: {

    },
    getters: {

    },
    actions: {

    }
}

const store = new Vuex.Store({
    modules: {
        users: Users,
        magasins: Magasins,
        evenements: Evenements,
        restaurants: Restaurants
    }
})

export default store

