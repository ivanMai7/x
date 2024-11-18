package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ivan.x.service.IFollowService;
import xyz.ivan.x.util.Result;

@RestController
@RequestMapping("/v1")
public class FollowController {
    @Autowired
    private IFollowService followService;

    @GetMapping("/follow/{followerId}")
    public Result getFollowById(@PathVariable("followerId") Long followerId){
        return followService.getFolloweeListByFollowerId(followerId);
    }
}
