package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.ivan.x.service.IFollowService;
import xyz.ivan.x.util.Result;

@RestController
@RequestMapping("/v1")
public class FollowController {
    @Autowired
    private IFollowService followService;

    @PostMapping("/follow/{followeeId}")
    public Result follow(@PathVariable("followeeId") Long followeeId){
        return followService.follow(followeeId);
    }

    @PostMapping("/unfollow/{followeeId}")
    public Result unfollow(@PathVariable("followeeId") Long followeeId){
        return followService.unfollow(followeeId);
    }


    @GetMapping("/followers")
    public Result getFollowersById(){
        return followService.getFollowerList();
    }

    @GetMapping("/followees")
    public Result getFolloweesById(){
        return followService.getFolloweeList();
    }
}
