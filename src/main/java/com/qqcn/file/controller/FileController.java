package com.qqcn.file.controller;

import com.qqcn.common.constant.ResultConstant;
import com.qqcn.common.vo.Result;
import com.qqcn.file.utils.MinioUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/file")
@Tag(name = "文件服务",description = "文件接口")
@Slf4j
public class FileController {

    @Autowired
    private MinioUtils minioUtils;

    @Operation(summary = "文件上传")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public Result<Map<String,Object>> upload(@RequestPart("file") MultipartFile file){
        if(file == null || file.getSize() ==0){
            return Result.fail(ResultConstant.FAIL_FILE_UPLOAD_NULL.getCode(),ResultConstant.FAIL_FILE_UPLOAD_NULL.getMessage());
        }
        try {
            Map<String, Object> uploadResult = minioUtils.upload(file);
            return Result.success(uploadResult);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail(ResultConstant.FAIL_FILE_UPLOAD_ERROR.getCode(),ResultConstant.FAIL_FILE_UPLOAD_ERROR.getMessage());
        }
    }

    @Operation(summary = "获取文件访问url")
    @GetMapping("/url/{fileName}")
    public Result<String> getFileUrl(@PathVariable("fileName") String fileName){
        String url = minioUtils.getUrl(fileName);
        return Result.success(url,"success");
    }

}
