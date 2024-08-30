package fun.quzhi.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.TimeZone;

@SpringBootApplication
@MapperScan("fun.quzhi.shop.model.dao")
@EnableCaching
public class ShopApplication {

	public static void main(String[] args) {
		System.out.println(TimeZone.getDefault());
		SpringApplication.run(ShopApplication.class, args);
	}

}
