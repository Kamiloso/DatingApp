package page.se3.datingapp.model.struct.post;

import page.se3.datingapp.model.enums.Gender;
import page.se3.datingapp.model.enums.Relationship;

public record UserPost(
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