package es.miguelgsi.springboottestingprimer;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * With "webEnvironment" parameter, the Spring test open a real random port. The default value is mock.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class WebEnvironmentRandomPortTest {

    @LocalServerPort
    private Integer port;

    @Test
    void using_webEnvironment_RANDOM_PORT_a_random_port_is_open_for_this_test() {
        System.out.println(String.format("The local port is %d", port));
        assertNotNull(port); // JUnit assertions library
        MatcherAssert.assertThat(port, Matchers.notNullValue()); // Hamcrest assertions library
        Assertions.assertThat(port).isNotNull(); // AssertJ assertions library
    }
}
