package com.example.ideogram.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdeogramPicGeneratorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 8471416384072280641L;

    boolean success;

    String resultCode;

    String resultMsg;

    private List<String> prompts;

    private Map<String, List<String>> picUrls;

    public static IdeogramPicGeneratorResponse buildSuccessRes(String prompts, List<String> picUrls) {
        IdeogramPicGeneratorResponse response = new IdeogramPicGeneratorResponse();
        response.setSuccess(true);
        response.setResultCode("SUCCESS");
        response.setPrompts(Collections.singletonList(prompts));
        Map<String, List<String>> picUrlsMap = new HashMap<>();
        picUrlsMap.put(prompts, picUrls);
        response.setPicUrls(picUrlsMap);
        return response;
    }


    public List<String> getPrompts() {
        return prompts;
    }

    public void setPrompts(List<String> prompts) {
        this.prompts = prompts;
    }

    public Map<String, List<String>> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(Map<String, List<String>> picUrls) {
        this.picUrls = picUrls;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
