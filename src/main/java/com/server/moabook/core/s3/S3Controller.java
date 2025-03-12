package com.server.moabook.core.s3;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {
    private final S3Service s3Service;

    @GetMapping
    public ResponseEntity<GetS3UrlDto> getPreSignedUrl(
            @RequestParam("fileName") String fileName,
            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(s3Service.getPresignedUrl(fileName, token));
    }

    @GetMapping("/presign")
    public ResponseEntity<GetS3UrlDto> getPutPreSignedUrl(
            @RequestParam("fileName") String fileName,
            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(s3Service.createPresignedUrl(fileName, token));
    }

}
