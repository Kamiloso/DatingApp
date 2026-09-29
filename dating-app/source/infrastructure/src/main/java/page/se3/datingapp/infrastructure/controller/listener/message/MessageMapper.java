package page.se3.datingapp.infrastructure.controller.listener.message;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.message.MessageSend;
import page.se3.datingapp.domain.model.message.Message;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessageSendDto;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessageDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    MessageDto toDto(Message message);
    MessageSend toDomain(MessageSendDto messageSendDto);
}
