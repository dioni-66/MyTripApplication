package com.mytrip.response;

import com.mytrip.pojo.ApplicationUserDetails;
import lombok.Data;

@Data
public class AuthenticationResponseDTO {
    private ApplicationUserDetails applicationUserDetails;

    public AuthenticationResponseDTO(ApplicationUserDetails applicationUserDetails) {
        this.applicationUserDetails = applicationUserDetails;
    }
}
