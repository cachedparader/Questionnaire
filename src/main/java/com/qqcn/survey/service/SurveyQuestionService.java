package com.qqcn.survey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qqcn.survey.entity.Survey;
import com.qqcn.survey.entity.SurveyQuestion;

import java.util.List;

public interface SurveyQuestionService extends IService<SurveyQuestion> {

    public List<SurveyQuestion> getExamineeList(Integer surveyId);

    void updateSurveyQuestionOrder(Integer surveyId, Integer questionId, Integer questionType, Integer oldOrder, Integer newOrder);

    void addSurveyQuestion(SurveyQuestion surveyQuestion);

    List<SurveyQuestion> getQuestionList(Integer surveyId);

    Integer copyQuestion(Integer srcQuestionId, Integer surveyId);

    void deleteSurveyQuestionById(Integer id);

    void deleteBySurveyId(Integer surveyId);


    List<SurveyQuestion> getExamineeListForEaxm(Integer scoreId);

    List<SurveyQuestion> getQuestionListForEaxm(Integer scoreId);
}
