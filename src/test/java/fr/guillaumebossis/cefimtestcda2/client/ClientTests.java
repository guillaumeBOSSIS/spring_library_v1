package fr.guillaumebossis.cefimtestcda2.client;

import fr.guillaumebossis.cefimtestcda2.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientTests {

    @Autowired
    private ClientService clientService;
    @Autowired
    private BookService bookService;

    @Test
    void testHelloClient() {
        assert clientService.helloClient().equals(("Hello Client !"));
    }
}
