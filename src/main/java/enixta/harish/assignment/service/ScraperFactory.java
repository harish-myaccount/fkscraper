package enixta.harish.assignment.service;

import java.io.IOException;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonParser;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ScraperFactory {

	private static final String BASE_FK_URL = "https://affiliate-api.flipkart.net/affiliate/api/";

	@Autowired
	private FlipkartScraper fkartScraper;

	@Value("${flipkart.affiliate.id}")
	private String affiliateId;

	@Value("${flipkart.affiliate.token}")
	private String affiliateToken;

	public Scraper getFlipkartScraper() {
		return fkartScraper;
	}

	public CloseableHttpResponse getFkResponse(String url) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet= addSecurityHeaders(httpGet);
		return httpclient.execute(httpGet);
	}
	
	private HttpGet addSecurityHeaders(HttpGet httpGet){
		httpGet.addHeader("Fk-Affiliate-Id", affiliateId);
		httpGet.addHeader("Fk-Affiliate-Token", affiliateToken);
		return httpGet;
	}
	
	

	public String getProductUrlFor(String category) {
		try {
			String json = EntityUtils.toString(getFkResponse(BASE_FK_URL + affiliateId + ".json").getEntity());
			JsonParser jsonParser = new JsonParser();
			return jsonParser.parse(json).getAsJsonObject().get("apiGroups").getAsJsonObject().get("affiliate")
					.getAsJsonObject().get("apiListings").getAsJsonObject().get(category).getAsJsonObject()
					.get("availableVariants").getAsJsonObject().get("v0.1.0").getAsJsonObject().get("get")
					.getAsString();
		} catch (IOException e) {
			log.error(e);
		}
		return null;
	}

}
