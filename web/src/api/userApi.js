import request from "@/utils/request";

export default{
    register(user){
        return request({
            method: "post",
            url: `/user/reg`,
            data: user
        });
    },
    login(user){
        return request({
            method: "post",
            url: `/user/login`,
            data: user
        });
    },
    getUserInfo(token){
        return request({
            method: "get",
            url: `/user/info`,
            params: {
                token
            }
        });
    },
    logout(){
        return request({
            method: "post",
            url: `/user/logout`
        });
    },
    updateNickname(userId,nickname){
        return request({
            method: "put",
            url: `/user/nickname`,
            params: {
                userId,
                nickname
            }
        });
    },
    getNewToken(){
        return request({
            method: "get",
            url: `/user/token`
        });
    },
    updatePassword(userId,password,newPassword){
        return request({
            method: "put",
            url: `/user/password`,
            data: {
                userId,password,newPassword
            }
        });
    },
    updateAvatar(userId,filename){
        return request({
            method: "put",
            url: `/user/avatar`,
            data: {
                userId,filename
            }
        });
    },
}