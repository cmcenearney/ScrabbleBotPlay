package scrabblebot.data;

import com.google.common.collect.ImmutableList;
import java.util.List;

public class RealList<T> {

    final ImmutableList<T> list;

    public RealList(List<T> items) {
        list = new ImmutableList.Builder<T>().addAll(items).build();
    }

    T head() {
        return list.get(0);
    }

    ImmutableList<T> tail() {
        return list.subList(1, list.size());
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public T get(int i) {
        return list.get(i);
    }

    public int size() {
        return list.size();
    }

    public RealList<T> remove(int i) {
        ImmutableList.Builder<T> b = new ImmutableList.Builder<T>();
        for (int j = 0; j < list.size(); j++) {
            if (j != i)
                b.add(list.get(j));
        }
        return new RealList<T>(b.build());
    }

    public RealList<T> replace(int i, T item) {
        ImmutableList.Builder<T> b = new ImmutableList.Builder<T>();
        for (int j = 0; j < list.size(); j++) {
            if (j == i) {
                b.add(item);
            } else {
                b.add(list.get(j));
            }
        }
        return new RealList<T>(b.build());
    }

}
