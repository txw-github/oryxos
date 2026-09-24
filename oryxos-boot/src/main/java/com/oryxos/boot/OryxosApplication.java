package com.oryxos.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OryxOS Spring Boot 启动模块。
 *
 * <p>打成单二进制 fat JAR：{@code mvn clean package} 后
 * {@code java -jar oryxos-boot/target/oryxos.jar ...} 启动。</p>
 */
@SpringBootApplication
public class OryxosApplication {

    public static void main(String[] args) {
        SpringApplication.run(OryxosApplication.class, args);
    }
}
