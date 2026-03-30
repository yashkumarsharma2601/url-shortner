package com.personal_projects.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlCacheDto {
    private String longUrl;
    private LocalDateTime expiresAt;
    private Boolean isActive;
}
