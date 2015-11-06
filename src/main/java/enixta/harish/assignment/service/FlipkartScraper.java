package enixta.harish.assignment.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironmentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import enixta.harish.assignment.dao.TelevisionRepository;
import enixta.harish.assignment.model.Television;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FlipkartScraper implements Scraper {

	@Autowired
	private ScraperFactory scraperfactory;

	@Override
	public String loadProductsIntoDB(String category) throws IOException {
		String producturl = scraperfactory.getProductUrlFor(category);
		if (producturl == null) {
			log.error("No product url for " + category);
			return "Invalid category " + category;
		} else {
			log.debug(producturl);
			getproducts(producturl, category);
		}
		return "Scraping started";
	}

	private void getproducts(final String producturl, final String category) throws IOException {
		try {
			ExecutorService asyncService = Executors.newCachedThreadPool();
			asyncService.execute(new Runnable() {

				private JsonArray asJsonArray;

				@Override
				public void run() {
					JsonParser jsonParser = new JsonParser();
					CloseableHttpResponse response;
					String json = null;
					try {
						response = scraperfactory.getFkResponse(producturl);
						json = EntityUtils.toString(response.getEntity());
					} catch (IOException e) {
						e.printStackTrace();
					}
					String nextUrl=null;
					JsonElement nextUrlElement = jsonParser.parse(json).getAsJsonObject().get("nextUrl");
					if(!nextUrlElement.isJsonNull()){
						nextUrl=nextUrlElement.getAsString();
					}
					log.debug("next >> " + nextUrl);

					asJsonArray = jsonParser.parse(json).getAsJsonObject().get("productInfoList").getAsJsonArray();
					switch (category) {
					case "televisions":
						ArrayList<Television> tvs = new ArrayList<>();
						for (int i = 0; i < asJsonArray.size(); i++) {
							Television tv = new Television();
							String productId = asJsonArray.get(i).getAsJsonObject().get("productBaseInfo")
									.getAsJsonObject().get("productIdentifier").getAsJsonObject().get("productId")
									.getAsString();
							String name = asJsonArray.get(i).getAsJsonObject().get("productBaseInfo").getAsJsonObject()
									.get("productAttributes").getAsJsonObject().get("title").getAsString();
							Integer price = asJsonArray.get(i).getAsJsonObject().get("productBaseInfo")
									.getAsJsonObject().get("productAttributes").getAsJsonObject().get("sellingPrice")
									.getAsJsonObject().get("amount")
									.getAsInt();
							String url = asJsonArray.get(i).getAsJsonObject().get("productBaseInfo")
									.getAsJsonObject().get("productAttributes").getAsJsonObject().get("productUrl")
									.getAsString();
									
							tv.setProductUrl(url);
							tv.setProductId(productId);
							tv.setModelName(name);
							tv.setPrice(price);
							if(price>0)
							tvs.add(tv);
						}
						tvRepository.save(tvs);
						break;

					default:
						break;
					}
					if(nextUrl!=null){
						try {
							getproducts(nextUrl, category);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else{
						log.debug("Scraping finished");
						System.out.print("Enter Category  : ");
					}
				}
			});

		} catch (Exception e) {
			log.error(e);
		}
	}

	@Autowired
	private TelevisionRepository tvRepository;

}
