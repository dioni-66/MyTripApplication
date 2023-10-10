package com.mytrip.service;

import com.mytrip.pojo.ApplicationUserDetails;
import com.mytrip.request.CreateUserRequestDTO;
import com.mytrip.response.CreateUserResponseDTO;

public interface UserService {

    ApplicationUserDetails getAuthenticationUserDetails(final String username, final String password);

    void updateLastLoginDate(String username);

    CreateUserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO);
}
