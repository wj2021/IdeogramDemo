package com.example.ideogram.model;

public class IdeogramGenerateSingleResponse {
    String prompt;
    String resolution;
    boolean is_image_safe;
    Integer seed;
    String url;
    String style_type;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public boolean isIs_image_safe() {
        return is_image_safe;
    }

    public void setIs_image_safe(boolean is_image_safe) {
        this.is_image_safe = is_image_safe;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStyle_type() {
        return style_type;
    }

    public void setStyle_type(String style_type) {
        this.style_type = style_type;
    }
}