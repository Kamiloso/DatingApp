package page.se3.datingapp.application.service.message.struct;

import page.se3.datingapp.domain.model.message.Message;

import java.util.List;

public record MessagesFetched(
        List<Message> persistent,
        List<Message> dirty
) {

    public MessagesFetched {
        persistent = List.copyOf(persistent);
        dirty = List.copyOf(dirty);
    }

    public static MessagesFetched empty() {
        return new MessagesFetched(List.of(), List.of());
    }

    public static MessagesFetched persistent(List<Message> persistent) {
        return new MessagesFetched(persistent, List.of());
    }

    public static MessagesFetched dirty(List<Message> dirty) {
        return new MessagesFetched(List.of(), dirty);
    }

    public static MessagesFetched both(List<Message> persistent, List<Message> dirty) {
        return new MessagesFetched(persistent, dirty);
    }
}
