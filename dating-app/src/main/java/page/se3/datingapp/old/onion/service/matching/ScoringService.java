package page.se3.datingapp.old.onion.service.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.model.struct.dto.table.User;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ScoringService {

    // TODO: move quiz objects into some sort of config

    private static final String QUIZ_SEPARATOR = ":";

    record QuizSegment(
            int weight,
            boolean requiresEquals
    ) {}

    private static final List<QuizSegment> WEIGHT_LIST = List.of(
            new QuizSegment(5, true),
            new QuizSegment(10, true),
            new QuizSegment(5, false)
    );

    public int computeMatchScore(User userMain, User userMatch) {
        String[] quizArray1 = userMain.quizAnswers().split(Pattern.quote(QUIZ_SEPARATOR));
        String[] quizArray2 = userMatch.quizAnswers().split(Pattern.quote(QUIZ_SEPARATOR));

        if (quizArray1.length != quizArray2.length) {
            return 0;
        }

        int score = 0;

        for (int i = 0; i < Integer.min(quizArray1.length, WEIGHT_LIST.size()); i++) {
            String tape1 = quizArray1[i];
            String tape2 = quizArray2[i];

            if (tape1.length() != tape2.length()) {
                return 0;
            }

            QuizSegment quizSegment = WEIGHT_LIST.get(i);

            for (int j = 0; j < tape1.length(); j++) {
                char c1 = tape1.charAt(j);
                char c2 = tape1.charAt(j);

                if ((c1 == c2) == quizSegment.requiresEquals()) {
                    score += quizSegment.weight();
                }
            }
        }

        return score;
    }
}
