package xyz.ivan.x.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ivan.x.dto.UserDTO;
import xyz.ivan.x.entity.Like;
import xyz.ivan.x.entity.Tweet;
import xyz.ivan.x.entity.User;
import xyz.ivan.x.mapper.LikeMapper;
import xyz.ivan.x.service.ILikeService;
import xyz.ivan.x.service.ITweetService;
import xyz.ivan.x.service.IUserService;
import xyz.ivan.x.util.Result;
import xyz.ivan.x.util.UserHolder;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements ILikeService {

    @Autowired
    private IUserService userService;
    @Autowired
    private ITweetService tweetService;


    @Override
    public Result like(Long tweetId) {
        Long userId = UserHolder.getUser().getId();
        Long count = query().eq("tweet_id", tweetId).eq("user_id", userId).count();
        if (count > 0) {
            log.error("该用户：{}已经给该博客:{}点赞，不可重复点赞！",userId,tweetId);
            return Result.fail(401,"该用户已经给该博客点赞，不可重复点赞！");
        }
        Like like = new Like();
        like.setTweetId(tweetId);
        like.setUserId(userId);
        save(like);
        log.info("用户：{}给博客:{}点赞",userId,tweetId);
        return Result.ok();
    }

    @Override
    public Result unlike(Long tweetId) {
        Long userId = UserHolder.getUser().getId();
        Long count = query().eq("tweet_id", tweetId).eq("user_id", userId).count();
        if(count == 0){
            log.error("用户：{}未点赞tweet：{}，不可取消点赞！",userId,tweetId);
            return Result.fail(401,"用户未点赞，不可取消点赞！");
        }
        UpdateWrapper<Like> objectUpdateWrapper = new UpdateWrapper<>();
        objectUpdateWrapper.eq("tweet_id",tweetId);
        objectUpdateWrapper.eq("user_id",userId);
        remove(objectUpdateWrapper);
        log.info("用户：{}取消点赞tweet：{}",userId,tweetId);
        return Result.ok();
    }

    @Override
    public Result getLikeUsersByTweetId(Long tweetId) {
        List<User> users = query().eq("tweet_id", tweetId).list()
                .stream().mapToLong(Like::getUserId).mapToObj(userService::getById)
                .toList();
        List<UserDTO> userDTOS = BeanUtil.copyToList(users, UserDTO.class);
        log.info("查询tweet：{}的点赞用户列表",tweetId);
        return Result.ok(userDTOS);
    }

    @Override
    public Result getLikeTweetsByUser() {
        Long userId = UserHolder.getUser().getId();
        Stream<Tweet> tweets = query().eq("user_id", userId).list()
                .stream().mapToLong(Like::getTweetId)
                .mapToObj(tweetService::getById);
        log.info("查询用户：{}点赞的tweet列表",userId);
        return Result.ok(tweets);
    }
}
