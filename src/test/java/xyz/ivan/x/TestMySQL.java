package xyz.ivan.x;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xyz.ivan.x.entity.User;
import xyz.ivan.x.service.IUserService;

import java.util.*;

@SpringBootTest
@Slf4j
public class TestMySQL {

    @Autowired
    private IUserService userService;

    /**
     * 插入1000,000条用户
     */
    @Test
    public void insertUsers(){
        long start = System.currentTimeMillis();
        Set<String> set = new HashSet<>();
        set.add("15934381427");
        set.add("13467032840");
        set.add("13952611025");
        set.add("18335567508");
        set.add("15512562563");
        for (int i = 0; i < 1000; i++) {
            Set<String> phones = PhoneNumberGenerator.generatePhoneNumbers(1000);
            List<User> users = new ArrayList<>();
            for (String phone : phones) {
                if (set.contains(phone)) {
                    continue;
                }
                String name = "user_" + RandomUtil.randomString(10);
                User user = new User();
                user.setNickName(name);
                user.setPhone(phone);
                users.add(user);
                set.add(phone);
            }
            userService.saveBatch(users);
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) / 1000);
    }

    /**
     * 查询所有1000_000条用户数据耗时:5秒多
     * 在数据库中查询耗时：3秒多
     */
    @Test
    public void testQuery1000_000Users(){
        long start = System.currentTimeMillis();
        List<User> list = userService.query().list();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}

class PhoneNumberGenerator {

    // 定义手机号前缀
    private static final String[] PREFIXES = {
            "13", "14", "15", "16", "17", "18", "19"
    };

    public static void main(String[] args) {
        int count = 1000000;  // 生成手机号的数量
        Set<String> phoneNumbers = generatePhoneNumbers(count);

        // 输出生成的手机号个数
        System.out.println("生成的手机号数量: " + phoneNumbers.size());
        // 可以根据需求，输出一部分手机号或者保存到文件
        // 输出前10个手机号作为示例
        phoneNumbers.stream().limit(10).forEach(System.out::println);
    }

    public static Set<String> generatePhoneNumbers(int count) {
        Set<String> phoneNumbers = new HashSet<>();
        Random random = new Random();

        while (phoneNumbers.size() < count) {
            // 随机选择一个前缀
            String prefix = PREFIXES[random.nextInt(PREFIXES.length)];
            // 生成剩余9位数字
            StringBuilder suffix = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                suffix.append(random.nextInt(10));  // 随机生成0-9的数字
            }
            // 合成完整的手机号
            String phoneNumber = prefix + suffix.toString();
            phoneNumbers.add(phoneNumber);  // 使用Set保证唯一性
        }

        return phoneNumbers;
    }
}
