package com.qqcn.survey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qqcn.survey.entity.SurveyQuestion;
import com.qqcn.survey.entity.SurveyQuestionOption;

import java.util.List;

public interface SurveyQuestionOptionService extends IService<SurveyQuestionOption> {

    List<SurveyQuestionOption> getSurveyQuestionOptionList(Integer questionId);

    void addSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption);

    void deleteSurveyQuestionOption(Integer id);

    void deleteBySurveyId(Integer surveyId);
}
