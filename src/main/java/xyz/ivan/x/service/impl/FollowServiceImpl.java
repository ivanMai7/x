package xyz.ivan.x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ivan.x.entity.Follow;
import xyz.ivan.x.entity.User;
import xyz.ivan.x.mapper.FollowMapper;
import xyz.ivan.x.service.IFollowService;
import xyz.ivan.x.service.IUserService;
import xyz.ivan.x.util.Result;

import java.util.List;

@Service
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
}
