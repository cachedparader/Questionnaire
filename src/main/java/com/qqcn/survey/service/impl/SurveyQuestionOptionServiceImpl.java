package com.qqcn.survey.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qqcn.survey.entity.SurveyQuestion;
import com.qqcn.survey.entity.SurveyQuestionOption;
import com.qqcn.survey.mapper.SurveyQuestionOptionMapper;
import com.qqcn.survey.service.SurveyQuestionOptionService;
import com.qqcn.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyQuestionOptionServiceImpl extends ServiceImpl<SurveyQuestionOptionMapper, SurveyQuestionOption> implements SurveyQuestionOptionService {

    @Autowired
    private SurveyService surveyService;

    @Override
    public List<SurveyQuestionOption> getSurveyQuestionOptionList(Integer questionId) {
        return baseMapper.getSurveyQuestionOptionList(questionId);
    }

    @Override
    public void addSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption) {
        // 1. 更新影响范围内的ordernum
        baseMapper.batchUpdateOptionOrderNum(surveyQuestionOption.getQuestionId(),surveyQuestionOption.getOrderNum(),1);
        // 2. 新增数据
        surveyQuestionOption.setOrderNum(surveyQuestionOption.getOrderNum()+1);
        baseMapper.insert(surveyQuestionOption);

        surveyService.updateSurveyStatus(surveyQuestionOption.getSurveyId(),0);
    }

    @Override
    public void deleteSurveyQuestionOption(Integer id) {
        SurveyQuestionOption option = baseMapper.selectById(id);
        // 1. 更新影响范围内的ordernum
        baseMapper.batchUpdateOptionOrderNum(option.getQuestionId(),option.getOrderNum(),-1);
        // 2. 删除数据
        baseMapper.deleteById(id);

        surveyService.updateSurveyStatus(option.getSurveyId(),0);
    }

    @Override
    public void deleteBySurveyId(Integer surveyId) {
        baseMapper.deleteBySurveyId(surveyId);
    }

}
