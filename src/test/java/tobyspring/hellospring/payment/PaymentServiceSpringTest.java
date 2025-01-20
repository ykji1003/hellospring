package tobyspring.hellospring.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestObjectFactory;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)  // 스프링의 기능을 이용하기 위해 확장해달라.
@ContextConfiguration(classes = TestObjectFactory.class)
class PaymentServiceSpringTest {

    @Autowired PaymentService paymentService;
    @Autowired ExRateProviderStub exRateProviderStub;


    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증한다.")
    void convertedAmount() throws IOException {
        // exRate : 1000
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);


        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        // exRate : 500
        exRateProviderStub.setExRate(valueOf(500));
        Payment payment2= paymentService.prepare(1L, "USD", BigDecimal.TEN);
        assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(500));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));


        // 원화 환산금액 유효시간.
        // testAmount(payment.getValidUntil()).isAfter(LocalDateTime.now());
        // testAmount(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));

    }

    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {

    }
}