package com.qqcn.base.controller;

import com.qqcn.base.entity.BaseParam;
import com.qqcn.base.service.BaseParamService;
import com.qqcn.common.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "基础参数模块", description = "基础参数模块接口")
@RestController
@RequestMapping("/base")
public class BaseParamController {


    @Autowired
    private BaseParamService baseParamService;

    @Operation(summary = "查询参数列表")
    @GetMapping
    public Result<?> getParamListByBaseName(@RequestParam("baseName") String baseName){
        List<BaseParam> list = baseParamService.getParamListByBaseName(baseName);
        return  Result.success(list);
    }
}
