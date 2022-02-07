package es.miguelgsi.springboottestingprimer;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

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
}
