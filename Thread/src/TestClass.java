import static java.lang.Thread.sleep;

public class TestClass implements Runnable {
    private static Object a = new Object();

    public static Object getA() {
        return a;
    }

    @Override
    public void run() {
        try {
            sleep(50);
            someMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void someMethod() {
        synchronized (a) {
            try {
                a.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



