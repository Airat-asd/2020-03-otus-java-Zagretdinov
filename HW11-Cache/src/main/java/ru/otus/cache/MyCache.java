package ru.otus.cache;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.SoftReference;
import java.util.*;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();
    private List<SoftReference<HwListener<K, V>>> listOfListener = new ArrayList<>();

    @Override
    public void put(@NotNull K key, V value) {
        cache.put(key, value);
        listOfListener.forEach(listeners -> {
            HwListener<K, V> listener = listeners.get();
            if (listener != null) {
                listener.notify(key, value, "put");
            }
        });
    }

    @Override
    public void remove(K key) {
        V remove = cache.remove(key);
        if (remove != null) {
            listOfListener.forEach(listeners -> {
                HwListener<K, V> listener = listeners.get();
                if (listener != null) {
                    listener.notify(key, remove, "remove");
                }
            });
        }
    }

    @Override
    public V get(@NotNull K key) {
        V value = cache.get(key);
        if (value != null) {
            listOfListener.forEach(listeners -> {
                HwListener<K, V> listener = listeners.get();
                if (listener != null) {
                    listener.notify(key, value, "get");
                }
            });
        }
        return value;
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
