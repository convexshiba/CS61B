
/**
 * Created by Meth on 1/3/15.
 */
public class testtest {
    public static void main(String[] args) {
        for (int n = 10; ; n++) {
            long time1 = System.currentTimeMillis();
            f(n);
            System.out.println("f(" + n + ") ~ " + (System.currentTimeMillis() - time1) + " ms");
        }
    }

    static int f(int x) {
        if (x < 1) {
            return 1;
        } else {
            return f(x - 1) + g(x);
        }
    }

    static int g(int x) {
        if (x < 2) {
            return 2;
        } else {
            return f(x - 1) + g(x / 2);
        }
    }

    static int g1(int x) {
        if (x < 2) {
            return 2;
        } else {
            return f(x - 1) + f(x / 2) - f(x / 2 - 1);
        }
    }

    static int f1(int x) {
        if (x < 1) {
            return 1;
        } else {
            return 2 * f(x - 1) + f(x / 2) - f(x / 2 - 1);
        }
    }
}