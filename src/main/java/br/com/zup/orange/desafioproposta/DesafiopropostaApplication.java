package br.com.zup.orange.desafioproposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class DesafiopropostaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafiopropostaApplication.class, args);
    }

}
