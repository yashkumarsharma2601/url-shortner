package com.personal_projects.url_shortner.repository;

import com.personal_projects.url_shortner.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortCode(String shortCode);

    @Query("SELECT u FROM Url u WHERE u.shortCode = :code AND u.isActive = true")
    Optional<Url> findActiveUrl(@Param("code") String code);
}
