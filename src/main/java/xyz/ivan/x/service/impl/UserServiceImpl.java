package xyz.ivan.x.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import xyz.ivan.x.dto.UserDTO;
import xyz.ivan.x.entity.User;
import xyz.ivan.x.mapper.UserMapper;
import xyz.ivan.x.service.IUserService;
import xyz.ivan.x.util.RedisConstants;
import xyz.ivan.x.util.Result;
import xyz.ivan.x.util.SHA256Utils;
import xyz.ivan.x.util.XUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phoneNumber) {

        if(!XUtil.isValidPhoneNumber(phoneNumber)){
            return Result.fail(400,"手机号格式错误！");
        }

        String code = RandomUtil.randomNumbers(6);

        // 模拟发送验证码
        log.info("{} 's code is {}", phoneNumber, code);

        stringRedisTemplate.opsForValue().set(RedisConstants.USER_CODE + phoneNumber, code, RedisConstants.CODE_DURATION, TimeUnit.MINUTES);

        return Result.ok();
    }

    @Override
    public Result login(String phoneNumber, String code) {

        if(!XUtil.isValidPhoneNumber(phoneNumber)){
            return Result.fail(400,"手机号格式错误！");
        }

        String redisCode = stringRedisTemplate.opsForValue().get(RedisConstants.USER_CODE + phoneNumber);
        if (redisCode == null || StrUtil.isBlank(redisCode)) {
            return Result.fail(400, "验证码过期！");
        }
        if(!code.equals(redisCode)){
            return Result.fail(400, "验证码错误！");
        }

        User user = query().eq("phone", phoneNumber).one();
        if (user == null) {
            user = createUserByPhone(phoneNumber);
            save(user);
        }

        // 如果用户已经登录过，并且其token还未过期，则发送还未过期的token
        String loginUserKey = getLoginUserKey(RedisConstants.TOKEN_PREFIX + user.getId() + ":" + "*");
        if(StrUtil.isNotBlank(loginUserKey)){
            log.info("login key is : " + loginUserKey);
            String redisToken = loginUserKey.split(":")[2];
            // 返回给用户的token拼接了用户id
            return Result.ok(redisToken + ":" + user.getId());
        }

        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);

        String token = RandomUtil.randomString(20);

        Map<String, Object> map = userDTO2Map(userDTO);
        String tokenKey = RedisConstants.TOKEN_PREFIX + user.getId() + ":" + token;

        stringRedisTemplate.opsForHash().putAll(tokenKey, map);
        stringRedisTemplate.expire(tokenKey, RedisConstants.TOKEN_TTL, TimeUnit.DAYS);

        // 返回给用户的token拼接了用户id
        return Result.ok(token + ":" + user.getId());
    }

    @Override
    public Result saveUser(User user) {
        // 只有手机号是必须值
        if(StrUtil.isBlank(user.getPhone())){
            return Result.fail(401,"手机号不可为空！");
        }
        if(!XUtil.isValidPhoneNumber(user.getPhone())){
            return Result.fail(401,"手机号格式错误！");
        }
        User userByPhone = query().eq("phone", user.getPhone()).one();
        if (userByPhone != null) {
            return Result.fail(401, "手机号已存在");
        }
        // 使用SHA256加密密码
        if(StrUtil.isNotBlank(user.getPassword())){
            String encryptPassword = SHA256Utils.encrypt(user.getPassword());
            user.setPassword(encryptPassword);
        }
        if (StrUtil.isBlank(user.getNickName())) {
            String nickName = RedisConstants.USER_NICKNAME_PREFIX + RandomUtil.randomString(10);
            user.setNickName(nickName);
        }
        save(user);
        return Result.ok(user.getId());
    }

    @Override
    public Result removeUserById(Long id) {
        User userById = getById(id);
        if (userById == null) {
            return Result.fail(401,"用户不存在，无法删除！");
        }
        removeById(id);
        return Result.ok();
    }

    @Override
    public Result listUsers() {
        List<User> list = query().list();
        if (CollUtil.isEmpty(list)) {
            return Result.fail(401, "用户列表为空！");
        }
        return Result.ok(list);
    }

    @Override
    public Result getUserById(Long id) {
        User user = getById(id);
        if (user == null) {
            return Result.fail(401, "用户不存在！");
        }
        return Result.ok(user);
    }

    @Override
    public Result updateUser(User user) {
        Long userId = user.getId();
        if (userId == null) {
            return Result.fail(401,"id不可以为空！");
        }
        User userById = getById(userId);
        if (userById == null) {
            return Result.fail(401,"用户不存在！");
        }
        if (StrUtil.isNotBlank(user.getPhone())) {
            if(StrUtil.isBlank(user.getPhone())){
                return Result.fail(401,"手机号不可为空！");
            }
            if(!XUtil.isValidPhoneNumber(user.getPhone())){
                return Result.fail(401,"手机号格式错误！");
            }
            User userByPhone = query().eq("phone", user.getPhone()).one();
            if (userByPhone != null) {
                return Result.fail(401, "手机号已存在");
            }
        }
        // 使用SHA256加密密码
        if(StrUtil.isNotBlank(user.getPassword())){
            String encryptPassword = SHA256Utils.encrypt(user.getPassword());
            user.setPassword(encryptPassword);
        }
        updateById(user);
        return Result.ok(user.getId());
    }

    private User createUserByPhone(String phone){
        User user = new User();
        return user.setPhone(phone).setNickName(RedisConstants.USER_NICKNAME_PREFIX + RandomUtil.randomString(10));
    }

    private Map<String,Object> userDTO2Map(UserDTO userDTO){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",userDTO.getId().toString()); //这里一定加上toString
        map.put("nickName",userDTO.getNickName());
        map.put("icon",userDTO.getIcon());
        return map;
    }

    /**
     * 获取单个匹配指定前缀的 key
     *
     * @param pattern 匹配的前缀，例如 "login:1012:*"
     * @return 第一个匹配的 key，如果没有则返回 null
     */
    public String getLoginUserKey(String pattern) {
        return stringRedisTemplate.execute((RedisCallback<String>) connection -> {
            // 使用 ScanOptions 创建匹配模式
            ScanOptions options = ScanOptions.scanOptions()
                    .match(pattern)
                    .count(1000) // 每次最多扫描 1000 个键
                    .build();

            try (Cursor<byte[]> cursor = connection.scan(options)) {
                while (cursor.hasNext()) {
                    return new String(cursor.next()); // 返回第一个匹配的 key
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
            return null; // 如果没有匹配结果，返回 null
        });
    }

}
