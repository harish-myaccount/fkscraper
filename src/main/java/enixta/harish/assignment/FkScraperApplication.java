package enixta.harish.assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import enixta.harish.assignment.service.ScraperFactory;
import lombok.extern.log4j.Log4j;

@EnableJpaRepositories
@SpringBootApplication
@Log4j
@Controller
public class FkScraperApplication {

	ConfigurableApplicationContext ctx;
	
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(FkScraperApplication.class, args);
        scraperfactory = ctx.getBean(ScraperFactory.class);
        readCommand();
    }
    
    private static void readCommand() {
    	System.out.print("Enter Category  : ");
    	while(true){
			  String inputString = null;
			  try
			  {	  
				  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			        inputString = bufferedReader.readLine();
			        runScraper(inputString);
			  } catch (Exception ex)
			  {
			     ex.printStackTrace();
			  }
		}
	}

	
	private static ScraperFactory scraperfactory;
	
    
	public static void runScraper(String category) throws IOException {
    	log.debug("Requested products for "+category);
    	scraperfactory.getFlipkartScraper().loadProductsIntoDB(category); 
	}

    
}
