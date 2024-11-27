package xyz.ivan.x.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ivan.x.dto.UserDTO;
import xyz.ivan.x.entity.Retweet;
import xyz.ivan.x.entity.Tweet;
import xyz.ivan.x.entity.User;
import xyz.ivan.x.mapper.RetweetMapper;
import xyz.ivan.x.service.IRetweetService;
import xyz.ivan.x.service.ITweetService;
import xyz.ivan.x.service.IUserService;
import xyz.ivan.x.util.Result;
import xyz.ivan.x.util.UserHolder;

import java.util.List;

@Service
@Slf4j
public class RetweetServiceImpl extends ServiceImpl<RetweetMapper, Retweet> implements IRetweetService {
    @Autowired
    private ITweetService tweetService;
    @Autowired
    private IUserService userService;
    @Override
    public Result retweet(Long tweetId) {
        Long userId = UserHolder.getUser().getId();
        Tweet tweet = tweetService.getById(tweetId);
        if (tweet == null) {
            log.error("该推特：{}不存在，无法转发",tweetId);
            return Result.fail(401,"该推特不存在，无法转发");
        }
        if (tweet.getUserId().equals(userId)) {
            log.error("该推特:{}是同一个用户:{}的，无法转发",tweetId,userId);
            return Result.fail(401,"该推特是同一个用户的，无法转发");
        }
        if (query().eq("tweet_id",tweetId).eq("user_id",userId).count() != 0) {
            log.error("该推特:{}已经被用户:{}转发，无法再次转发",tweetId,userId);
            return Result.fail(401,"该推特已经被转发，无法再次转发");
        }
        Retweet retweet = new Retweet();
        retweet.setTweetId(tweetId);
        retweet.setUserId(userId);
        save(retweet);
        log.info("用户：{}转发tweet：{}成功！",userId,tweetId);
        return Result.ok(retweet.getId());
    }

    @Override
    public Result unretweet(Long retweetId) {
        Retweet retweet = getById(retweetId);
        if (retweet == null) {
            log.error("该转发:{}不存在！",retweetId);
            return Result.fail(401,"该转发不存在");
        }
        removeById(retweetId);
        log.info("取消转发:{}成功！",retweetId);
        return Result.ok();
    }

    @Override
    public Result getRetweetUsers(Long tweetId) {
        List<User> users = query().eq("tweet_id", tweetId).list().stream()
                .mapToLong(Retweet::getUserId).mapToObj(userService::getById).toList();
        List<UserDTO> userDTOS = BeanUtil.copyToList(users, UserDTO.class);
        log.info("查询retweet：{}的用户列表",tweetId);
        return Result.ok(userDTOS);
    }

    @Override
    public Result getRetweetTweets() {
        Long userId = UserHolder.getUser().getId();
        List<Tweet> tweets = query().eq("user_id", userId).list().stream()
                .mapToLong(Retweet::getTweetId).mapToObj(tweetService::getById).toList();
        log.info("查询用户：{}的retweet列表",userId);
        return Result.ok(tweets);
    }
}
