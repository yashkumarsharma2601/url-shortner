package com.personal_projects.url_shortner.controller;

import com.personal_projects.url_shortner.dto.CreateUrlRequest;
import com.personal_projects.url_shortner.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("shorten")
    public String shorten(@RequestBody CreateUrlRequest request) {
        return urlService.createShortUrl(request.getLongUrl());
    }
}
