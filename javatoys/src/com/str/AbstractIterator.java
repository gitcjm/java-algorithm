package com.str;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractIterator<T> implements Iterator<T> {
    T next = nextElement();

    public boolean hasNext() {
        return next != null;
    }

    public T next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        T result = next;
        next = nextElement();
        return result;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    protected abstract T nextElement();

    private static Iterator<Character> test(final String s) {
        return new AbstractIterator<Character>() {
            private int cursor = 0;
            protected Character nextElement() {
                return cursor == s.length() ? null : s.charAt(cursor++);
            }
        };
    }

    public static void main(String[] args) {
        for(Iterator<Character> i = test("CJM"); i.hasNext();) {
            System.out.println(i.next());
        }
    }
}
