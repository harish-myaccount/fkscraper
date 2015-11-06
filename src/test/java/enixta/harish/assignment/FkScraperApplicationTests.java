package enixta.harish.assignment;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import enixta.harish.assignment.service.ScraperFactory;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FkScraperApplication.class)
@WebAppConfiguration
@Log4j
public class FkScraperApplicationTests {

	@Autowired
	private ScraperFactory factory;

	@Test
	public void httpClientTest() {
		try {
			CloseableHttpResponse response = factory
					.getFkResponse("https://affiliate-api.flipkart.net/affiliate/api/harishmye.json");
			Assert.assertNotNull(response.getEntity());
			log.info(EntityUtils.toString(response.getEntity()));
		    EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
			log.error(e);
			Assert.assertTrue(false);
		}
	}

}
