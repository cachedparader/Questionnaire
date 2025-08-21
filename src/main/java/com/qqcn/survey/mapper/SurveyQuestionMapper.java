package com.qqcn.survey.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qqcn.survey.entity.Survey;
import com.qqcn.survey.entity.SurveyQuestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SurveyQuestionMapper extends BaseMapper<SurveyQuestion> {

    @Select("select * from wj_survey_question where survey_id=#{surveyId} and question_type=0 order by order_num")
    public List<SurveyQuestion> getExamineeList(Integer surveyId);


    public void questionOrderBatchIncr(@Param("surveyId") Integer surveyId,
                                       @Param("questionId") Integer questionId,
                                       @Param("questionType") Integer questionType,
                                       @Param("start") Integer start,
                                       @Param("end") Integer end,
                                       @Param("increment") Integer increment);

    @Update("update wj_survey_question set order_num = #{newOrder} where id = #{questionId}")
    public void updateQuestionOrder(Integer questionId, Integer newOrder);

    @Select("select * from wj_survey_question where survey_id=#{surveyId} and question_type>0 order by order_num")
    List<SurveyQuestion> getQuestionList(Integer surveyId);

    @Delete("delete from wj_survey_question where survey_id=#{surveyId} ")
    void deleteBySurveyId(Integer surveyId);


    List<SurveyQuestion> getExamineeListForEaxm(Integer scoreId);

    List<SurveyQuestion> getQuestionListForEaxm(Integer scoreId);
}
