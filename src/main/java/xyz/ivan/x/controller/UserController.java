package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.ivan.x.entity.User;
import xyz.ivan.x.service.IUserService;
import xyz.ivan.x.util.Result;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    /**
     * 发送验证码
     * @param phoneNumber
     * @return
     */
    @PostMapping("/sendCode/{phoneNumber}")
    public Result sendCode(@PathVariable("phoneNumber") String phoneNumber){
        return userService.sendCode(phoneNumber);
    }

    @PostMapping("login/{phoneNumber}/{code}")
    public Result login(@PathVariable("phoneNumber") String phoneNumber, @PathVariable("code") String code){
        return userService.login(phoneNumber,code);
    }

    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return userService.saveUser(user);
    }

    @DeleteMapping("/remove/{id}")
    public Result removeById(@PathVariable Long id){
        return userService.removeUserById(id);
    }

    @GetMapping("/list")
    public Result listUsers(){
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

}
