package infopublica.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("infopublica")
@PropertySource(value = "classpath:elasticsearch.properties")
public class InfoPublicaConfiguration {

}
