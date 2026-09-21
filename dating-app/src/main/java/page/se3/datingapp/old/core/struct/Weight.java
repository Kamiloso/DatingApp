package page.se3.datingapp.old.core.struct;

import lombok.NonNull;

public record Weight<T>(
        T item,
        int weight
) implements Comparable<Weight<T>> {

    @Override
    public int compareTo(@NonNull Weight<T> other) {
        return Integer.compare(weight, other.weight);
    }
}
