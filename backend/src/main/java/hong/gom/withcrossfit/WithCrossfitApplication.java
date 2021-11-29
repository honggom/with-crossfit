package hong.gom.withcrossfit;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.repository.BoxRepository;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class WithCrossfitApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(WithCrossfitApplication.class, args);
	}

}
