package com.example.ideogram.controller;

import com.example.ideogram.model.IdeogramPicGeneratorRequest;
import com.example.ideogram.model.IdeogramPicGeneratorResponse;
import com.example.ideogram.service.IdeogramPicGeneratorService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ideogram")
public class IdeogramPicGenerator {

    @Autowired
    private IdeogramPicGeneratorService ideogramPicGeneratorService;

    @RequestMapping(value="/generate", method = RequestMethod.POST)
    @ResponseBody
    public IdeogramPicGeneratorResponse generate(@RequestBody IdeogramPicGeneratorRequest request) {
        System.out.println("request:" + request);
        Assert.notNull(request, "request must not be null");
        Assert.notEmpty(request.getPrompts(), "prompts must not be empty");

        String inputPrompt = request.getPrompts().get(0);
        List<String> picUrlList = ideogramPicGeneratorService.generatePicByIdeogram(inputPrompt);
        if (CollectionUtils.isNotEmpty(picUrlList)) {
            return IdeogramPicGeneratorResponse.buildSuccessRes(inputPrompt, picUrlList);
        }

        return null;
    }

    public void setIdeogramPicGeneratorService(IdeogramPicGeneratorService ideogramPicGeneratorService) {
        this.ideogramPicGeneratorService = ideogramPicGeneratorService;
    }
}
