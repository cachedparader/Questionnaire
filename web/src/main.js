import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

import  "./permission";

import App from './App.vue'
import router from './router'

const app = createApp(App)


import { createPersistedState } from 'pinia-persistedstate-plugin'
const myPinia = createPinia()
const persist = createPersistedState()
myPinia.use(persist)

app.use(myPinia) 

//app.use(createPinia())
app.use(router)
app.use(ElementPlus, {
  locale: zhCn,
})

app.mount('#app')

import * as ElementPlusIconsVue from '@element-plus/icons-vue'

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}





// 全局批量注册自定义组件
console.log("加载自定义组件：");
import { defineAsyncComponent } from 'vue'
const wjComponentList = import.meta.glob('@/components/wj/*.vue')
for (const [key, component] of Object.entries(wjComponentList)) {
     let componentName = key.slice(key.lastIndexOf('/') + 1, key.lastIndexOf('.'))
     console.log("----> ",key);
     app.component(componentName, defineAsyncComponent(component))
}



