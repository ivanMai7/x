package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ivan.x.service.ILikeService;
import xyz.ivan.x.util.Result;

@RestController
@RequestMapping("/v1")
public class LikeController {
    @Autowired
    private ILikeService likeService;
    @GetMapping("/likes/{tweetId}")
    public Result getTweetLikes(@PathVariable("tweetId") Long tweetId){
        Long likesCount = likeService.query().eq("tweet_id", tweetId).count();
        return Result.ok(likesCount);
    }
}
