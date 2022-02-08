package es.miguelgsi.springboottestingprimer;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class JsonUtilsTest {

    @Autowired
    private JacksonTester<PaymentResponse> jacksonTester;

    @Test
    void json_assert_not_strict_validate_if_contains() throws JSONException {
        String actual = "{\"name\": \"duke\", \"age\":\"42\"}";
        String expected = "{\"name\": \"duke\"}";
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    void json_assert_strict_validate_if_equals() throws JSONException {
        String actual = "{\"name\": \"duke\", \"age\":\"42\"}";
        String expected = "{\"name\": \"duke\"}";
        JSONAssert.assertNotEquals(expected, actual, true);
    }

    @Test
    void jsonPathExample() {
        String result = "{\"age\":\"42\", \"name\": \"duke\", \"tags\":[\"java\", \"jdk\"]}";

        assertEquals(2, JsonPath.parse(result).read("$.tags.length()", Long.class));
        assertEquals("duke", JsonPath.parse(result).read("$.name", String.class));
    }

    @Test
    public void serialize_payment_response_should_return_the_expected_json() throws IOException {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId("42");
        paymentResponse.setAmount(new BigDecimal("42.50"));
        paymentResponse.setPaymentConfirmationCode(UUID.randomUUID());
        paymentResponse.setPaymentTime(LocalDateTime.parse("2020-07-20T19:00:00.123"));

        JsonContent<PaymentResponse> result = jacksonTester.write(paymentResponse);

        assertThat(result).hasJsonPathStringValue("$.paymentConfirmationCode");
        assertThat(result).extractingJsonPathNumberValue("$.payment_amount").isEqualTo(42.50);
        assertThat(result).extractingJsonPathStringValue("$.paymentTime").isEqualTo("2020-07-20|19:00:00");
        assertThat(result).doesNotHaveJsonPath("$.id");
    }
}
