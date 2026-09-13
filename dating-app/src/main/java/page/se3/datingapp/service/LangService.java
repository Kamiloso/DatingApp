package page.se3.datingapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.abort.reason.LanguageNotFound;

public interface LangService {
    String getJson(String lang) throws LanguageNotFound;
}

@Service
@RequiredArgsConstructor
class LangServiceImpl implements LangService {

    @Override
    public String getJson(String lang) throws LanguageNotFound {
        return "DEF"; // TODO: finish
    }
}
