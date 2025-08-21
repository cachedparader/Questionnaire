package com.qqcn.survey.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qqcn.survey.entity.Survey;
import com.qqcn.survey.entity.SurveyQuestion;
import com.qqcn.survey.entity.SurveyQuestionOption;
import com.qqcn.survey.mapper.SurveyMapper;
import com.qqcn.survey.mapper.SurveyQuestionMapper;
import com.qqcn.survey.mapper.SurveyQuestionOptionMapper;
import com.qqcn.survey.service.SurveyQuestionOptionService;
import com.qqcn.survey.service.SurveyQuestionService;
import com.qqcn.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyQuestionServiceImpl extends ServiceImpl<SurveyQuestionMapper, SurveyQuestion> implements SurveyQuestionService {

    @Autowired
    private SurveyService surveyService;

    @Override
    public List<SurveyQuestion> getExamineeList(Integer surveyId) {
        return baseMapper.getExamineeList(surveyId);
    }

    @Transactional
    @Override
    public void updateSurveyQuestionOrder(Integer surveyId, Integer questionId, Integer questionType,Integer oldOrder, Integer newOrder) {
        int start = newOrder < oldOrder ? newOrder : oldOrder;
        int end = newOrder > oldOrder ? newOrder : oldOrder;
        int increment = newOrder < oldOrder ? 1 : -1;
        // 操作影响范围内的数据做增量操作
        baseMapper.questionOrderBatchIncr(surveyId,questionId,questionType,start, end,increment);
        // 更新当前记录的排序
        baseMapper.updateQuestionOrder(questionId,newOrder);
        surveyService.updateSurveyStatus(surveyId,0);
    }

    @Autowired
    private SurveyQuestionOptionService surveyQuestionOptionService;

    @Transactional
    @Override
    public void addSurveyQuestion(SurveyQuestion surveyQuestion) {
        // 新增到问题表
        baseMapper.insert(surveyQuestion);
        // 对于单选和多选，新增默认4个选项
        if(surveyQuestion.getQuestionType() == 1 || surveyQuestion.getQuestionType() == 2){
            for (int i = 0; i < 4; i++) {
                SurveyQuestionOption option = new SurveyQuestionOption();
                option.setSurveyId(surveyQuestion.getSurveyId());
                option.setQuestionId(surveyQuestion.getId());
                option.setOrderNum(i);
                surveyQuestionOptionService.save(option);
                surveyQuestion.getAnswerOptions().add(option);
            }
        }
        surveyService.updateSurveyStatus(surveyQuestion.getSurveyId(),0);
    }

    @Override
    public List<SurveyQuestion> getQuestionList(Integer surveyId) {
        List<SurveyQuestion> list = baseMapper.getQuestionList(surveyId);

        return list.stream().map(item -> {
            List<SurveyQuestionOption> optionList = surveyQuestionOptionService.getSurveyQuestionOptionList(item.getId());
            item.setAnswerOptions(optionList);
            return item;
        }).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Integer copyQuestion(Integer srcQuestionId, Integer surveyId) {
        // 1. 查询源数据
        SurveyQuestion question = baseMapper.selectById(srcQuestionId);
        List<String> srcCorrectList = Arrays.asList(question.getCorrectAnswer().split(","));
        List<String> newCorrectList = new ArrayList<>();
        // 2. 插入问题表
        Integer newOrderNum = question.getOrderNum()+1;
        question.setContent("【副本】"+question.getContent());
        question.setId(null);
        question.setFcd(null);
        question.setLud(null);
        question.setCorrectAnswer(null);
        question.setOrderNum(newOrderNum);
        if(surveyId != null ){
            question.setSurveyId(surveyId);
        }
        baseMapper.insert(question);

        // 操作影响范围内的数据做增量操作
        baseMapper.questionOrderBatchIncr(question.getSurveyId(),question.getId(),question.getQuestionType(),newOrderNum, 99,1);
        // 更新当前记录的排序
        baseMapper.updateQuestionOrder(question.getId(),newOrderNum);



        // 3. 插入选项表
        List<SurveyQuestionOption> optionList = surveyQuestionOptionService.getSurveyQuestionOptionList(srcQuestionId);
        if(optionList != null){
            optionList.forEach(option -> {
                Integer srcOptionId = option.getId();
                option.setId(null);
                option.setFcd(null);
                option.setLud(null);
                option.setQuestionId(question.getId());
                if(surveyId != null ){
                    option.setSurveyId(surveyId);
                }
                surveyQuestionOptionService.save(option);

                if(srcCorrectList.contains(srcOptionId+"")){
                    newCorrectList.add(option.getId()+"");
                }
            });
        }
        // 4. 更新正确答案
        question.setCorrectAnswer(String.join(",",newCorrectList));
        baseMapper.updateById(question);

        return question.getId();
    }

    @Autowired
    private SurveyQuestionOptionMapper surveyQuestionOptionMapper;

    @Transactional
    @Override
    public void deleteSurveyQuestionById(Integer id) {
        SurveyQuestion question = baseMapper.selectById(id);
        // 删除问题表
        baseMapper.deleteById(id);
        // 删除选项表
        surveyQuestionOptionMapper.deleteOptionByQuestionId(id);
        surveyService.updateSurveyStatus(question.getSurveyId(),0);
    }

    @Override
    public void deleteBySurveyId(Integer surveyId) {
        baseMapper.deleteBySurveyId(surveyId);
    }

    @Override
    public List<SurveyQuestion> getExamineeListForEaxm(Integer scoreId) {
        return baseMapper.getExamineeListForEaxm(scoreId);
    }

    @Override
    public List<SurveyQuestion> getQuestionListForEaxm(Integer scoreId) {
        List<SurveyQuestion> list = baseMapper.getQuestionListForEaxm(scoreId);

        return list.stream().map(item -> {
            List<SurveyQuestionOption> optionList = surveyQuestionOptionService.getSurveyQuestionOptionList(item.getId());
            item.setAnswerOptions(optionList);
            return item;
        }).collect(Collectors.toList());
    }


}
