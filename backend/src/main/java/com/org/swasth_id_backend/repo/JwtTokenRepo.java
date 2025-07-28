package com.org.swasth_id_backend.repo;

import com.org.swasth_id_backend.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JwtTokenRepo extends JpaRepository<JwtToken, UUID> {

    List<JwtToken> findByUserId(UUID userId);

    List<JwtToken> findByUserIdAndBlacklisted(UUID userId, Boolean blacklisted);

    JwtToken findByToken(String token);
}
