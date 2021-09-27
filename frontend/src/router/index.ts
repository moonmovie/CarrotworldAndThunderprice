import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
function importComp(view: string) {
  return () => import(/* webpackChunkName: "view-[request]" */ `@/views/${view}.vue`)
}
const Prod = () => import(/* webpackChunkName: "prod" */ '@/views/Prod.vue');

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: importComp('Home'),
  },
  // {
  //   path: '/chart',
  //   name: 'Chart',
  //   component: Chart,
  // },
  {
    path: '/prod',
    name: 'Prod',
    component: Prod,
  },
  {
    path: '/sel',
    name: 'Select',
    component: importComp('Select'),
  },
  {
    path: '/test',
    name: 'Test',
    component: importComp('Test'),
  },
  {
    path: '/test2',
    name: 'Test2',
    component: importComp('Test2'),
  },
  {
    path: '/bridge',
    name: 'BridgeTest',
    component: importComp('BridgeTest'),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
