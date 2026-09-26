package page.se3.datingapp.infrastructure.rest.controller.message;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.application.service.message.struct.MessageSendRequest;
import page.se3.datingapp.application.service.message.struct.MessageFetchResponse;
import page.se3.datingapp.domain.model.message.Message;
import page.se3.datingapp.infrastructure.rest.controller.message.dto.MessageSendRequestDto;
import page.se3.datingapp.infrastructure.rest.controller.message.dto.MessageFetchResponseDto;
import page.se3.datingapp.infrastructure.rest.controller.message.dto.MessageDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    MessageDto toDto(Message message);
    MessageSendRequest toDomain(MessageSendRequestDto messageSendRequestDto);

    default MessageFetchResponseDto toDto(MessageFetchResponse messageFetchResponse) {
        return new MessageFetchResponseDto(
                MapUtils.toList(messageFetchResponse.persistent(), this::toDto),
                MapUtils.toList(messageFetchResponse.dirty(), this::toDto)
        );
    }
}
