import VueRouter from 'vue-router'
import Vue from 'vue';
import Profils from "@/components/Profils";
import MainPanel from "@/components/MainPanel";
import Accueil from "@/pages/Accueil";

Vue.use(VueRouter)

const routes = [
    {path: '/', component: Profils, name: "Profils"},
    {
        path: '/u', component: MainPanel, props: true, children: [
            {path: '/', component: Accueil, name: "Accueil"},
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export default router
