package page.se3.datingapp.old.model.struct.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import page.se3.datingapp.old.model.enums.Gender;
import page.se3.datingapp.old.model.enums.Relationship;

@Table("users")
public record UserEntity(
        @Id Long id,
        String email,
        String name,
        String surname,
        Gender gender,
        Integer age,
        String fieldOfStudy,
        String description,
        Gender targetGender,
        Relationship targetRelationship,
        String quizAnswers,
        Boolean isParticipating,
        Integer readyIndex
) {}
