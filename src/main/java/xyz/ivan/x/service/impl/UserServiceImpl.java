package xyz.ivan.x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.ivan.x.entity.User;
import xyz.ivan.x.mapper.UserMapper;
import xyz.ivan.x.service.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
}
