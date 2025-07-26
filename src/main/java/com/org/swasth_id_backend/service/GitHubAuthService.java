package com.org.swasth_id_backend.service;

import com.org.swasth_id_backend.dto.JwtResponse;

public interface GitHubAuthService {

    com.org.swasth_id_backend.dto.JwtResponse handleCallBack(String code) throws Exception;
}
