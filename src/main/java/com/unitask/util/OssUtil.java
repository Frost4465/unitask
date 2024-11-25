package com.unitask.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import com.unitask.config.OssPropertyConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RequiredArgsConstructor
@ConditionalOnExpression(value = "${spring.app.oss.useS3Bucket:false}")
public class OssUtil {

    private final AmazonS3 amazonS3;
    private final OssPropertyConfig ossPropertyConfig;

    @SneakyThrows
    private PutObjectResult putObject(String objectName, InputStream stream) {
        byte[] bytes = IOUtils.toByteArray(stream);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(stream.available());
        //hardcode first
        objectMetadata.setContentType("application/octet-stream");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//        only if need different env
//        String envObjectName = new StringBuilder().append(env).append("/").append(objectName).toString();
        return amazonS3.putObject(ossPropertyConfig.getBucket(), objectName, byteArrayInputStream, objectMetadata);
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

}
