package com.qqcn.survey.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("wj_survey")
public class Survey implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private Integer status;
    private Integer answerTotal;
    private Integer star;
    private Integer deleted;
    private Integer userId;
    private Integer timeLimit;
    private java.util.Date fcd;
    private java.util.Date lud;

    @TableField(exist = false)
    private List<SurveyQuestion> examineeList;
    @TableField(exist = false)
    private List<SurveyQuestion> questionList;


    /*@TableField(exist = false)
    private String statusDesc;
    public String getStatusDesc(){
        if(status == 0){
            return "编辑中";
        }else if(status == 1){
            return "已就绪";
        }else if(status == 2){
            return "已发布";
        }else{
            return "未知";
        }
    }*/
}