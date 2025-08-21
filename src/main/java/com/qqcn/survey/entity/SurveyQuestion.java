package com.qqcn.survey.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("wj_survey_question")
public class SurveyQuestion implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer surveyId;
    private Integer questionType;
    private String questionTypeDesc;
    private String content;
    private Integer score;
    private Integer orderNum;
    private String correctAnswer;
    private String questionEditorComponent;
    private String component;
    private java.util.Date fcd;
    private java.util.Date lud;

    @TableField(exist = false)
    public List<SurveyQuestionOption> answerOptions = new ArrayList<>();

    @TableField(exist = false)
    private String userAnswer;

    @TableField(exist = false)
    private Integer answerValid;

}