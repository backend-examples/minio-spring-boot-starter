package com.minio.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.minio.enums.MimeTypeEnum;
import com.minio.properties.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Part;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 文件服务器工具类
 */
public class MinioTemplate {

    @Autowired
    private MinioProperties minioProperties;

    @Autowired(required = false)
    private CustomMinioClient customMinioClient;

    public MinioTemplate(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
        this.customMinioClient = new CustomMinioClient(MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build());
    }

    /**
     * 查看存储bucket是否存在
     * @return boolean
     */
    public Boolean bucketExists(String bucketName) {
        boolean found;

        try {
            found = customMinioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return found;
    }

    /**
     * 创建存储bucket
     * @return Boolean
     */
    public Boolean makeBucket(String bucketName) {
        try {
            customMinioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 根据bucketname删除信息
     *
     * @param bucketname bucket名称
     */
    public void removeBucket(String bucketname)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        customMinioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketname).build());
    }

    /**
     * 不存在存储桶，则新建一个
     */
    @PostConstruct
    public void init() {
        if (!bucketExists(minioProperties.getBucket())) {
            makeBucket(minioProperties.getBucket());
        }
    }

    /**
     * 检查文件是否存在
     * @param filename
     * @return
     */
    @SneakyThrows
    public StatObjectResponse fileExited(String filename) {
        return customMinioClient.statObject(StatObjectArgs.builder().bucket(minioProperties.getBucket()).object(filename).build());
    }

    /**
     * 获取文件流
     *
     * @param bucket 存储桶名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    public InputStream getObject(String bucket, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return customMinioClient
                .getObject(GetObjectArgs.builder().bucket(bucket).object(objectName).build());
    }

    /**
     *  上传分片上传请求，返回uploadId
     */
    @SneakyThrows
    public String getUploadId(String bucket, String region, String objectName, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) {
        return customMinioClient.createMultipartUpload(bucket, region, objectName, headers, extraQueryParams).result().uploadId();
    }

    /**
     *
     * @param bucketName 存储桶
     * @param objectName 文件目录名(可选)/文件名
     * @param totalPart 总分片数
     * @return
     */
    @SneakyThrows
    public Map<String, Object> initMultiPartUpload(String bucketName, String objectName, int totalPart, String fileType) {
        Map<String, Object> result = new HashMap<>();

        // 设置header可以用来更改文件的content-type，用来避免MP4文件的链接被迅雷自动劫持，导致视频无法播放
        Multimap<String, String> headers= HashMultimap.create();
        if (fileType != null) {
            headers.put("Content-Type", fileType);
        }

        String uploadId = getUploadId(bucketName, null, objectName, headers, null);

        result.put("uploadId", uploadId);

        List<String> partList = new ArrayList<>();
        Map<String, String> reqParams = new HashMap<>();

//            reqParams.put("response-content-type", "application/json");
        reqParams.put("uploadId", uploadId);

        for (int i = 1; i <= totalPart; i++) {
            reqParams.put("partNumber", String.valueOf(i));

            String uploadUrl = customMinioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(1, TimeUnit.DAYS)
                            .extraQueryParams(reqParams)
                            .build());

            partList.add(uploadUrl);
        }

        result.put("uploadUrls", partList);

        return result;
    }

    /**
     * 分片合并
     * @param bucketName
     * @param objectName 文件目录名(可选)/文件名，这里要和获取文件分片地址的时候保持一致
     * @param uploadId
     * @return
     */
    @SneakyThrows
    public ObjectWriteResponse mergeMultipartUpload(String bucketName, String objectName, String uploadId) {
        Part[] parts = new Part[10000];
        //此方法注意2020.02.04之前的minio服务端有bug
        ListPartsResponse partResult = customMinioClient.listMultipart(bucketName, null, objectName, 10000, 0, uploadId, null, null);
        int partNumber = 1;

        for (Part part : partResult.result().partList()) {
            parts[partNumber - 1] = new Part(partNumber, part.etag());
            partNumber++;
        }

        ObjectWriteResponse completeMultipartUpload = customMinioClient.completeMultipartUpload(bucketName, null, objectName, uploadId, parts, null, null);

        return completeMultipartUpload;
    }

    /**
     * 获取存储桶策略
     *
     * @param bucket 存储桶名称
     * @return json
     */
    private JSONObject getBucketPolicy(String bucket)
            throws IOException, InvalidKeyException, InvalidResponseException, BucketPolicyTooLargeException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InsufficientDataException, ErrorResponseException {
        String bucketPolicy = customMinioClient
                .getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucket).build());
        return JSONObject.parseObject(bucketPolicy);
    }

    /**
     * 文件上传
     * @param file 文件
     * @return Boolean
     */
    @SneakyThrows
    public String upload(MultipartFile file) {
        // 修饰过的文件名 非源文件名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fullPath = MimeTypeEnum.getClassifyType(suffix) + "/" + file.getOriginalFilename();

        PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(fullPath)
                .stream(file.getInputStream(), file.getSize(),-1).contentType(file.getContentType()).build();

        //文件名称相同会覆盖
        customMinioClient.putObject(objectArgs);

        return preview(minioProperties.getBucket(), fullPath);
    }

    /**
     * 使用putObject上传一个文件到文件分类
     *
     * @param fileName ： 文件名
     * @param stream   ： 文件流
     * @throws Exception ： 异常
     */
    @SneakyThrows
    public void upload(String fileName, InputStream stream) throws IOException {
        PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(fileName)
                .stream(stream, stream.available(), -1).build();

        //文件名称相同会覆盖
        customMinioClient.putObject(objectArgs);
    }

    /**
     * 上传本地文件
     *
     * @param bucket 存储桶
     * @param objectName 对象名称
     * @param fileName 本地文件路径
     */
    public ObjectWriteResponse putObject(String bucket, String objectName,
                                                String fileName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return customMinioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucket).object(objectName).filename(fileName).build());
    }

    /**
     * 创建文件夹或目录
     *
     * @param bucket 存储桶
     * @param objectName 目录路径
     */
    public ObjectWriteResponse putDirObject(String bucket, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return customMinioClient.putObject(
                PutObjectArgs.builder().bucket(bucket).object(objectName).stream(
                                new ByteArrayInputStream(new byte[]{}), 0, -1)
                        .build());
    }

    /**
     * 预览
     * @param fileName 是上传图片的fullPath=>eg:2021-12/27/typora-setup-x64.exe
     * @return
     */
    @SneakyThrows
    public String preview(String bucket, String fileName) {
        customMinioClient.statObject(StatObjectArgs.builder().bucket(bucket).object(fileName).build());

        return customMinioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucket).expiry(7, TimeUnit.DAYS).object(fileName).build());
    }

    /**
     * 断点下载
     *
     * @param bucket bucket名称
     * @param objectName 文件名称
     * @param offset 起始字节的位置
     * @param length 要读取的长度
     * @return 流
     */
    public InputStream getObject(String bucket, String objectName, long offset, long length)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return customMinioClient.getObject(
                GetObjectArgs.builder().bucket(bucket).object(objectName).offset(offset).length(length)
                        .build());
    }

    /**
     * 获取文件信息, 如果抛出异常则说明文件不存在
     *
     * @param bucket bucket名称
     * @param objectName 文件名称
     */
    public StatObjectResponse statObject(String bucket, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return customMinioClient
                .statObject(StatObjectArgs.builder().bucket(bucket).object(objectName).build());
    }

    /**
     * 删除文件
     *
     * @param bucket bucket名称
     * @param objectName 文件名称
     */
    public void removeObject(String bucket, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        customMinioClient
                .removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
    }
}
