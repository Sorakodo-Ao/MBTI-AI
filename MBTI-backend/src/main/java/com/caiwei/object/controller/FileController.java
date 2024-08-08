package com.caiwei.object.controller;

import com.caiwei.object.common.BaseResponse;
import com.caiwei.object.common.ResultUtils;
import com.caiwei.object.utils.AliyunOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

/**
 * 文件上传
 */
@RestController("/file")
@Slf4j
public class FileController {
    @Resource
    private AliyunOssUtil aliyunOssUtil;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse<String> upload(MultipartFile file) {
        log.info("上传文件是：{}", file);

        //原始文件名
        String originalFilename = file.getOriginalFilename();

        //截取原始文件名的后缀,比如 .png .jpg
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //构造存储在oss的文件名称
        LocalDate downloadTime = LocalDate.now();
        String objectName = UUID.randomUUID() + "-" + downloadTime + extension;

        //返回的文件路径
        String filePath;
        //文件的请求路径
        try {
            filePath = aliyunOssUtil.upload(file.getBytes(), objectName);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        // 返回可访问地址
        return ResultUtils.success(filePath);
    }


}
