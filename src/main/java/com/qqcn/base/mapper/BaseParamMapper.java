package com.qqcn.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qqcn.base.entity.BaseParam;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BaseParamMapper extends BaseMapper<BaseParam> {

    @Select("select * from wj_base_param where base_name=#{baseName} order by priority")
    List<BaseParam> getParamListByBaseName(String baseName);
}
