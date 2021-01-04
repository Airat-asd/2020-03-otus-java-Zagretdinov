package ru.otus.cache;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();
    private List<WeakReference<HwListener<K, V>>> listOfListener = new ArrayList<>();

    @Override
    public void put(@NotNull K key, V value) {
        cache.put(key, value);
        listOfListener.forEach(listener -> listener.get().notify(key, value, "put"));
    }

    @Override
    public void remove(K key) {
        V remove = cache.remove(key);
        listOfListener.forEach(listener -> listener.get().notify(key, remove, "remove"));
    }

    @Override
    public V get(@NotNull K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listOfListener.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listOfListener
                .forEach(element -> {
                    if (element.get().equals(listener)) {
                        listOfListener.remove(element);
                    }
                });
    }
}
