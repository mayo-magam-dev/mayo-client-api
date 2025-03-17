package com.mayo.client.mayoclientapi.common.config;

import com.mayo.client.mayoclientapi.common.interceptor.AuthenticationInterceptor;
import com.mayo.client.mayoclientapi.common.interceptor.LogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final LogInterceptor logInterceptor;
    private final FirebaseInitializer.FirestoreConnectionInterceptor firestoreConnectionInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**",
                        "/css/**",
                        "/js/**")
                .addResourceLocations("classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(firestoreConnectionInterceptor)
                .addPathPatterns("/**");
    }

}
