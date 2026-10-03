package page.se3.datingapp.infrastructure.database.jdbc.account;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.account.Account;
import page.se3.datingapp.infrastructure.database.RoundTripEntityMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper extends RoundTripEntityMapper<Account, AccountEntity> {}
