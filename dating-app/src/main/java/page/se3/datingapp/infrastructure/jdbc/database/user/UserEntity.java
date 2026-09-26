package page.se3.datingapp.infrastructure.jdbc.database.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.enums.Relationship;

@Table("readyUsers")
public record UserEntity(
        @Id Long id,
        String email,
        String name,
        String surname,
        Gender gender,
        Integer age,
        String fieldOfStudy,
        String description,
        Relationship targetRelationshipMan,
        Relationship targetRelationshipWoman,
        Relationship targetRelationshipOther,
        String quizAnswers,
        Integer matchingIndex,
        Integer readyIndex
) {}
