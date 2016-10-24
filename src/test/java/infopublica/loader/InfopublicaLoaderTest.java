package infopublica.loader;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import infopublica.configuration.SpringJavaConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringJavaConfiguration.class})
public class InfopublicaLoaderTest {

	@Autowired
	private InfopublicaFeedsLoader loader;
	@Autowired
	private ElasticSearchLoader elasticSearchLoader;

	@Test
	public void testLoad() {
		loader.load();
		assertTrue(loader.getFeedsList().size() != 0);
	}
	
	@Test
	public void testLoadElasticSearch() {
		try {
			elasticSearchLoader.loadIntoElastic();
		} catch (Exception ex) {
			System.out.println("Exception occured: " + ex.getMessage());
		}
	}

}
