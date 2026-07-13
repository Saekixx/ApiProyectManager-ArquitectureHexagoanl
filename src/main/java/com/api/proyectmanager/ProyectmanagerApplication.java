package com.api.proyectmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication
@ComponentScan(
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class,
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION, 
        classes = com.api.proyectmanager.shared.domain.annotation.UseCase.class
    )
)
public class ProyectmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectmanagerApplication.class, args);
	}

}
