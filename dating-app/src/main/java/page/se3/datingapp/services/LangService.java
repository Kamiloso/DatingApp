package page.se3.datingapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface LangService {
    String getJson(String lang);
}

@Service
@RequiredArgsConstructor
class LangServiceImpl implements LangService {

    public String getJson(String lang) {
        return "DEF"; // TODO: finish
    }
}
