import router from './router'


const whitelist = ['/login','/reg',"/exam"]

import storage from '@/utils/storage'

router.beforeEach((to, from, next) => {
    let token = storage.getStorageToken()
    if(token || whitelist.indexOf(to.path) !== -1){
        console.log("-----> 路由守卫放行：" + to.path);
        next();
    }else{
        console.log("-----> 路由守卫拦截：" + to.path);
        next("/login")
    }
});