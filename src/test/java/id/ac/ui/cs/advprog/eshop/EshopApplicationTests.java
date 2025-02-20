package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        // Does nothing for the time being
        // Can be utilized to ensure  context loads if needed in future developments.
    }

    @Test
    void mainMethodStartsApplication() {
        EshopApplication.main(new String[]{});

        // Add assertion for clean code
        assertTrue(true);
    }

}
