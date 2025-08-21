package com.qqcn.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qqcn.base.entity.BaseParam;

import java.util.List;

public interface BaseParamService extends IService<BaseParam> {
    List<BaseParam> getParamListByBaseName(String baseName);
}
