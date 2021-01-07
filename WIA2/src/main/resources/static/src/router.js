import VueRouter from 'vue-router'
import Vue from 'vue';
import Profils from "@/components/Profils";
import MainPanel from "@/components/MainPanel";
import Accueil from "@/pages/Accueil";
import PatrimoinePage from "@/components/Patrimoine/Patrimoine-Page";
import CommercePage from "@/components/Commerces/Commerce-Page";
import RestaurantPage from "@/components/Restaurant/Restaurant-Page";
import EvenementPage from "@/components/Evenement/Evenement-Page";
import GroupePage from "@/components/Groupe/Groupe-Page";
import StationementTransport from "@/pages/StationementTransport";
import ContactPage from "@/components/Contacts/Contact-Page";
import RecommendedGroupePage from "@/components/Groupe/Recommended-Groupe-Page";
import MyGroupsPage from "@/components/Groupe/My-Groups-Page";
import PolyTrip from "@/pages/PolyTrip";

Vue.use(VueRouter)

const routes = [
    {path: '/', component: Profils, name: "Profils"},
    {
        path: '/polyville/:id', component: MainPanel, props: true, children: [
            {path: '', component: Accueil, name: "Accueil"},
            {path: 'patrimoine', component: PatrimoinePage, name: "Patrimoine"},
            {path: 'commerces', component: CommercePage, name: "Commerces"},
            {path: 'restaurants', component: RestaurantPage, name: "Restaurants"},
            {path: 'evenements', component: EvenementPage, name: "Evénements"},
            {path: 'recommended/groupes', component: RecommendedGroupePage, name: "Recommended Groupes"},
            {path: 'stationnement', component: StationementTransport, name: "Stationement"},
            {path: 'contacts', component: ContactPage, name:"Contact"},
            {path: 'mescontacts', component: ContactPage, name: "Mes contacts"},
            {path: 'groupes', component: GroupePage, name: "Groupes"},
            {path: 'mesgroupes', component: MyGroupsPage, name: "Mes Groupes"},
            {path: 'PolyTrip', component: PolyTrip, name: "PolyTrip"},
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export default router
