package zjh;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhongjinhui
 */
@EnableDubbo
@SpringBootApplication
public class accountApplication {

    public static void main(String[] args) {
        SpringApplication.run(accountApplication.class, args);
    }

}
