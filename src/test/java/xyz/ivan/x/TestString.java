package xyz.ivan.x;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class TestString {
    private String str = "";
    @Test
    public void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                str = str + "a";
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                str = str + "a";
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.info("str len is:{}",str.length());
    }
}
