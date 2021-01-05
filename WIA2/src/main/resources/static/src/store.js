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
        recommendedGroups: [],
        mygroups: []
    },
    mutations: {
        setGroups(state, payload) {
            state.groups = payload
        },
        setRecommendedGroups(state, payload) {
            state.recommendedGroups = payload
        },
        setMyGroups(state, payload) {
            state.mygroups = payload
        },
    },
    getters: {
        getGroups(state){
            return state.groups
        },
        getRecommendedGroups(state){
            return state.recommendedGroups
        },
        getMyGroups(state){
            return state.mygroups
        }
    },
    actions: {
        async setGroups(context, payload){
            try {
                const response = await Vue.axios.get('http://localhost:8090/groups?userId=' + payload.userId)
                console.log(response)
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
        async setMyGroups(context, payload){
            try {
                const response = await Vue.axios.get('http://localhost:8090/mygroups?userId=' + payload.userId)
                console.log(response.data)
                context.commit('setMyGroups', response.data)
            } catch (err) {
                console.log(err)
            }
        },
        async addMember(context, payload) {
            try {
                await Vue.axios.post('http://localhost:8090/addmember?groupId=' + payload.groupId + '&userId=' + payload.userId)
                const path = "set" + payload.to;
                context.dispatch(path, payload)
                context.dispatch(payload.request, payload)
            } catch (err) {
                console.log(err)
            }
        },
        async removeMember(context, payload) {
            try {
                await Vue.axios.post('http://localhost:8090/removemember?groupId=' + payload.groupId + '&userId=' + payload.userId)
                context.dispatch("setMyGroups", payload)
                context.dispatch(payload.request, payload)
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
                console.log(response.data)
                context.commit('setPublications', response.data)
            } catch (err) {
                console.log(err)
            }
        },
        async likePublication(context, payload){
            try {
                await Vue.axios.post("http://localhost:8090/publications/" + payload.id + "/like/" + payload.userId)
                context.dispatch("setPublications")
            } catch (err){
                console.log(err)
            }
        },
        async unlikePublication(context, payload){
            try {
                await Vue.axios.post("http://localhost:8090/publications/" + payload.id + "/unlike/" + payload.userId)
                context.dispatch("setPublications")
            } catch (err){
                console.log(err)
            }
        },
        async commenterPublication(context, payload){
            try {
                await Vue.axios.post("http://localhost:8090/publications/" + payload.id + "/comment", payload.comment)
                context.dispatch("setPublications")
            }catch (err) {
                console.log(err)
            }
        }
    }
}

const Bus = {
    state: {
        bus: []
    },
    mutations: {
        setBus(state, payload){
            state.bus = payload
        }
    },
    getters: {
        getBus(state){
            return state.bus
        }
    },
    actions: {
        async setBus(context){
            try {
                const url = "http://localhost:8090/bus"
                const response = await Vue.axios.get(url)
                context.commit('setBus', response.data)
            } catch (err) {
                console.log(err)
            }
        }
    }
}

const BusRecommandations = {
    state: {
        busRecommandations: []
    },
    mutations: {
        setBusRecommandations(state, payload){
            state.busRecommandations = payload
        }
    },
    getters: {
        getBusRecommandations(state){
            return state.busRecommandations
        }
    },
    actions: {
        async setBusRecommandations(context, payload){
            try {
                const latitude = payload.evenement.latitude;
                const longitude = payload.evenement.longitude;
                const url = "http://localhost:8090/busRecommandations?latitude=" + latitude + "&longitude=" + longitude
                const response = await Vue.axios.get(url)
                context.commit('setBusRecommandations', response.data)
            } catch (err) {
                console.log(err)
            }
        }
    }
}

const Patrimoines = {
    state: {
        patrimoines: []

    },
    mutations: {
        setPatrimoines (state, payload) {
            state.patrimoines = payload
        }
    },
    getters: {
        getPatrimoines(state) {
            return state.patrimoines
        }
    },
    actions: {
        async setPatrimoines(context) {
            try {
                const response = await Vue.axios.get('http://localhost:8090/patrimoines')
                context.commit('setPatrimoines', response.data)
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
        bus: Bus,
        busRecommandations: BusRecommandations,
        restaurants: Restaurants,
        groups: Groups,
        evenementsRecommandations: EvenementsRecommandations,
        publication: Publications,
        patrimoines: Patrimoines,
    }
})

export default store

