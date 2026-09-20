package page.se3.datingapp.onion.service.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.model.struct.dto.Match;
import page.se3.datingapp.onion.accessor.MatchAccessor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchAccessor matchAccessor;

    public List<Match> getPotentialList() {
        throw new RuntimeException();
    }
}
