package longhoang.uet.mobile.closm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ClosmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClosmApplication.class, args);
    }

}
