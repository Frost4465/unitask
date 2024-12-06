package com.unitask.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import com.unitask.config.OssPropertyConfig;
import com.unitask.dto.FileResponse;
import com.unitask.entity.OssFile;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ConditionalOnExpression(value = "${spring.app.oss.useS3Bucket:false}")
@Service
public class OssUtil {

    private final AmazonS3 amazonS3;
    private final OssPropertyConfig ossPropertyConfig;

    @SneakyThrows
    public PutObjectResult putObject(OssFile ossFile) {
        byte[] bytes = IOUtils.toByteArray(ossFile.getMultipartFile().getInputStream());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(ossFile.getMultipartFile().getInputStream().available());
        //hardcode first
        objectMetadata.setContentType("application/octet-stream");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//        only if need different env
//        String envObjectName = new StringBuilder().append(env).append("/").append(objectName).toString();
        return amazonS3.putObject(ossPropertyConfig.getBucket(), ossFile.getPath(), byteArrayInputStream, objectMetadata);
    }

    @SneakyThrows
    public URL getObjectURL(String objectName) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, ossPropertyConfig.getExpireHour());
//        String envObjectName = new StringBuilder().append(env).append("/").append(objectName).toString();
        return amazonS3.generatePresignedUrl(ossPropertyConfig.getBucket(), objectName, calendar.getTime());
    }

    public FileResponse toResponse(OssFile file) {
        if (Objects.isNull(file)) {
            return null;
        }

        FileResponse fileDTO = new FileResponse();
        fileDTO.setId(file.getId());
        fileDTO.setName(file.getName());
        fileDTO.setUrl(getObjectURL(file.getPath()).toString());
        fileDTO.setCreatedDate(file.getCreatedDate());
        return fileDTO;
    }

    public List<FileResponse> toResponse(Collection<? extends OssFile> baseFiles) {

        if (CollectionUtils.isEmpty(baseFiles)) {
            return null;
        }

        List<FileResponse> fileResDTOS = new ArrayList<>();
        for (OssFile baseFile : baseFiles) {
            FileResponse fileResDTO = toResponse(baseFile);
            fileResDTOS.add(fileResDTO);
        }
        return fileResDTOS.stream().sorted(Comparator.comparing(FileResponse::getCreatedDate).
                thenComparing(FileResponse::getName).reversed()).collect(Collectors.toList());
    }

    public static <T extends OssFile> T toBaseOssFile(Class<T> targetClass, String prefix, MultipartFile multipartFile) {
        if (Objects.nonNull(prefix)) {
            try {
                T baseOssFile = targetClass.newInstance();

                String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                String uuid = UUID.randomUUID().toString();
                String path = prefix + "/" + uuid + "." + extension;

                baseOssFile.setUuid(uuid);
                baseOssFile.setName(multipartFile.getOriginalFilename());
                baseOssFile.setPath(path);
                baseOssFile.setMultipartFile(multipartFile);
                return baseOssFile;
            } catch (Exception exception) {
                return null;
            }
        } else return null;
    }

    @SneakyThrows
    public void removeObject(OssFile ossFile) {
        amazonS3.deleteObject(ossPropertyConfig.getBucket(), ossFile.getPath());
    }

}
