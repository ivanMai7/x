package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("likes/likeUsers")
    public Result getUserLikes(){
        return likeService.getLikeTweetsByUser();
    }

    @GetMapping("/likes/likeTweets/{tweetId}")
    public Result getLikeUsersByTweetId(@PathVariable("tweetId") Long tweetId){
        return likeService.getLikeUsersByTweetId(tweetId);
    }


    @PostMapping("/like/{tweetId}")
    public Result likeTweet(@PathVariable("tweetId") Long tweetId){
        return likeService.like(tweetId);
    }

    @PostMapping("/unlike/{tweetId}")
    public Result unlikeTweet(@PathVariable("tweetId") Long tweetId){
        return likeService.unlike(tweetId);
    }

}
