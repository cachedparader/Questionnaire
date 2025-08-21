import request from "@/utils/request";

export default{
    addSurvey(survey){
        return request({
            method: "post",
            url: `/survey`,
            data: survey
        });
    },
    getSurveyById(id){
        return request({
            method: "get",
            url: `/survey/${id}`
        });
    },
    updateSurvey(survey){
        return request({
            method: "put",
            url: `/survey`,
            data: survey
        });
    },
    addSurveyQuestion(surveyQuestion){
        return request({
            method: "post",
            url: `/survey/question`,
            data: surveyQuestion
        });
    },
    getExamineeList(surveyId){
        return request({
            method: "get",
            url: `/survey/question/examinee/${surveyId}`
        });
    },
    deleteSurveyQuestion(id){
        return request({
            method: "delete",
            url: `/survey/question/${id}`
        });
    },
    updateSurveyQuestionOrder(surveyId,questionId,questionType,oldOrder,newOrder){
        return request({
            method: "put",
            url: `/survey/question/order`,
            params: {
                surveyId,questionId,questionType,oldOrder,newOrder
            }
        });
    },
    updateSurveyQuestion(surveyQuestion){
        if(Array.isArray(surveyQuestion.correctAnswer)){
            surveyQuestion.correctAnswer = surveyQuestion.correctAnswer.sort((a,b) => a-b);
            surveyQuestion.correctAnswer = surveyQuestion.correctAnswer.join(",")
        }
        return request({
            method: "put",
            url: `/survey/question`,
            data: surveyQuestion
        });
    },
    updateSurveyQuestionOption(surveyQuestionOption){
        return request({
            method: "put",
            url: `/survey/question/option`,
            data: surveyQuestionOption
        });
    },
    addSurveyQuestionOption(surveyQuestionOption){
        return request({
            method: "post",
            url: `/survey/question/option`,
            data: surveyQuestionOption
        });
    },
    getSurveyQuestionOptionList(questionId){
        return request({
            method: "get",
            url: `/survey/question/option`,
            params: {
                questionId
            }
        });
    },
    deleteSurveyQuestionOption(optionId){
        return request({
            method: "delete",
            url: `/survey/question/option/${optionId}`
        });
    },
    getQuestionList(surveyId){
        return request({
            method: "get",
            url: `/survey/question/${surveyId}`
        });
    },
    getQuestionById(questionId){
        return request({
            method: "get",
            url: `/survey/question`,
            params: {
                questionId
            }
        });
    },
    copyQuestionById(srcQuestionId){
        return request({
            method: "get",
            url: `/survey/question/copy`,
            params: {
                srcQuestionId
            }
        });
    },
    finishEditSurvey(survey){
        return request({
            method: "put",
            url: `/survey/finish`,
            data: survey
        });
    },
    getSurveyList(param){
        return request({
            method: "get",
            url: `/survey/list`,
            params: param
        });
    },
    updateSurveyStar(surveyId,star){
        return request({
            method: "put",
            url: `/survey/star`,
            params: {
                surveyId,star
            }
        });
    },
    copySurvey(sourceSurveyId){
        return request({
            method: "post",
            url: `/survey/copy/${sourceSurveyId}`
        });
    },
    deleteSurveyToRecycle(surveyId){
        return request({
            method: "delete",
            url: `/survey/${surveyId}`
        });
    },
    getRecycleList(){
        return request({
            method: "get",
            url: `/survey/recycle/list`
        });
    },
    restoreSurvey(surveyId){
        return request({
            method: "put",
            url: `/survey/restore/${surveyId}`
        });
    },
    deleteSurvey(surveyId){
        return request({
            method: "delete",
            url: `/survey/physical/${surveyId}`
        });
    },
    publishSurvey(surveyId){
        return request({
            method: "put",
            url: `/survey/publish/${surveyId}`
        });
    },
    stopSurvey(surveyId){
        return request({
            method: "put",
            url: `/survey/stop/${surveyId}`
        });
    },
    getSurveyForExam(surveyId){
        return request({
            method: "get",
            url: `/survey/exam/${surveyId}`
        });
    },
    submitExam(userAnwserList,surveyId,examDuration){
        return request({
            method: "post",
            url: `/survey/exam`,
            data: userAnwserList,
            params: {
                surveyId,examDuration
            }
        });
    },
    getExamResult(scoreId){
        return request({
            method: "get",
            url: `/survey/exam/result`,
            params: {
                scoreId
            }
        });
    },
    getExamDetail(scoreId){
        return request({
            method: "get",
            url: `/survey/exam/detail`,
            params: {
                scoreId
            }
        });
    },
    getExamRanking(surveyId){
        return request({
            method: "get",
            url: `/survey/exam/ranking`,
            params: {
                surveyId
            }
        });
    },
}