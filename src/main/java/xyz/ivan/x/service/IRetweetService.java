package xyz.ivan.x.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.ivan.x.entity.Retweet;
import xyz.ivan.x.util.Result;

public interface IRetweetService extends IService<Retweet> {
    Result retweet(Long tweetId);

    Result unretweet(Long retweetId);

    Result getRetweetUsers(Long tweetId);

    Result getRetweetTweets();
}
