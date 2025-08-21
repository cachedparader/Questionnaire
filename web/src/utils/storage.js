
const tokenKey = "Authorization"
export default{
    setStorageToken(token){
        localStorage.setItem(tokenKey, "Bearer " + token)
    },
    getStorageToken(token){
        return localStorage.getItem(tokenKey)
    },
    clearStorageToken(){
        localStorage.removeItem(tokenKey)
    }
}