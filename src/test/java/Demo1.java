import org.junit.Test;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;


/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
public class Demo1 {
    public static void main(String[] args) {
        LocalDate mon = LocalDate.now().minusDays(7).with(ChronoField.DAY_OF_WEEK, 1);
        System.out.println("mon = " + mon);
        System.out.println("mon.plusDays(6) = " + mon.plusDays(6));

    }

    @Test
    public void demo_1() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime of = LocalDateTime.of(2020, 11, 6, 0, 0, 0);
        long days = Duration.between(of, now).toDays();
        System.out.println("days = " + days);

        System.out.println("\"今天是星期几\"+now.getDayOfWeek() = " + "今天是星期几:" + now.getDayOfWeek());
        long between = ChronoUnit.DAYS.between(of, now);
        System.out.println("between = " + between);
        LocalDate nowDate = LocalDate.now();
        LocalDate age = nowDate.minusDays(44140);
        System.out.println("age = " + age);

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String format = decimalFormat.format(((float) 1 / 3) * 100);
        System.out.println("format = " + format);
    }


}
