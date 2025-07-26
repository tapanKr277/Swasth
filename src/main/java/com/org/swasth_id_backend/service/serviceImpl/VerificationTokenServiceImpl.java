package com.org.swasth_id_backend.service.serviceImpl;

import com.org.swasth_id_backend.dto.VerificationTokenDto;
import com.org.swasth_id_backend.entity.VerificationToken;
import com.org.swasth_id_backend.mapper.VerificationTokenMapper;
import com.org.swasth_id_backend.repo.VerificationTokenRepo;
import com.org.swasth_id_backend.service.VerificationTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    public String generateVerificationToken(){
        return UUID.randomUUID().toString();
    }

    @Override
    @Transactional
    public VerificationToken createVerificationToken(String email){
        String token = generateVerificationToken();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setEmail(email);
        verificationToken.setExpiryTime(LocalDateTime.now().plusMinutes(3));
        return verificationTokenRepo.save(verificationToken);
    }

    @Override
    public boolean validateVerificationToken(String token, String email){
        VerificationToken verificationToken = verificationTokenRepo.findByTokenAndEmail(token, email).orElseThrow(() -> new RuntimeException("Invalid token"));

        if (verificationToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        return true;
    }

    @Override
    @Transactional
    public VerificationToken resendVerificationToken(String email) {
        verificationTokenRepo.deleteByEmail(email);
        return createVerificationToken(email);
    }

    @Override
    @Transactional
    public void deleteVerificationToken(String email){
        verificationTokenRepo.deleteByEmail(email);
    }

    @Override
    public List<VerificationTokenDto> getAllTokenList() {
        List<VerificationToken> verificationTokenList = verificationTokenRepo.findAll();
        List<VerificationTokenDto> verificationTokenDtoList = new ArrayList<>();
        verificationTokenList.forEach(token-> verificationTokenDtoList.add(VerificationTokenMapper.verificationTokenToVerificationTokenDto(token)));
        return verificationTokenDtoList;
    }
}
