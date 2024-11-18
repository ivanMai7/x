package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
