package page.se3.datingapp.application.service.message.struct;

import page.se3.datingapp.domain.model.message.Message;

import java.util.List;

public record MessageFetchResponse(
        List<Message> persistent,
        List<Message> dirty
) {

    public MessageFetchResponse {
        persistent = List.copyOf(persistent);
        dirty = List.copyOf(dirty);
    }

    public static MessageFetchResponse empty() {
        return new MessageFetchResponse(List.of(), List.of());
    }

    public static MessageFetchResponse persistent(List<Message> persistent) {
        return new MessageFetchResponse(persistent, List.of());
    }

    public static MessageFetchResponse dirty(List<Message> dirty) {
        return new MessageFetchResponse(List.of(), dirty);
    }

    public static MessageFetchResponse both(List<Message> persistent, List<Message> dirty) {
        return new MessageFetchResponse(persistent, dirty);
    }
}
