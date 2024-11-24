package xyz.ivan.x.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ivan.x.dto.UserDTO;
import xyz.ivan.x.entity.Follow;
import xyz.ivan.x.entity.User;
import xyz.ivan.x.mapper.FollowMapper;
import xyz.ivan.x.service.IFollowService;
import xyz.ivan.x.service.IUserService;
import xyz.ivan.x.util.Result;
import xyz.ivan.x.util.UserHolder;

import java.util.List;

@Service
@Slf4j
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private IUserService userService;
    @Override
    public Result getFolloweeListByFollowerId(Long followerId) {
        List<Follow> followees = query().eq("follower_id", followerId).list();
        if(followees == null || followees.isEmpty()){
            return Result.ok("该用户的关注列表为空！");
        }
        List<User> users = followees.stream().map(Follow::getFolloweeId).map(userService::getById).toList();
        return Result.ok(users);
    }

    @Override
    public Result follow(Long followeeId) {
        if( userService.getById(followeeId) == null ){
            log.error("关注的用户：{}不存在",followeeId);
            return Result.fail(401,"该用户不存在！");
        }
        Long followerId = UserHolder.getUser().getId();
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFolloweeId(followeeId);
        save(follow);
        log.info("用户：{}关注了：{}",followerId,followeeId);
        return Result.ok();
    }

    @Override
    public Result unfollow(Long followeeId) {
        Long followerId = UserHolder.getUser().getId();
        remove(new QueryWrapper<Follow>().eq("follower_id",followerId).eq("followee_id",followeeId));
        log.info("用户：{}取关了：{}",followerId,followeeId);
        return Result.ok();
    }

    @Override
    public Result getFollowerList() {
        Long followeeId = UserHolder.getUser().getId();
        List<Follow> followers = query().eq("followee_id", followeeId).list();
        log.info("用户：{}查询自己的粉丝列表",followeeId);
        return Result.ok(followers);
    }

    @Override
    public Result getFolloweeList() {
        Long followerId = UserHolder.getUser().getId();
        List<Follow> followees = query().eq("follower_id", followerId).list();
        log.info("用户：{}查询自己的关注列表",followerId);
        return Result.ok(followees);
    }
}
