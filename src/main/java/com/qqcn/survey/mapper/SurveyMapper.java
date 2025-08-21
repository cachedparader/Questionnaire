package com.qqcn.survey.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qqcn.survey.entity.Survey;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SurveyMapper extends BaseMapper<Survey> {
    @Update("update wj_survey set status=#{status} where id = #{surveyId} "  )
    void updateSurveyStatus(Integer surveyId, int status);

    @Update("update wj_survey set star = #{star} where id = #{surveyId}")
    void updateSurveyStar(Integer surveyId, Integer star);

    @Update("update wj_survey set deleted = 1 where id = #{surveyId}")
    void deleteSurveyToRecycle(Integer surveyId);

    @Select("select * from wj_survey where deleted = 1 and user_id = #{userId}")
    List<Survey> getRecycleList(Integer userId);

    @Update("update wj_survey set deleted = 0 where id = #{surveyId}")
    void restoreSurvey(Integer surveyId);


    @Update("update wj_survey set answer_total = (select count(1) from wj_survey_user_score where survey_id=#{surveyId} ) where id = #{surveyId}")
    void updateAnswerTotal(Integer surveyId);
}
