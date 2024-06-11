package top.tobin.component.framework;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DisplayManager {
    private final List<OnDisplayChangeListener> listeners = new ArrayList<>();

    private static final String TAG = "DisplayManager";

    private static class Holder {
        private static final DisplayManager INSTANCE = new DisplayManager();
    }

    public static DisplayManager getInstance() {
        return Holder.INSTANCE;
    }

    private DisplayManager() {

    }

    public void addOnDisplayChangeListener(OnDisplayChangeListener listener) {
        Log.i(TAG, "addOnDisplayChangeListener");
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeOnDisplayChangeListener(OnDisplayChangeListener listener) {
        Log.i(TAG, "removeOnDisplayChangeListener");
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void notifyOnDisplayChange(int displayId) {
        Log.i(TAG, "notifyOnDisplayChange:" + displayId);
        for (OnDisplayChangeListener listener : listeners) {
            listener.onDisplayChange(displayId);
        }
    }

    public void clearListener() {
        listeners.clear();
    }

    public interface OnDisplayChangeListener {
        void onDisplayChange(int displayId);
    }
}
