package hw6;

/**
 * Created by Meth on 1/11/15.
 */
public class test {
    public static void main(String[] args) {
        int sizeEstimate = 100;
        FOUND:
        for (int i = sizeEstimate; ; i++) {
            for (int j = 2; j < Math.sqrt(i); j++) {
                if (i % j == 0) {
                    System.out.println(i + "%" + j);
                    continue FOUND;
                }
            }
            System.out.println(i);
            break;
        }
    }
}
