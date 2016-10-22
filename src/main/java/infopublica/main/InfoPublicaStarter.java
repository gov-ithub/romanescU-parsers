package infopublica.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import infopublica.configuration.InfoPublicaConfiguration;
import infopublica.loader.ElasticSearchLoader;

@Component
public class InfoPublicaStarter {
	
	private static ApplicationContext ctx;

	public static void main(String[] args) {
		ctx = new AnnotationConfigApplicationContext(InfoPublicaConfiguration.class);
		ElasticSearchLoader elasticSearchLoader = ctx.getBean(ElasticSearchLoader.class);
		elasticSearchLoader.loadIntoElastic();
	}

}
