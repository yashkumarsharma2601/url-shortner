package com.personal_projects.url_shortner.service;

import com.personal_projects.url_shortner.entity.Url;
import com.personal_projects.url_shortner.Util.Base62;
import com.personal_projects.url_shortner.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public String createShortUrl(String longUrl) {
        Url url = Url.builder()
                .longUrl(longUrl)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();
        urlRepository.save(url);
        String shortCode = Base62.encode(url.getId());

        url.setShortCode(shortCode);
        urlRepository.save(url);
        return shortCode;
    }
}
