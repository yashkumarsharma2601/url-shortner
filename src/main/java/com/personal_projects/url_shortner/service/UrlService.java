package com.personal_projects.url_shortner.service;

import com.personal_projects.url_shortner.entity.Url;
import com.personal_projects.url_shortner.util.Base62;
import com.personal_projects.url_shortner.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    private final RedisTemplate<String, String> redisTemplate;

    public String createShortUrl(String longUrl) {

        Optional<Url> existing = urlRepository.findByLongUrl(longUrl);

        if (existing.isPresent()) {
            return existing.get().getShortCode();
        }

        Url url = Url.builder()
                .longUrl(longUrl)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();
        urlRepository.save(url);
        String shortCode = Base62.encode(url.getId());
        url.setShortCode(shortCode);
        urlRepository.save(url);

        String key = "url:" + shortCode;
        redisTemplate.opsForValue().set(key, longUrl);
        return shortCode;
    }

    public String getLongUrl(String shortCode) {
        String key = "url:" + shortCode;
        String longUrl = redisTemplate.opsForValue().get(key);
        System.out.println("Redis value:" + longUrl);

        if (longUrl != null) {
            System.out.println("#############Redis Hit");
            return longUrl;
        }

        System.out.println("****************DB Hit");

        Optional<Url> optionalUrl = urlRepository.findActiveUrl(shortCode);

        if (optionalUrl.isEmpty()) {
            return null;
        }
        Url url = optionalUrl.get();
        if (url.getExpiresAt() != null && url.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Link Expired");
        }
        redisTemplate.opsForValue().set(key, url.getLongUrl());
        return url.getLongUrl();
    }
}
