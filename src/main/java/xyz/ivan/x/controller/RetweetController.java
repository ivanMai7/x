package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.ivan.x.entity.Retweet;
import xyz.ivan.x.service.IRetweetService;
import xyz.ivan.x.util.Result;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class RetweetController {
    @Autowired
    private IRetweetService retweetService;

    @GetMapping("/retweets/{tweetId}")
    public Result getRetweets(@PathVariable("tweetId") Long tweetId) {
        List<Retweet> retweets = retweetService.query().eq("tweet_id", tweetId).list();
        return Result.ok(retweets);
    }

    @PostMapping("/retweet/{tweetId}")
    public Result retweet(@PathVariable("tweetId") Long tweetId){
        return retweetService.retweet(tweetId);
    }

    @PostMapping("/unretweet/{retweetId}")
    public Result unretweet(@PathVariable("retweetId") Long retweetId){
        return retweetService.unretweet(retweetId);
    }

    @GetMapping("/retweet/tweet/{tweetId}")
    public Result getRetweetUsers(@PathVariable("tweetId") Long tweetId){
        return retweetService.getRetweetUsers(tweetId);
    }

    @GetMapping("/retweet/user")
    public Result getRetweetTweets(){
        return retweetService.getRetweetTweets();
    }
}
