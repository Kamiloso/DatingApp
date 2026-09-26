package page.se3.datingapp.infrastructure.jdbc;

public interface RoundTripEntityMapper<TDomain, TEntity> {

    TEntity toEntity(TDomain domain);
    TDomain toDomain(TEntity entity);
}
