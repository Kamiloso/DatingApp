package page.se3.datingapp.application.service.relation.assembly;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.SubService;
import page.se3.datingapp.application.repository.ChatRepository;
import page.se3.datingapp.application.repository.MatchGroupRepository;
import page.se3.datingapp.application.repository.MatchRepository;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.model.relation.chat.Chat;
import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;
import page.se3.datingapp.domain.model.relation.Relation;

import java.util.*;
import java.util.stream.Collectors;

@SubService
@RequiredArgsConstructor
public class MultiRelationAssembler {

    private final ChatRepository chatRepository;
    private final MatchRepository matchRepository;
    private final MatchGroupRepository matchGroupRepository;

    public List<Relation> getByIdUserForApplicationPurposes(Long idUser, int withChat, boolean blocked) {
        return getByMatchList(matchRepository.findAllByIdUserForApplicationPurposes(idUser, blocked), withChat);
    }

    private List<Relation> getByMatchList(List<Match> matchList, int withChat) {
        Map<Long, Match> matchMap = matchList.stream()
                .collect(Collectors.toMap(Match::id, x -> x));

        List<Long> idMatchList = MapUtils.toList(matchList, Match::id);
        
        List<Chat> chatList = chatRepository.findAllByIdMatchList(idMatchList);

        List<Long> idMatchGroupList = matchMap.values().stream()
                .map(Match::idMatchGroup)
                .distinct()
                .toList();

        Map<Long, MatchGroup> matchGroupMap = MapUtils.toMap(
                matchGroupRepository.findAllByIdList(idMatchGroupList),
                MatchGroup::id, x -> x
        );

        return assembleAndCollect(chatList, matchMap, matchGroupMap, withChat);
    }

    private static List<Relation> assembleAndCollect(
            List<Chat> chatList,
            Map<Long, Match> matchMap,
            Map<Long, MatchGroup> matchGroupMap,
            int withChat
    ) {
        List<Relation> targetRelations = new ArrayList<>();

        if (withChat >= 0) {
            assembleAndAppendWithChat(targetRelations, chatList, matchMap, matchGroupMap);
        }

        if (withChat <= 0) {
            assembleAndAppendWithoutChat(targetRelations, chatList, matchMap, matchGroupMap);
        }

        return targetRelations;
    }

    private static void assembleAndAppendWithChat(
            List<Relation> targetRelations,
            List<Chat> chatList,
            Map<Long, Match> matchMap,
            Map<Long, MatchGroup> matchGroupMap
    ) {
        for (Chat chat : chatList) {
            Match match = matchMap.get(chat.idMatch());

            if (match != null) {
                MatchGroup matchGroup = matchGroupMap.get(match.idMatchGroup());

                if (matchGroup != null) {
                    targetRelations.add(new Relation(matchGroup, match, chat));
                }
            }
        }
    }

    private static void assembleAndAppendWithoutChat(
            List<Relation> targetRelations,
            List<Chat> chatList,
            Map<Long, Match> matchMap,
            Map<Long, MatchGroup> matchGroupMap
    ) {
        Set<Long> referencedIdMatchSet = MapUtils.toSet(chatList, Chat::idMatch);

        for (Match match : matchMap.values()) {
            if (referencedIdMatchSet.contains(match.id())) {
                continue;
            }

            MatchGroup matchGroup = matchGroupMap.get(match.idMatchGroup());

            if (matchGroup != null) {
                targetRelations.add(new Relation(matchGroup, match, null));
            }
        }
    }
}
