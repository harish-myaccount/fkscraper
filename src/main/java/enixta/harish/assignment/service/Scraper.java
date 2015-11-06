package enixta.harish.assignment.service;

import java.io.IOException;
import java.util.List;


public interface Scraper {
	String loadProductsIntoDB(String category) throws IOException;
}
