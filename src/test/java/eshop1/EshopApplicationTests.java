package eshop1;

import com.ecommerce.admin.EshopApplication1;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EshopApplication1.class) // Replace EshopApplication with your main application class
public class EshopApplicationTests {

    @Test
    public void contextLoads() {
        // Your test code
    }
}
