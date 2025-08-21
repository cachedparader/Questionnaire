package com.qqcn.survey.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qqcn.survey.entity.Survey;
import com.qqcn.survey.entity.SurveyUserAnswer;
import com.qqcn.survey.entity.SurveyUserScore;
import com.qqcn.survey.vo.SurveyQuery;

import java.util.HashMap;
import java.util.List;

public interface SurveyService extends IService<Survey> {
    void updateSurveyStatus(Integer surveyId, int status);

    Page<Survey> getSurveyList(SurveyQuery param);

    void updateSurveyStar(Integer surveyId, Integer star);

    Integer copySurvey(Integer sourceSurveyId);

    void deleteSurveyToRecycle(Integer surveyId);

    List<Survey> getRecycleList(Integer userId);

    void restoreSurvey(Integer surveyId);

    void deleteSurvey(Integer surveyId);

    Integer submitSurvey(List<SurveyUserAnswer> userAnswerList, Integer surveyId, Integer examDuration);

    SurveyUserScore getExamResult(Integer scoreId);

    HashMap<String, Object> getExamRanking(Integer surveyId);
}
