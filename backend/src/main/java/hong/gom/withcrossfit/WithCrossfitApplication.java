package hong.gom.withcrossfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Auditing 활성화 
public class WithCrossfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(WithCrossfitApplication.class, args);
	}

}
