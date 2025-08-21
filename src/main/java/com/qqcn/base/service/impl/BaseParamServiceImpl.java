package com.qqcn.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qqcn.base.entity.BaseParam;
import com.qqcn.base.mapper.BaseParamMapper;
import com.qqcn.base.service.BaseParamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseParamServiceImpl extends ServiceImpl<BaseParamMapper, BaseParam> implements BaseParamService {
    @Override
    public List<BaseParam> getParamListByBaseName(String baseName) {
        return baseMapper.getParamListByBaseName(baseName);
    }
}
