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
        setGroups(state, payload) {
            state.groups = payload
        },
        addMember(state, payload) {
            state.groups.forEach(element => {
                if (element.id == payload.groupId) {
                    var alreadyAdd = false;
                    element.members.forEach(member => {
                        if (member.id == payload.user.id) {
                            alreadyAdd = true;
                        }
                    })
                    if (!alreadyAdd)
                        element.members.push(payload.user)
                }
            });
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
        },
        async addMember(context, payload) {
            try {
                const response = await Vue.axios.get('http://localhost:8090/user?userId=' + payload.userId)
                console.log(response.data)
                context.commit('addMember', {groupId: payload.groupId, user: response.data})
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

