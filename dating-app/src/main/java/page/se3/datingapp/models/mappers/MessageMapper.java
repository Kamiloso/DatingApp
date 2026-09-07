package page.se3.datingapp.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import page.se3.datingapp.models.dto.*;
import page.se3.datingapp.models.entities.MessageEntity;
import page.se3.datingapp.models.requests.MessageSendRequest;

import java.time.Instant;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MessageMapper {

    Message toDto(MessageEntity messageEntity);

    default MessageEntity toEntity(
            MessageSendRequest messageSendRequest,
            Instant sendTime,
            User user,
            Chat chat
    ) {
        if (messageSendRequest == null) {
            return null;
        }
        return new MessageEntity(
                null,
                messageSendRequest.message(),
                sendTime,
                user.id(),
                chat.id()
        );
    }
}
