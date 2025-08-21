package com.qqcn.survey.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qqcn.survey.entity.SurveyUserAnswer;
import com.qqcn.survey.entity.SurveyUserScore;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SurveyUserScoreMapper extends BaseMapper<SurveyUserScore> {


    @Select("select * from wj_survey_user_score where survey_id = #{survey_id} order by score desc, exam_duration asc")
    List<SurveyUserScore> getExamRangking(Integer surveyId);
}
