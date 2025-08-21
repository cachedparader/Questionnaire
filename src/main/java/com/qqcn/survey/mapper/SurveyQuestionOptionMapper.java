package com.qqcn.survey.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qqcn.survey.entity.SurveyQuestion;
import com.qqcn.survey.entity.SurveyQuestionOption;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SurveyQuestionOptionMapper extends BaseMapper<SurveyQuestionOption> {

    @Select("select * from wj_survey_question_option where question_id = #{questionId} order by order_num,id")
    List<SurveyQuestionOption> getSurveyQuestionOptionList(Integer questionId);

    @Update("update wj_survey_question_option set order_num = order_num + #{increment} where question_id = #{questionId} and order_num > #{orderNum}")
    void batchUpdateOptionOrderNum(Integer questionId, Integer orderNum, int increment);

    @Delete("delete from wj_survey_question_option where question_id = #{questionId}")
    void deleteOptionByQuestionId(Integer questionId);

    @Delete("delete from wj_survey_question_option where survey_id = #{surveyId}")
    void deleteBySurveyId(Integer surveyId);
}
