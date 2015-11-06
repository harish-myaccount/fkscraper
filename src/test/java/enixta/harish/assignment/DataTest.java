package enixta.harish.assignment;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import enixta.harish.assignment.dao.TelevisionRepository;
import enixta.harish.assignment.model.Television;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {FkScraperApplication.class})
public class DataTest {

    @Autowired
    private TelevisionRepository tvRepository;

    @Test
    public void testSaveProduct(){
        //setup product
        Television tvOne= new Television();
        tvOne.setModelName("Test model");
        tvOne.setPrice(123);
        //save product, verify has ID value after save
        tvRepository.save(tvOne);
        assertNotNull(tvOne.getProductId()); //not null after save

        //fetch from DB
        Television fetchedProduct = tvRepository.findOne(tvOne.getProductId());

       assertNotNull(fetchedProduct);
    }
}