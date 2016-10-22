package infopublica.webservices;

import infopublica.loader.InfopublicaFeedsLoader;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

@WebService(serviceName="LoaderService")
public class LoaderService {
	
	@Autowired
	private InfopublicaFeedsLoader feedsLoader;
	
	@WebMethod
	public void loadTheFeeds() {
		feedsLoader.load();
	}

}
