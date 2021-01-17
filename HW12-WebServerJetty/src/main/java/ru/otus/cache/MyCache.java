package ru.otus.cache;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.SoftReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<SoftReference<HwListener<K, V>>> listOfListener = new ArrayList<>();

    @Override
    public void put(@NotNull K key, V value) {
        cache.put(key, value);
        forEachListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V remove = cache.remove(key);
        if (remove != null) {
            forEachListeners(key, remove, "remove");
        }
    }

    @Override
    public V get(@NotNull K key) {
        V value = cache.get(key);
        if (value != null) {
            forEachListeners(key, value, "get");
        }
        return value;
    }

    private void forEachListeners(@NotNull K key, V value, String action) {
        listOfListener.forEach(listeners -> {
            HwListener<K, V> listener = listeners.get();
            if (listener != null) {
                try {
                    listener.notify(key, value, action);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                listOfListener.remove(listeners);
            }
        });
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listOfListener.add(new SoftReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listOfListener
                .forEach(element -> {
                    var elementOfListOfListener = element.get();
                    if (elementOfListOfListener != null) {
                        if (elementOfListOfListener.equals(listener)) {
                            listOfListener.remove(element);
                        } else {
                            throw new NoSuchElementException("The listener to be removed is null");
                        }
                    }
                });
    }
}
