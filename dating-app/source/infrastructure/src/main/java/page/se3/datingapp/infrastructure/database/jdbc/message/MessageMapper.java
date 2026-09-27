package page.se3.datingapp.infrastructure.database.jdbc.message;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.message.Message;
import page.se3.datingapp.infrastructure.database.RoundTripEntityMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper extends RoundTripEntityMapper<Message, MessageEntity> {}
