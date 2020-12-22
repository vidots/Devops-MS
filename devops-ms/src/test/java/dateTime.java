import java.text.SimpleDateFormat;
import java.util.Date;

public class dateTime {
    public static void main(String[] args) {
        SimpleDateFormat format_1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format_2 = new SimpleDateFormat("HH:mm:ss");
        System.out.println(format_1.format(new Date()));
        System.out.println(format_2.format(new Date()));
    }
}
