import request from "@/utils/request";

export default{
    getCaptcha(phone){
        return request({
            method: "get",
            url: `/sms/${phone}`
        });
    },
    checkCaptcha(phone,code){
        return request({
            method: "get",
            url: `/sms/check`,
            params: {
                phone,
                code
            }
        });
    }
}