import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        try {
            Thread thread = new Thread(new TestClass());
            Thread thread2 = new Thread(new TestClass());
            System.out.println(thread.getState());
            thread.start();
            System.out.println(thread.getState());
            sleep(60);
            System.out.println(thread.getState());
            thread2.start();
            sleep(30);
            System.out.println(thread2.getState());
            Object a = TestClass.getA();
            synchronized (a){
                a.notify();
            }
            System.out.println(thread.getState());
            sleep(50);
            System.out.println(thread.getState());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
