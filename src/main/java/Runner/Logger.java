package Runner;

import Entity.Port;
import Entity.Ship;
import logic.CommandPort;
import logic.Loader;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
/**
 * @version 1.0 02 декабря 2018 г.
 * @author Наташа Яценя
 * Утилита, выполняющая логирование в консоль. Должна быть потокобезопасной.
 */

public class Logger {
    private static final Port PORT = Port.getInstance();
    private static final Semaphore SEMAPHORE = new Semaphore(Port.getDockCount(), true);
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static CommandPort portManager = new CommandPort(SEMAPHORE, PORT);

    public static void main(String[] args) {
        List<Callable<Integer>> callable = new ArrayList<Callable<Integer>>();
        for (int i = 0; i<15; i++) {
            callable.add(new Ship((int) (Math.random()*20+2), true, portManager));
        }
        for (int i = 0; i<15; i++) {
            callable.add(new Ship((int) (Math.random()*20+2), false, portManager));
        }
        try {
            Loader storageManager = new Loader(PORT);
            storageManager.setDaemon(true);
            storageManager.start();
            executorService.invokeAll(callable);
            executorService.shutdown();
        } catch (InterruptedException e) {
            System.out.println("error");
        }
    }
}
