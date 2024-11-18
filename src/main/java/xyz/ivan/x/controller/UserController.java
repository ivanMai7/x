package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.ivan.x.dto.UserDTO;
import xyz.ivan.x.dto.UserVO;
import xyz.ivan.x.entity.User;
import xyz.ivan.x.service.IUserService;

@RestController
@RequestMapping("/v1/auth")
public class UserController {
    @Autowired
    private IUserService userService;
    @PostMapping("/login")
    public UserVO login(@RequestBody UserDTO userDTO){
        User user = userService.getById(1012L);
        System.out.println(user);
        return new UserVO("This is a token.");
    }

    @GetMapping("/hello")
    public String hello(){
        User user = userService.getById(1012L);
        System.out.println(user);
        return "hello";
    }
}
