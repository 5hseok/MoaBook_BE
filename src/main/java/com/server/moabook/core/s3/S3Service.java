package com.server.moabook.core.s3;

import com.server.moabook.core.exception.BusinessException;
import com.server.moabook.core.exception.message.ErrorMessage;
import com.server.moabook.core.security.jwt.JwtTokenProvider;
import com.server.moabook.core.security.type.JwtValidationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final S3Presigner s3Presigner;
    private final JwtTokenProvider jwtTokenProvider;

    public GetS3UrlDto getPresignedUrl(String filename, String token) {

        //jwt 토큰 검증
        if (!JwtValidationType.VALID_JWT.equals(jwtTokenProvider.validateToken(token))) {
            throw new BusinessException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION);
        }

        if (filename == null || filename.isEmpty()) {
            return null;
        }

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();
        GetObjectPresignRequest getObjectPresignRequest
                = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest)
                .build();
        PresignedGetObjectRequest presignedGetObjectRequest
                = s3Presigner.presignGetObject(getObjectPresignRequest);

        String url = presignedGetObjectRequest.url().toString();

        s3Presigner.close();

        GetS3UrlDto getS3UrlDto = GetS3UrlDto.builder()
                .preSignedUrl(url)
                .key(filename)
                .build();

        return getS3UrlDto;
    }

    public GetS3UrlDto createPresignedUrl(String keyName, String token) {

        //jwt 토큰 검증
        if (!JwtValidationType.VALID_JWT.equals(jwtTokenProvider.validateToken(token))) {
            throw new BusinessException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION);
        }

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
                    .putObjectRequest(objectRequest)
                    .build();


            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
            String myURL = presignedRequest.url().toString();
            log.info("Presigned URL: " + myURL);
            GetS3UrlDto getS3UrlDto = GetS3UrlDto.builder()
                    .preSignedUrl(myURL)
                    .key(keyName)
                    .build();

            log.info(presignedRequest.url().toExternalForm());
            return getS3UrlDto;
    }
}