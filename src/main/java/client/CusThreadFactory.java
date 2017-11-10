package client;

import java.util.concurrent.ThreadFactory;

/**
 * Created by EML on 2017/11/6.
 */
public class CusThreadFactory  implements ThreadFactory{

    String name;

    int counter;

    public CusThreadFactory(String name) {
        this.name = name;
        counter = 1;
    }

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, name+"-Thread"+counter);
        counter++;
        return thread;
    }
}
