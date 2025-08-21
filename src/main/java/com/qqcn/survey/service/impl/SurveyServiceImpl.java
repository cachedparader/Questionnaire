package com.qqcn.survey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qqcn.survey.entity.SurveyQuestion;
import com.qqcn.survey.entity.SurveyUserAnswer;
import com.qqcn.survey.entity.SurveyUserScore;
import com.qqcn.survey.mapper.SurveyMapper;
import com.qqcn.survey.entity.Survey;
import com.qqcn.survey.mapper.SurveyUserAnswerMapper;
import com.qqcn.survey.mapper.SurveyUserScoreMapper;
import com.qqcn.survey.service.SurveyQuestionOptionService;
import com.qqcn.survey.service.SurveyQuestionService;
import com.qqcn.survey.service.SurveyService;
import com.qqcn.survey.vo.SurveyQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Service
public class SurveyServiceImpl extends ServiceImpl<SurveyMapper, Survey> implements SurveyService {
    @Override
    public void updateSurveyStatus(Integer surveyId, int status) {
        baseMapper.updateSurveyStatus(surveyId,status);
    }

    @Override
    public Page<Survey> getSurveyList(SurveyQuery param) {
        Page<Survey> page = new Page<>(param.getPageNo(), param.getPageSize());

        QueryWrapper<Survey> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        wrapper.lambda().eq(Survey::getUserId,param.getUserId());
        wrapper.lambda().like(StringUtils.hasLength(param.getTitle()),Survey::getTitle, param.getTitle());
        wrapper.lambda().eq(param.getStatus()!=null,Survey::getStatus,param.getStatus());
        wrapper.lambda().eq(param.getStar()!=null,Survey::getStar,param.getStar());

        wrapper.orderBy(StringUtils.hasLength(param.getOrderCondition()),param.isAsc(), param.getOrderColumn());

        baseMapper.selectPage(page,wrapper);

        return page;
    }

    @Override
    public void updateSurveyStar(Integer surveyId, Integer star) {
        baseMapper.updateSurveyStar(surveyId,star);
    }

    @Autowired
    @Lazy
    private SurveyQuestionService surveyQuestionService;

    @Transactional
    @Override
    public Integer copySurvey(Integer sourceSurveyId) {
        // 问卷主表
        Survey survey = baseMapper.selectById(sourceSurveyId);
        survey.setId(null);
        survey.setFcd(null);
        survey.setLud(null);
        survey.setStatus(0);
        survey.setTitle("【复制】" + survey.getTitle());
        baseMapper.insert(survey);

        // 问题表
        // 考生信息
        List<SurveyQuestion> examineeList = surveyQuestionService.getExamineeList(sourceSurveyId);
        examineeList.forEach( item -> {
            item.setId(null);
            item.setFcd(null);
            item.setLud(null);
            item.setSurveyId(survey.getId());
            surveyQuestionService.addSurveyQuestion(item);
        });
        // 试题信息 >> 选项表
        List<SurveyQuestion> questionList = surveyQuestionService.getQuestionList(sourceSurveyId);
        questionList.forEach(item -> {
            surveyQuestionService.copyQuestion(item.getId(), survey.getId());
        });

        return survey.getId();
    }

    @Override
    public void deleteSurveyToRecycle(Integer surveyId) {
        baseMapper.deleteSurveyToRecycle(surveyId);
    }

    @Override
    public List<Survey> getRecycleList(Integer userId) {
        return baseMapper.getRecycleList(userId);
    }

    @Override
    public void restoreSurvey(Integer surveyId) {
        baseMapper.restoreSurvey(surveyId);
    }


    @Lazy
    @Autowired
    private SurveyQuestionOptionService surveyQuestionOptionService;

    @Transactional
    @Override
    public void deleteSurvey(Integer surveyId) {
        baseMapper.deleteById(surveyId);
        surveyQuestionService.deleteBySurveyId(surveyId);
        surveyQuestionOptionService.deleteBySurveyId(surveyId);
    }


    @Autowired
    private SurveyUserScoreMapper surveyUserScoreMapper;
    @Autowired
    private SurveyUserAnswerMapper surveyUserAnswerMapper;

    @Transactional
    @Override
    public Integer submitSurvey(List<SurveyUserAnswer> userAnswerList, Integer surveyId, Integer examDuration) {
        // 分数表、答案表

        // 分数表新增，目的是获取id
        SurveyUserScore surveyUserScore = new SurveyUserScore();
        surveyUserScore.setSurveyId(surveyId);
        surveyUserScore.setExamDuration(examDuration);
        surveyUserScoreMapper.insert(surveyUserScore);


        // 批改
        Integer userScore = 0;   // 用户得分
        Integer questionNum = 0;  // 试题数
        Integer questionTotalScore = 0;  // 试题总分数
        Integer userCorrectNum = 0;  // 答对数
        String examName = "匿名";   // 考生名

        for(SurveyUserAnswer userAnswer:userAnswerList) {
            userAnswer.setScoreId(surveyUserScore.getId());

            SurveyQuestion question = surveyQuestionService.getById(userAnswer.getQuestionId());
            if(question.getQuestionType() > 0){
                questionNum++;
                questionTotalScore += question.getScore();
            }
            if(question.getQuestionType() == 0
                    && question.getContent().equals("姓名")
                    && StringUtils.hasLength(userAnswer.getUserAnswer())){
                examName = userAnswer.getUserAnswer();
            }
            if(question.getQuestionType() > 0
                    && StringUtils.hasLength(question.getCorrectAnswer())
                    && question.getCorrectAnswer().equals(userAnswer.getUserAnswer())){
                userScore += question.getScore();
                userCorrectNum++;
                userAnswer.setAnswerValid(1);
            }
            surveyUserAnswerMapper.insert(userAnswer);
        };

        // 更新分数表
        surveyUserScore.setScore(userScore);
        surveyUserScore.setCorrectNum(userCorrectNum);
        surveyUserScore.setUserName(examName);
        surveyUserScore.setQuestionNumber(questionNum);
        surveyUserScore.setSurveyScore(questionTotalScore);
        surveyUserScoreMapper.updateById(surveyUserScore);

        // 更新主表答卷数
        baseMapper.updateAnswerTotal(surveyId);


        return surveyUserScore.getId();
    }

    @Override
    public SurveyUserScore getExamResult(Integer scoreId) {
        return surveyUserScoreMapper.selectById(scoreId);
    }

    @Override
    public HashMap<String, Object> getExamRanking(Integer surveyId) {
        Survey survey = baseMapper.selectById(surveyId);

        HashMap<String, Object> data = new HashMap<>();
        data.put("title",survey.getTitle());

        List<SurveyUserScore> list =  surveyUserScoreMapper.getExamRangking(surveyId);
        data.put("rankingList",list);

        return data;
    }


}
