package es.miguelgsi.springboottestingprimer.infrastructure.adapters.in.rest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * With "webEnvironment" parameter, the Spring test open a real random port. The default value is mock.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class WebEnvironmentRandomPortTestIT {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate testRestTemplate; // to access your application on the random port

    @Test
    void using_webEnvironment_RANDOM_PORT_a_random_port_is_open_for_this_test() {
        System.out.println(String.format("The local port is %d", port));
        assertNotNull(port); // JUnit assertions library
        MatcherAssert.assertThat(port, Matchers.notNullValue()); // Hamcrest assertions library
        assertThat(port).isNotNull(); // AssertJ assertions library
    }

    @Test
    void call_actuator_endpoint_should_response_with_status_200() {
        ResponseEntity response = testRestTemplate.withBasicAuth("user1", "user1")
                .getForEntity("/actuator/health", Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
