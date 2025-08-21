import request from "@/utils/request";

export default{
    getParamListByBaseName(baseName){
        return request({
            method: "get",
            url: `/base`,
            params: {
                baseName
            }
        });
    }
}