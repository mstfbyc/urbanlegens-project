package com.urbanlegends;

import com.urbanlegends.configuration.AppConfiguration;
import com.urbanlegends.hoax.Hoax;
import com.urbanlegends.hoax.HoaxService;
import com.urbanlegends.user.User;
import com.urbanlegends.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.util.ArrayList;

@SpringBootApplication
@EnableSwagger2
public class UrbanlegensApplication {

	@Autowired
	AppConfiguration appConfiguration;

	@Autowired
	HoaxService hoaxService;

	public static void main(String[] args) {
		SpringApplication.run(UrbanlegensApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner createInitialUsers(UserService userService){
		return ( args) -> {
			for (int i = 1; i <= 30; i++) {
				User user = User.builder()
						.username("user" + i)
						.displayName("display" + i)
						.password("Password123")
						.build();
				userService.saveUser(user);
				for (int j = 1; j <= 4; j++) {
					Hoax hoax = new Hoax();
					hoax.setContent("hoax - " + j);
					hoax.setUser(user);
					hoaxService.save(hoax);
				}
			}
			};
	}

	@Bean
	CommandLineRunner createStorageDirectories(){
		return ( args) -> {
			File folder = new File(appConfiguration.getUploadPath());
			boolean folderExist = folder.exists() && folder.isDirectory();
			if(!folderExist){
				folder.mkdir();
			}
		};
	}

	@Bean
	public Docket api(){
		SecurityReference securityReference = SecurityReference.builder()
				.reference("basicAuth")
				.scopes(new AuthorizationScope[0])
				.build();
		ArrayList<SecurityReference> references = new ArrayList<>(1);
		references.add(securityReference);

		ArrayList<SecurityContext> securityContexts = new ArrayList<>(1);
		securityContexts.add(SecurityContext.builder().securityReferences(references).build());

		ArrayList<SecurityScheme> auth = new ArrayList<>(1);
		auth.add(new BasicAuth("basicAuth"));

		return new Docket(DocumentationType.SWAGGER_2)
				.securitySchemes(auth)
				.securityContexts(securityContexts)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

}
