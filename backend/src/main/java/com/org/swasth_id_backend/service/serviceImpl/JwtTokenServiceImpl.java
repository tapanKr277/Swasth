package com.org.swasth_id_backend.service.serviceImpl;

import com.org.swasth_id_backend.dto.UserDto;
import com.org.swasth_id_backend.entity.JwtToken;
import com.org.swasth_id_backend.repo.JwtTokenRepo;
import com.org.swasth_id_backend.repo.UserRepo;
import com.org.swasth_id_backend.service.JwtService;
import com.org.swasth_id_backend.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Autowired
    private JwtTokenRepo jwtTokenRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Override
    public String generateToken(UserDto userDto) {
        invalidateOldToken(userDto.getUserId());
        String newToken = jwtService.generateToken(userDto);
        saveNewToken(userDto.getUserId(), newToken, 7);
        return newToken;
    }

    @Override
    public JwtToken getJwtTokenByUserIdAndBlacklisted(UUID userId, Boolean blackListed) {
//        return jwtTokenRepo.findByUserIdAndBlacklisted(userId, blackListed)
        return  null;
    }

    @Override
    public JwtToken getJwtTokenByToken(String token) {
        return jwtTokenRepo.findByToken(token);
    }

    @Override
    public JwtToken saveToken(JwtToken jwtToken) {
        return jwtTokenRepo.save(jwtToken);
    }

    @Override
    public String generateRefreshToken(UserDto userDto) {
        String newRefreshToken = jwtService.generateRefreshToken(userDto);
        saveNewToken(userDto.getUserId(), newRefreshToken, 30);
        return newRefreshToken;
    }


    private void invalidateOldToken(UUID userId){
        List<JwtToken> userTokens = jwtTokenRepo.findByUserId(userId);
        for (JwtToken token : userTokens) {
            jwtTokenRepo.delete(token);
//            if (!token.getBlacklisted()) {
//                token.setBlacklisted(true);
//                jwtTokenRepo.save(token);
//            }
        }
    }


    private void saveNewToken(UUID userId, String newToken, Integer expirationTime){
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(newToken);
        jwtToken.setUserId(userId);
        jwtToken.setExpirationTime(LocalDateTime.now().plusMinutes(expirationTime));
        jwtTokenRepo.save(jwtToken);
    }
}
