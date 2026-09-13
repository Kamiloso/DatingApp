package page.se3.datingapp.core.type;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Out<T> {

    private boolean exists;
    private T value;

    public void set(T value) {
        this.exists = true;
        this.value = value;
    }
    
    public void unset() {
        this.exists = false;
        this.value = null;
    }

    public T get() {
        if (!exists) {
            throw new IllegalStateException("Out<T> value was not set");
        }
        return value;
    }
}
