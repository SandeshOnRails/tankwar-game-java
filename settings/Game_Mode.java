package settings;

import java.awt.event.KeyEvent;
import java.util.Observable;

public class Game_Mode extends Observable {

    public int typeEvent;
    public Object eventID;

    public void setValue(KeyEvent key, int keyType) {
        typeEvent = keyType;
        eventID = key;
        setChanged();
        notifyObservers(this);
    }

    public void setValue(String message) {
        typeEvent = 2;
        eventID = message;
        setChanged();
        notifyObservers(this);
    }

    public void setValue(int time) {
        typeEvent = 3;
        eventID = time;
        setChanged();
        notifyObservers(this);
    }

}
