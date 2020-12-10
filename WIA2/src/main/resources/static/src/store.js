import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const Utilisateurs = {
    state: {
        utilisateurs: []
    },
    mutations: {
        populateUsers (state, payload){
            state.utilisateurs = payload
        }
    },
    getters: {
        getUsers(state) {
           return state.utilisateurs
        },
        getUserById(state, id){
            return state.utilisateurs + id;
        }

    },
    actions: {
        async setUsers(context) {
            try {
                const response = await Vue.axios.get('')
                context.commit('populateUsers', response.data)
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
        utilisateurs: Utilisateurs,
        magasins: Magasins,
        evenements: Evenements,
        restaurants: Restaurants
    }
})

export default store

