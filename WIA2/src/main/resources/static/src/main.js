import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'
import router from "@/router";
import VueRouter from 'vue-router'
import store from "@/store";
import axios from 'axios'
import VueAxios from 'vue-axios'
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import 'leaflet/dist/leaflet.css';
import { Icon } from "leaflet";
delete Icon.Default.prototype._getIconUrl;

Icon.Default.mergeOptions({
    iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
    iconUrl: require("leaflet/dist/images/marker-icon.png"),
    shadowUrl: require("leaflet/dist/images/marker-shadow.png")
});

Vue.use(Buefy)
Vue.config.productionTip = false
Vue.use(router)
Vue.use(Vuex)
Vue.use(VueRouter)
Vue.use(VueAxios, axios)


new Vue({
    store,
    router,
    render: h => h(App),
}).$mount('#app')
