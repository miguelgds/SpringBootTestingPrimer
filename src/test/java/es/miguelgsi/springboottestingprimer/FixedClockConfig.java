package es.miguelgsi.springboottestingprimer;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class FixedClockConfig {

    private static Instant NOW = Instant.parse("2018-08-19T16:02:42.00Z");
    private static Clock CLOCK = Clock.fixed(NOW, ZoneId.systemDefault());

    @Bean
    public Clock clock() {
        return CLOCK;
    }
}
