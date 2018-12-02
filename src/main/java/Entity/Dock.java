package Entity;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @version 1.0 02 декабря 2018 г.
 * @author Наташа Яценя
 * Береговое хранилище груза, обеспечивающее возможность загрузки и разгрузки.
 */

public class Dock {
    private AtomicBoolean isBusy = new AtomicBoolean();

    public AtomicBoolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy.set(busy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dock dock = (Dock) o;
        return isBusy.equals(dock.isBusy);
    }

    @Override
    public int hashCode() {
        return isBusy.hashCode();
    }
}