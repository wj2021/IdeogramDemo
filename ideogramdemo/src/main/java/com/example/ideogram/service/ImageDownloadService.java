package com.example.ideogram.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageDownloadService {

    @Value("${image.save.path}")
    private String savePath;

    private final RestTemplate restTemplate = new RestTemplate();

    public String downloadImage(String imageUrl) {
        try {
            // 验证并创建存储目录
            Path storagePath = Paths.get(savePath);
            if (!Files.exists(storagePath)) {
                Files.createDirectories(storagePath);
            }

            // 生成唯一文件名
            String extension = FilenameUtils.getExtension(imageUrl);
            String fileName = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
            Path targetPath = storagePath.resolve(fileName);

            // 保存文件到本地
            // Files.write(targetPath, response.getBody().getByteArray());

            // 使用流式下载代替内存缓冲
            InputStream inputStream = restTemplate.execute(
                    imageUrl,
                    HttpMethod.GET,
                    null,
                    clientHttpResponse -> {
                        if (!clientHttpResponse.getStatusCode().is2xxSuccessful()) {
                            throw new IOException("Failed to download image from URL");
                        }

                        clientHttpResponse.getBody();
                        FileUtils.copyInputStreamToFile(
                                clientHttpResponse.getBody(),
                                new File(targetPath.toString())
                        );

                        return null;
                    }
            );

            System.out.println("保存图片成功:" + imageUrl + "，保存位置：" + targetPath);

            return targetPath.toString();
        } catch (Throwable t) {
            // 需要接入日志
            System.out.println("图片下载失败:" + imageUrl);
            t.printStackTrace();
            return null;
        }
    }
}