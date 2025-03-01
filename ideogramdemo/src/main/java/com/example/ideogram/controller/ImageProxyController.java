package com.example.ideogram.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * 图片代理
 */
@RestController
@RequestMapping("/api")
public class ImageProxyController {

    private final WebClient webClient = WebClient.create();

    @Value("${image.save.path}")
    private String savePath;

    @GetMapping("/proxy/image")
    public Mono<ResponseEntity<byte[]>> proxyImage(
            @RequestParam("url") String imageUrl,
            @RequestHeader HttpHeaders headers) {

        // 安全验证（示例：仅允许指定域名）
        if (!isAllowedDomain(imageUrl)) {
            System.out.print("禁止展示图片");
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }

        return webClient.get()
                .uri(imageUrl)
                .headers(h -> h.addAll(headers))
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(byte[].class)
                                .map(body -> ResponseEntity.ok()
                                        .contentType(response.headers().contentType().orElse(MediaType.IMAGE_JPEG))
                                        .body(body));
                    } else {
                        return Mono.just(ResponseEntity.status(response.statusCode()).build());
                    }
                });
    }

    @GetMapping("/image")
    public Mono<ResponseEntity<Resource>> image(
            @RequestParam("url") @PathVariable String imageUrl) {
        // 验证并创建存储目录
        Path storagePath = Paths.get(imageUrl);
        if (!Files.exists(storagePath)) {
            return null;
        }

        String contentType = determineContentType(imageUrl);

        return loadImage(imageUrl)
                .map(resource -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.status(404)
                                .body(null)
                ));
    }

    private String determineContentType(String filename) {
        String extension = FilenameUtils.getExtension(filename).toLowerCase();
        return switch (extension) {
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "image/jpeg";
        };
    }

    public Mono<Resource> loadImage(String filePath) {
        Path storagePath = Paths.get(filePath);
        return Mono.fromCallable(() -> {
            try {
                Path file = storagePath.normalize();
                Resource resource = new UrlResource(file.toUri());

                if (resource.exists() && resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("File not found: " + filePath);
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid file path: " + filePath, e);
            }
        }).onErrorMap(e -> new RuntimeException("Failed to load image", e));
    }

    private boolean isAllowedDomain(String url) {
        // 实现域名白名单验证（示例允许ideogram）
        return url.contains("ideogram.ai")
                || url.contains("vcg") || url.contains("wallhaven");
    }
}
