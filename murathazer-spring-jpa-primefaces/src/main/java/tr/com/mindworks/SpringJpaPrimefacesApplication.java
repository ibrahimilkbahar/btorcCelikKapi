package tr.com.mindworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"tr.com.mindworks"})
public class SpringJpaPrimefacesApplication extends SpringBootServletInitializer
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringJpaPrimefacesApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(SpringJpaPrimefacesApplication.class);
	}
}
