import VueRouter from 'vue-router'
import Vue from 'vue';
import Profils from "@/components/Profils";
import MainPanel from "@/components/MainPanel";
import Accueil from "@/pages/Accueil";
import PatrimoinePage from "@/components/Patrimoine/Patrimoine-Page";
import CentresSportifsPage from "@/components/Centres-Sportifs/Centres-Sportifs-Page";
import CommercePage from "@/components/Commerces/Commerce-Page";
import EvenementPage from "@/components/Evenement/Evenement-Page";
import GroupePage from "@/components/Groupe/Groupe-Page";

Vue.use(VueRouter)

const routes = [
    {path: '/', component: Profils, name: "Profils"},
    {
        path: '/u', component: MainPanel, props: true, children: [
            {path: '/', component: Accueil, name: "Accueil"},
            {path: '/patrimoine', component: PatrimoinePage, name: "Patrimoine"},
            {path: '/centres-sportifs', component: CentresSportifsPage, name: "Centres Sportifs"},
            {path: '/commerces', component: CommercePage, name: "Commerces"},
            {path: '/evenements', component: EvenementPage, name: "Evénements"},
            {path: '/groupes', component: GroupePage, name: "Groupes"},
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export default router
