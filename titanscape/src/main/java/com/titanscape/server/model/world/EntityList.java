package com.titanscape.server.model.world;

import com.titanscape.server.model.entity.Entity;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A fixed-capacity indexed collection of entities.
 */
public final class EntityList<T extends Entity> implements Iterable<T> {

    private final Object[] entities;
    private final int capacity;
    private int size;

    public EntityList(int capacity) {
        this.capacity = capacity;
        this.entities = new Object[capacity];
    }

    public boolean add(T entity) {
        for (int i = 1; i < capacity; i++) {
            if (entities[i] == null) {
                entities[i] = entity;
                entity.setIndex(i);
                size++;
                return true;
            }
        }
        return false;
    }

    public void remove(T entity) {
        int idx = entity.getIndex();
        if (idx >= 0 && idx < capacity && entities[idx] == entity) {
            entities[idx] = null;
            entity.setIndex(-1);
            size--;
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= capacity) return null;
        return (T) entities[index];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public boolean isFull() {
        return size >= capacity - 1;
    }

    public Stream<T> stream() {
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(iterator(), Spliterator.ORDERED), false
        );
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cursor = 1;
            private int found = 0;

            @Override
            public boolean hasNext() {
                return found < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                while (cursor < capacity) {
                    T entity = (T) entities[cursor++];
                    if (entity != null) {
                        found++;
                        return entity;
                    }
                }
                throw new NoSuchElementException();
            }
        };
    }
}
