package com.example.ideogram.service;

import com.alibaba.fastjson2.JSON;
import com.example.ideogram.dao.mapper.IdeoPicMapper;
import com.example.ideogram.dao.model.IdeoPic;
import com.example.ideogram.model.IdeogramGenerateResponse;
import com.example.ideogram.model.IdeogramGenerateSingleResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class IdeogramPicGeneratorService {

    @Value("${ideogram.key}")
    public String apiKey;

    @Autowired
    private IdeoPicMapper ideoPicMapper;

    @Autowired
    private ImageDownloadService imageDownloadService;

    public List<String> generatePicByIdeogram(String prompt) {

        // String ideogramRes = "";
        // 先mock返回结果
        String ideogramRes = "{\n" +
                "  \"created\": \"2000-01-23T04:56:07Z\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"prompt\": \"A serene tropical beach scene. Dominating the foreground are tall palm trees with lush green leaves, standing tall against a backdrop of a sandy beach. The beach leads to the azure waters of the sea, which gently kisses the shoreline. In the distance, there's an island or landmass with a silhouette of what appears to be a lighthouse or tower. The sky above is painted with fluffy white clouds, some of which are tinged with hues of pink and orange, suggesting either a sunrise or sunset.\",\n" +
                "      \"resolution\": \"1024x1024\",\n" +
                "      \"is_image_safe\": true,\n" +
                "      \"seed\": 12345,\n" +
                "      \"url\": \"http://gips2.baidu.com/it/u=1674525583,3037683813&fm=3028&app=3028&f=JPEG&fmt=auto?w=1024&h=1024\",\n" +
                "      \"style_type\": \"REALISTIC\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // https://w.wallhaven.cc/full/kx/wallhaven-kxm9gm.png

       try {
           HttpResponse<String> response = Unirest.post("https://api.ideogram.ai/generate")
                   .header("Api-Key", apiKey)
                   .header("Content-Type", "application/json")
                   .body("{\n  \"image_request\": {\n    \"prompt\": \"" + prompt + "\",\n    \"aspect_ratio\": \"ASPECT_10_16\",\n    \"model\": \"V_2\",\n    \"magic_prompt_option\": \"AUTO\"\n  }\n}")
                   .asString();

           if (response.isSuccess()) {
               ideogramRes = response.getBody();
           }
       } catch (Throwable t) {
           // TODO 需要接入日志logback
           System.out.println("error error error");
//            throw t;
       }

        List<String> picUrls = convertRes(ideogramRes);

        if (CollectionUtils.isNotEmpty(picUrls)) {
            downloadAndSavePic(picUrls, prompt);
        }

        return picUrls;
    }

    /**
     * 转换ideogram返回的结果
     * @param ideogramRes
     * @return
     */
    private List<String> convertRes(String ideogramRes) {
        IdeogramGenerateResponse response = JSON.parseObject(ideogramRes, IdeogramGenerateResponse.class);

        if (response == null) {
            return null;
        }

        List<String> urlList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(response.getData())) {
            for (IdeogramGenerateSingleResponse singleResponse : response.getData()) {
                urlList.add(singleResponse.getUrl());
            }
        }

        return urlList;
    }

    /**
     * 下载图片到本地服务器
     * 由于生成图片返回的链接会失效，因此需要将图片下载到本地长期保存
     * @param picUrls
     */
    private void downloadAndSavePic(List<String> picUrls, String prompt) {
        for (String picUrl : picUrls) {
            String resPath = imageDownloadService.downloadImage(picUrl);
            if (StringUtils.isNotBlank(resPath)) {
                IdeoPic ideoPic = new IdeoPic();
                ideoPic.setId(UUID.randomUUID().toString());
                ideoPic.setUserId("admin_test");
                ideoPic.setPrompts(prompt);
                ideoPic.setPicLink(resPath);
                ideoPic.setOriPicLink(picUrl);
                ideoPic.setGmtCreate(new Date());
                ideoPic.setGmtModified(new Date());
                ideoPicMapper.insert(ideoPic);
            }
        }
    }

    /**
     * 保存图片信息到数据库中
     * 保存图片可以方面以后的图片搜索与查看
     */
    private void savePic() {

    }

    public void setIdeoPicMapper(IdeoPicMapper ideoPicMapper) {
        this.ideoPicMapper = ideoPicMapper;
    }

    public void setImageDownloadService(ImageDownloadService imageDownloadService) {
        this.imageDownloadService = imageDownloadService;
    }
}
