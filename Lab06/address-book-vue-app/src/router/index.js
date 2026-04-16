import { createRouter, createWebHistory } from 'vue-router'
import DefaultLayout from '@/layouts/DefaultLayout.vue'

const routes = [
  {
    path: '/',
    component: () => import('@/views/HomeView.vue'),
    meta: { layout: DefaultLayout }
  },
  {
    path: '/books',
    component: () => import('@/views/BooksView.vue'),
    meta: { layout: DefaultLayout }
  },
  {
    path: '/authors',
    component: () => import('@/views/AuthorsView.vue'),
    meta: { layout: DefaultLayout }
  },
  {
    path: '/readers',
    component: () => import('@/views/ReadersView.vue'),
    meta: { layout: DefaultLayout }
  },
  {
    path: '/loans',
    component: () => import('@/views/LoansView.vue'),
    meta: { layout: DefaultLayout }
  },
]

export default createRouter({
  history: createWebHistory(),
  routes,
})
