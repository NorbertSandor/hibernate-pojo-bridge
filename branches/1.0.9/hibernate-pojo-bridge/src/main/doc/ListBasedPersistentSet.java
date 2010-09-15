

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.erinors.hpb.client.impl.PersistentSet;

public class ListBasedPersistentSet<E> implements PersistentSet<E> {

    private boolean dirty;

    private ArrayList<E> list = new ArrayList<E>();

    public ListBasedPersistentSet() {
    }

    ArrayList<E> getList() {
        return list;
    }

    void setList(ArrayList<E> list) {
        this.list = list;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    private boolean markAsDirty(boolean value) {
        if (value) {
            setDirty(true);
        }

        return value;
    }

    private boolean markAsDirty() {
        return markAsDirty(true);
    }

    @Override
    public boolean add(E e) {
        if (contains(e)) {
            return false;
        } else {
            list.add(e);
            return markAsDirty();
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;

        for (E e : c) {
            changed |= add(e);
        }

        return markAsDirty(changed);
    }

    @Override
    public void clear() {
        if (!isEmpty()) {
            list.clear();
            markAsDirty();
        }
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size(); i++) {
            if (list.get(i) == o) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean b = true;

        for (Object e : c) {
            b &= contains(e);
        }

        return b;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        final Iterator<E> it = list.iterator();

        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public E next() {
                return it.next();
            }

            @Override
            public void remove() {
                it.remove();
                markAsDirty(true);
            }
        };
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size(); i++) {
            if (list.get(i) == o) {
                list.remove(i);
                return markAsDirty();
            }
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;

        for (Object e : c) {
            changed |= remove(e);
        }

        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }
}
