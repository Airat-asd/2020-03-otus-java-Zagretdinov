import java.util.*;
import java.util.function.UnaryOperator;

public class DIYArrayList<T> implements List<T> {
    private static final Object[] EMPTY_ITEM = {};
    private Object[] elements;
    private int size;

    //конструкторы сделаны по аналогии с arrayList
    public DIYArrayList(int initialSize) {
        size = 0;
        if (initialSize > 0) {
            elements = new Object[initialSize];
        } else if (initialSize == 0) {
            elements = EMPTY_ITEM;
        } else {
            throw new IllegalArgumentException("Invalid DIYArrayList size: " +
                    initialSize);
        }
    }

    public DIYArrayList() {
        elements = EMPTY_ITEM;
    }

    public DIYArrayList(Collection<? extends T> source) {
        elements = source.toArray();
        if ((size = elements.length) != 0) {
            if (elements.getClass() != Object[].class)
                elements = Arrays.copyOf(elements, size, Object[].class);
        } else {
            elements = EMPTY_ITEM;
        }
    }

    //возвращаем количество элементов списка
    @Override
    public int size() {
        return this.size;
    }

    //конвертируем список в массив
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    //вывод списка в строку
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        for (int i = 0; i < size; i++) {
            buffer.append(" " + elements[i]);
            if (i < size - 1) buffer.append(",");
            else buffer.append(" ]");
        }
        return buffer.toString();
    }

    //сортировка списка
    @Override
    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[]) elements, 0, size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    //добавление элемента в список, возвращаем true
    @Override
    public boolean add(T t) {
        if (size == elements.length) {
            elements = grow(size + 1);
        }
        elements[size] = t;
        size++;
        return true;
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    //возвращаем увеличенный в 1,5 раза список
    private Object[] grow(int size) {
        int oldCapacity = elements.length;
        if (oldCapacity > 0 || elements != EMPTY_ITEM) {
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            return elements = Arrays.copyOf(elements, newCapacity);
        } else {
            return elements = new Object[size];
        }
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    //возвращаем элемент списка по индексу
    @Override
    public T get(int index) {
        return (T) elements[index];
    }

    //заменяем элемент в списке по индексу, возвращая старый элемент
    @Override
    public T set(int index, T element) {
        T oldValue = (T) elements[index];
        elements[index] = element;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public Spliterator<T> spliterator() {
        throw new UnsupportedOperationException("Unsupported method");
    }

    //возвращаем количество ячеек массива
    public int capacity() {
        return elements.length;
    }

}
