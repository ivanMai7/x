package xyz.ivan.x.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.ivan.x.entity.User;
import xyz.ivan.x.util.Result;

public interface IUserService extends IService<User> {
    Result sendCode(String phoneNumber);

    Result login(String phoneNumber, String code);

    Result saveUser(User user);

    Result removeUserById(Long id);

    Result listUsers();

    Result getUserById(Long id);

    Result updateUser(User user);
}
