import { defineStore } from 'pinia'
import {ref} from 'vue'

import userApi from '@/api/userApi';
import storage from '@/utils/storage';


export const useUserStore = defineStore('user', () => {
    const user = ref({})
    const token = ref("")

    function login(user) {
        return new Promise((resolve,reject) => {
            userApi.login(user).then(res => {
                token.value = res.data;
                storage.setStorageToken(res.data);
                resolve(res)
            });
        }).catch(err => {
            reject(err)
        })
    }

    function getUserInfo(token) {
        return new Promise((resolve,reject) => {
           userApi.getUserInfo(token).then(res => {
              user.value = res.data;
              resolve(res)
           });
        }).catch(err => {
            reject(err)
        })
      
    }

    function logout() {
        return new Promise((resolve,reject) => {
            userApi.logout().then(res => {
               user.value = {}
               token.value = ""
               storage.clearStorageToken()
               resolve(res)
            });
         }).catch(err => {
             reject(err)
         })
    }

    function refreshToken() {
        userApi.getNewToken().then(res => {
            user.value = res.data.user;
            token.value = res.data.token;
            storage.setStorageToken(res.data.token);
        })
    }

  
    return { user, token, login, getUserInfo, logout,refreshToken }
  }, {
    persist: true // 持久化存储
  })