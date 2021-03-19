import static java.lang.Thread.sleep;

public class TestClass implements Runnable{
    @Override
    public void run()  {
        try {
            sleep(1000);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    }


