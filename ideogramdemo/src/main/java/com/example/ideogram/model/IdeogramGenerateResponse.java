package com.example.ideogram.model;

import java.util.List;

public class IdeogramGenerateResponse {
    String created;

    List<IdeogramGenerateSingleResponse> data;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<IdeogramGenerateSingleResponse> getData() {
        return data;
    }

    public void setData(List<IdeogramGenerateSingleResponse> data) {
        this.data = data;
    }


}

