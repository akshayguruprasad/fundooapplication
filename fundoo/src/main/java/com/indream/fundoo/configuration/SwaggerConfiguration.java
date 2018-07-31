package com.indream.fundoo.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * CONFIGURATION OF THE SWAGGER
 * 
 * @author Akshay
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /*
     * @purpose CREATION OF THE DOCKET BEAN FOR READING THE RESTCONTROLLER
     * IMPLEMENTAIONS
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Bean
    public Docket api() {

	return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.any()).build().securitySchemes(Collections.singletonList(apiKey()));

    }

    /*
     * @purpose API KEY VALUE PROVIDED FOR THE SWAGGER UI - MANAGEMENT
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    private ApiKey apiKey() {
	return new ApiKey("authorization", "authorization", "header");
    }

    /*
     * private SecurityContext securityContext() { return
     * SecurityContext.builder().securityReferences(defaultAuth()).forPaths(
     * PathSelectors.any()).build(); }
     */

    /*
     * private List<SecurityReference> defaultAuth() { AuthorizationScope
     * authorizationScope = new AuthorizationScope("global", "accessNothing");
     * AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
     * authorizationScopes[0] = authorizationScope; return Lists.newArrayList(new
     * SecurityReference("authorization", authorizationScopes)); }
     */

}
