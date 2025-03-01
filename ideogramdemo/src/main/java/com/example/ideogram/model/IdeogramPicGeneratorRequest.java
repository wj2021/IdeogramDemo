package com.example.ideogram.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class IdeogramPicGeneratorRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7004468917380636728L;

    List<String> prompts;

    public List<String> getPrompts() {
        return prompts;
    }

    public void setPrompts(List<String> prompts) {
        this.prompts = prompts;
    }

    @Override
    public String toString() {
        return "IdeogramPicGeneratorRequest{" +
                "prompts=" + prompts +
                '}';
    }
}
