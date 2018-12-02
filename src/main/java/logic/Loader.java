package logic;

import Entity.Port;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0 02 декабря 2018 г.
 * @author Наташа Яценя
 * Поток-демон. Выполняет загрузку/разгрузку корабля (порта) после соединения корабля с портом.
 */

public class Loader extends Thread {
    private ReentrantLock lock = new ReentrantLock();
    private Port port;


    public Loader (Port port) {
        this.port = port;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (port.getStorage().get()<(Port.getStorageCapacity()/2+1)) {
                //если на складе мало товара, добавляем
                port.setStorage((Port.getStorageCapacity()/10*9));
            }
            lock.unlock();
            lock.lock();
            if (port.getStorage().get()>(Port.getStorageCapacity()/5)*4) {
                //а если много, то разгружаем склад
                port.setStorage(Port.getStorageCapacity()/10);
            }
            lock.unlock();
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("demon error");
            }
        }
    }
}