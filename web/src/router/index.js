import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'index',
      component: () => import('@/views/index/index.vue'),
      children: [
        
        {
          path: '/wj/manage',
          name: 'wjmanage',
          component: () => import('@/views/wj/WjManage.vue'),
          children: [
            {
              path: '/wj/list',
              name: 'wjlist',
              component: () => import('@/views/wj/WjList.vue')
            },
            {
              path: '/wj/list/star',
              name: 'star',
              component: () => import('@/views/wj/WjList.vue')
            },
            {
              path: '/wj/recycle',
              name: 'recycle',
              component: () => import('@/views/wj/Recycle.vue')
            }
          ]
        }
        ,
        {
          path: '/wj/choosetype',
          name: 'choosetype',
          component: () => import('@/views/wj/ChooseType.vue')
          
        },
        {
          path: '/wj/blanknew',
          name: 'blanknew',
          component: () => import('@/views/wj/CreateBlankNew.vue')
          
        },
        {
          path: '/user/info',
          name: 'userinfo',
          component: () => import('@/views/user/UserInfo.vue')
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/Login.vue')
    },
    {
      path: '/reg',
      name: 'reg',
      component: () => import('@/views/register/Reg.vue')
    },
    {
      path: '/wj/design',
      name: 'design',
      component: () => import('@/views/wj/WjDesign.vue')
    },
    {
      path: '/exam',
      name: 'exam',
      component: () => import('@/views/wj/Exam.vue')
    },
    {
      path: '/exam/result',
      name: 'examresult',
      component: () => import('@/views/wj/ExamResult.vue')
    },
    {
      path: '/exam/ranking',
      name: 'ranking',
      component: () => import('@/views/wj/RankingList.vue')
    },
    {
      path: '/test',
      name: 'test',
      component: () => import('@/views/test.vue')
    }
  ]
})

export default router
