package com.mytrip.controller;

import com.mytrip.request.CreateUserRequestDTO;
import com.mytrip.response.CreateUserResponseDTO;
import com.mytrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequestMapping("/rest/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @Secured("ROLE_ADMIN")
    //TODO
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO){
        CreateUserResponseDTO createUserResponseDTO = userService.createUser(createUserRequestDTO);
        return new ResponseEntity<>(createUserResponseDTO, HttpStatus.OK);
    }
}
