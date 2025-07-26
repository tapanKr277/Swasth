package com.org.swasth_id_backend.service;


import com.org.swasth_id_backend.dto.VerificationTokenDto;
import com.org.swasth_id_backend.entity.VerificationToken;

import java.util.List;

public interface VerificationTokenService {

    public VerificationToken createVerificationToken(String email);

    public boolean validateVerificationToken(String token, String email);

    public VerificationToken resendVerificationToken(String email);

    public void deleteVerificationToken(String email);

    List<VerificationTokenDto> getAllTokenList();
}
