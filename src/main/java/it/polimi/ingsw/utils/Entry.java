package it.polimi.ingsw.utils;

import java.util.Map;

public class Entry<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key corresponding to this entry.
     *
     * @return the key corresponding to this entry
     * @throws IllegalStateException implementations may, but are not
     *                               required to, throw this exception if the entry has been
     *                               removed from the backing map.
     */
    @Override
    public K getKey() {
        return this.key;
    }

    /**
     * Returns the value corresponding to this entry.  If the mapping
     * has been removed from the backing map (by the iterator's
     * {@code remove} operation), the results of this call are undefined.
     *
     * @return the value corresponding to this entry
     * @throws IllegalStateException implementations may, but are not
     *                               required to, throw this exception if the entry has been
     *                               removed from the backing map.
     */
    @Override
    public V getValue() {
        return this.value;
    }

    /**
     * Replaces the value corresponding to this entry with the specified
     * value (optional operation).  (Writes through to the map.)  The
     * behavior of this call is undefined if the mapping has already been
     * removed from the map (by the iterator's {@code remove} operation).
     *
     * @param value new value to be stored in this entry
     * @return old value corresponding to the entry
     * @throws UnsupportedOperationException if the {@code put} operation
     *                                       is not supported by the backing map
     * @throws ClassCastException            if the class of the specified value
     *                                       prevents it from being stored in the backing map
     * @throws NullPointerException          if the backing map does not permit
     *                                       null values, and the specified value is null
     * @throws IllegalArgumentException      if some property of this value
     *                                       prevents it from being stored in the backing map
     * @throws IllegalStateException         implementations may, but are not
     *                                       required to, throw this exception if the entry has been
     *                                       removed from the backing map.
     */
    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }
}
