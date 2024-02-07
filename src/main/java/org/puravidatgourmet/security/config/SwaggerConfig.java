package org.puravidatgourmet.security.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(getApiInfo());                                           
    }
    
    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Pura Vida Gourmet - Security Module",
                "API de Security Module",
                "0.1",
                "pending",
                new Contact("Daniel Munoz","none","dmunoz.hon@gmail.com"),
                "GNU",
                "Google GNU",
                Collections.emptyList()
        );
    }
}
