package com.caiwei.object.utils;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.caiwei.object.config.AliyunOssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

@Component
@Slf4j
public class AliyunOssUtil {
    @Resource
    private AliyunOssProperties aliyunOssProperties;
 
    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName 在oss存储的名字！！！！
     * @return
     */
    public String upload(byte[] bytes, String objectName) {
 
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(),
                aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
 
        try {
            // 创建PutObject请求。
            ossClient.putObject(aliyunOssProperties.getBucketName(), objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
 
        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(aliyunOssProperties.getBucketName())
                .append(".")
                .append(aliyunOssProperties.getEndpoint())
                .append("/")
                .append(objectName);
 
        log.info("文件路径:{}", stringBuilder);
        //返回存储文件的远程路径
        return stringBuilder.toString();
    }
 
}