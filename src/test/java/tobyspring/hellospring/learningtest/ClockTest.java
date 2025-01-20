package tobyspring.hellospring.learningtest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClockTest {

    // clock을 이용해 LocalDateTime.now?
    @Test
    void clock() {
        Clock clock = Clock.systemDefaultZone();
        // 나의 경우 완전 동일한 결과를 반환했다.
        LocalDateTime dt1 = LocalDateTime.now(clock);
        LocalDateTime dt2 = LocalDateTime.now(clock);

        // isAfterOrEqualTo : 동일하거나 이후라면 성공
        // Assertions.assertThat(dt2).isAfter(dt1);  오류 발생(시간이동일)
        Assertions.assertThat(dt2).isAfterOrEqualTo(dt1);
    }

    // Clock Test에서 사용시 원하는 시간을 지정해 현재시간을 가져오게 할 수 있는가?
    @Test
    void fixedClock() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        // 나의 경우 완전 동일한 결과를 반환했다.
        LocalDateTime dt1 = LocalDateTime.now(clock);
        LocalDateTime dt2 = LocalDateTime.now(clock);

        LocalDateTime dt3 = LocalDateTime.now(clock).plusHours(1);

        // isAfterOrEqualTo : 동일하거나 이후라면 성공
        Assertions.assertThat(dt2).isEqualTo(dt1);
        Assertions.assertThat(dt3).isEqualTo(dt1.plusHours(1));
    }
}
