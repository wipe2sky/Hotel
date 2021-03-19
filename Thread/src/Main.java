import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        try {
            Thread thread = new Thread(new TestClass());
            Thread thread2 = new Thread(new SlowThread());
            System.out.println(thread.getState());
            thread.start();
            System.out.println(thread.getState());
            thread2.start();
            sleep(500);
            System.out.println(thread.getState());
            System.out.println(thread2.getState());
            Object a = SlowThread.getA();
            synchronized (a){
                a.notify();
            }
            System.out.println(thread2.getState());
            sleep(1000);
            System.out.println(thread.getState());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
