package com.qqcn.survey.vo;

import com.qqcn.common.vo.MyPage;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class SurveyQuery extends MyPage {
    private String title;
    private Integer status;
    private Integer star;
    private Integer userId;
    private String orderCondition;

    public boolean isAsc(){
        if(StringUtils.hasLength(orderCondition) && orderCondition.indexOf("desc") > -1){
            return false;
        }
        return true;
    }

    public String getOrderColumn(){
        if(StringUtils.hasLength(orderCondition)){
            String orderColumn = orderCondition.replaceAll("desc","").trim();
            orderColumn = orderColumn.replaceAll("asc","").trim();
            return orderColumn;
        }
        return "";
    }
}
