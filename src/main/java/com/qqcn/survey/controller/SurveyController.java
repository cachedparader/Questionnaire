package com.qqcn.survey.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qqcn.common.utils.JwtUtils;
import com.qqcn.common.vo.Result;
import com.qqcn.survey.entity.*;
import com.qqcn.survey.service.SurveyQuestionOptionService;
import com.qqcn.survey.service.SurveyQuestionService;
import com.qqcn.survey.service.SurveyService;
import com.qqcn.survey.vo.SurveyQuery;
import com.qqcn.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name="问卷模块", description = "问卷模块接口")
@RestController
@RequestMapping("/survey")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyQuestionService surveyQuestionService;

    @Autowired
    private SurveyQuestionOptionService surveyQuestionOptionService;


    @Operation(summary = "新增问卷")
    @PostMapping
    public Result<?> addSurvey(@RequestBody Survey survey){
        surveyService.save(survey);
        return Result.success(survey);
    }

    @Operation(summary = "根据id查询问卷")
    @GetMapping("/{id}")
    public Result<?> getSurveyById(@PathVariable("id") Integer id){
        Survey survey = surveyService.getById(id);
        return Result.success(survey);
    }

    @Operation(summary = "更新问卷")
    @PutMapping
    public Result<?> updateSurvey(@RequestBody Survey survey){
        survey.setStatus(0);
        surveyService.updateById(survey);
        return Result.success(survey);
    }


    @Operation(summary = "新增问卷问题")
    @PostMapping("/question")
    public Result<?> addSurveyQuestion(@RequestBody SurveyQuestion surveyQuestion){
        surveyQuestionService.addSurveyQuestion(surveyQuestion);
        return Result.success(surveyQuestion);
    }

    @Operation(summary = "更新问卷问题")
    @PutMapping("/question")
    public Result<?> updateSurveyQuestion(@RequestBody SurveyQuestion surveyQuestion){
        System.out.println("---> " + surveyQuestion);
        surveyQuestion.setLud(new Date());
        surveyQuestionService.updateById(surveyQuestion);
        return Result.success(surveyQuestion);
    }

    @Operation(summary = "更新问卷问题选项")
    @PutMapping("/question/option")
    public Result<?> updateSurveyQuestionOption(@RequestBody SurveyQuestionOption surveyQuestionOption){
        surveyQuestionOption.setLud(new Date());
        surveyQuestionOptionService.updateById(surveyQuestionOption);
        return Result.success(surveyQuestionOption);
    }

    @Operation(summary = "新增问卷问题选项")
    @PostMapping("/question/option")
    public Result<?> addSurveyQuestionOption(@RequestBody SurveyQuestionOption surveyQuestionOption){
        surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption);
        return Result.success(surveyQuestionOption);
    }

    @Operation(summary = "查询问卷问题选项列表")
    @GetMapping("/question/option")
    public Result<?> getSurveyQuestionOptionList(@RequestParam("questionId") Integer questionId){
        List<SurveyQuestionOption> list = surveyQuestionOptionService.getSurveyQuestionOptionList(questionId);
        return Result.success(list);
    }

    @Operation(summary = "根据id删除问卷问题选项")
    @DeleteMapping("/question/option/{id}")
    public Result<?> deleteSurveyQuestionOption(@PathVariable("id") Integer id){
        surveyQuestionOptionService.deleteSurveyQuestionOption(id);
        return Result.success();
    }

    @Operation(summary = "查询问卷考生信息列表")
    @GetMapping("/question/examinee/{surveyId}")
    public Result<?> getExamineeList(@PathVariable("surveyId") Integer surveyId){
        List<SurveyQuestion> examineeList = surveyQuestionService.getExamineeList(surveyId);
        return Result.success(examineeList);
    }

    @Operation(summary = "查询问卷试题信息列表")
    @GetMapping("/question/{surveyId}")
    public Result<?> getQuestionList(@PathVariable("surveyId") Integer surveyId){
        List<SurveyQuestion> questionList = surveyQuestionService.getQuestionList(surveyId);
        return Result.success(questionList);
    }

    @Operation(summary = "根据id删除问卷问题")
    @DeleteMapping("/question/{id}")
    public Result<?> deleteSurveyQuestionById(@PathVariable("id") Integer id){
        surveyQuestionService.deleteSurveyQuestionById(id);
        return Result.success();
    }


    @Operation(summary = "更新问题排序")
    @PutMapping("/question/order")
    public Result<?> updateSurveyQuestionOrder(
            @RequestParam("surveyId") Integer surveyId,
            @RequestParam("questionId") Integer questionId,
            @RequestParam("questionType") Integer questionType,
            @RequestParam("oldOrder") Integer oldOrder,
            @RequestParam("newOrder") Integer newOrder

    ){
        surveyQuestionService.updateSurveyQuestionOrder(surveyId,questionId,questionType,oldOrder,newOrder);
        return Result.success();
    }



    @Operation(summary = "根据questionId查询问卷试题详情")
    @GetMapping("/question")
    public Result<?> getQuestionById(@RequestParam("questionId") Integer questionId){
        SurveyQuestion question = surveyQuestionService.getById(questionId);
        question.setAnswerOptions(surveyQuestionOptionService.getSurveyQuestionOptionList(questionId));
        return Result.success(question);
    }

    @Operation(summary = "根据questionId复制问卷试题")
    @GetMapping("/question/copy")
    public Result<?> copyQuestion(@RequestParam("srcQuestionId") Integer srcQuestionId){
        Integer questionId = surveyQuestionService.copyQuestion(srcQuestionId, null);
        return Result.success(questionId);
    }

    @Operation(summary = "问卷完成编辑")
    @PutMapping("/finish")
    public Result<?> finishEditSurvey(@RequestBody Survey survey){
        //surveyService.updateById(survey);
        surveyService.updateSurveyStatus(survey.getId(),1);
        return Result.success();
    }


    @Autowired
    private JwtUtils jwtUtils;
    @Operation(summary = "分页查询问卷列表")
    @GetMapping("/list")
    public Result<?> getSurveyList(SurveyQuery param, @RequestHeader("Authorization") String jwt){
        User user = jwtUtils.parsseJwt(jwt, User.class);
        param.setUserId(user.getId());
        Page<Survey> page =  surveyService.getSurveyList(param);
        Map<String, Object> data = Map.of(
                "total",page.getTotal(),
                "rows", page.getRecords()
        );
        return Result.success(data);
    }

    @Operation(summary = "问卷星标更新")
    @PutMapping("/star")
    public Result<?> updateSurveyStar(@RequestParam("surveyId") Integer surveyId,
                                      @RequestParam("star") Integer star){

        surveyService.updateSurveyStar(surveyId,star);
        return Result.success();
    }

    @Operation(summary = "问卷复制")
    @PostMapping("/copy/{sourceSurveyId}")
    public Result<?> copySurvey(@PathVariable("sourceSurveyId") Integer sourceSurveyId){

        Integer newSurveyId =  surveyService.copySurvey(sourceSurveyId);
        return Result.success(newSurveyId);
    }

    @Operation(summary = "问卷逻辑删除")
    @DeleteMapping("/{surveyId}")
    public Result<?> deleteSurveyToRecycle(@PathVariable("surveyId") Integer surveyId){

        surveyService.deleteSurveyToRecycle(surveyId);
        return Result.success();
    }

    @Operation(summary = "问卷物理删除")
    @DeleteMapping("/physical/{surveyId}")
    public Result<?> deleteSurvey(@PathVariable("surveyId") Integer surveyId){

        surveyService.deleteSurvey(surveyId);
        return Result.success();
    }

    @Operation(summary = "回收站列表查询")
    @GetMapping("/recycle/list")
    public Result<?> getRecycleList(@RequestHeader("Authorization") String jwt){
        User user = jwtUtils.parsseJwt(jwt, User.class);
        List<Survey> list =  surveyService.getRecycleList(user.getId());
        return Result.success(list);
    }

    @Operation(summary = "问卷恢复")
    @PutMapping("/restore/{surveyId}")
    public Result<?> restoreSurvey(@PathVariable("surveyId") Integer surveyId){
        surveyService.restoreSurvey(surveyId);
        return Result.success();
    }

    @Operation(summary = "问卷发布")
    @PutMapping("/publish/{surveyId}")
    public Result<?> publishSurvey(@PathVariable("surveyId") Integer surveyId){
        Integer status = 2;
        surveyService.updateSurveyStatus(surveyId,status);
        return Result.success(status);
    }

    @Operation(summary = "问卷停止")
    @PutMapping("/stop/{surveyId}")
    public Result<?> stopSurvey(@PathVariable("surveyId") Integer surveyId){
        Integer status = 1;
        surveyService.updateSurveyStatus(surveyId,status);
        return Result.success(status);
    }

    @Operation(summary = "根据id查询考试问卷")
    @GetMapping("/exam/{surveyId}")
    public Result<?> getSurveyForExam(@PathVariable("surveyId") Integer surveyId){
        Survey survey = surveyService.getById(surveyId);

        if(survey == null || survey.getStatus()!=2){
            return Result.success();
        }


        survey.setExamineeList(surveyQuestionService.getExamineeList(surveyId));

        List<SurveyQuestion> questionList = surveyQuestionService.getQuestionList(surveyId);
        questionList.forEach(item -> {
            item.setCorrectAnswer(null);
        });

        survey.setQuestionList(questionList);


        return Result.success(survey);
    }

    @Operation(summary = "提交考试")
    @PostMapping("/exam")
    public Result<?> submitSurvey(@RequestBody List<SurveyUserAnswer> userAnswerList,
                                  @RequestParam("surveyId") Integer surveyId,
                                  @RequestParam("examDuration") Integer examDuration){

        Integer scoreId = surveyService.submitSurvey(userAnswerList,surveyId,examDuration);

        return Result.success(scoreId);
    }

    @Operation(summary = "查询考试结果")
    @GetMapping("/exam/result")
    public Result<?> getExamResult(@RequestParam("scoreId") Integer scoreId){

        SurveyUserScore surveyUserScore = surveyService.getExamResult(scoreId);

        return Result.success(surveyUserScore);
    }

    @Operation(summary = "查询考试详情")
    @GetMapping("/exam/detail")
    public Result<?> getExamDetail(@RequestParam("scoreId") Integer scoreId){

        HashMap<String, Object> data = new HashMap<>();
        data.put("examineeList", surveyQuestionService.getExamineeListForEaxm(scoreId));
        data.put("questionList", surveyQuestionService.getQuestionListForEaxm(scoreId));

        return Result.success(data);
    }

    @Operation(summary = "查询排行榜")
    @GetMapping("/exam/ranking")
    public Result<?> getExamRanking(@RequestParam("surveyId") Integer surveyId){

        HashMap<String, Object> data = surveyService.getExamRanking(surveyId);

        return Result.success(data);
    }

}
