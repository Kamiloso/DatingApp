package page.se3.datingapp.controller;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import page.se3.datingapp.infrastructure.abort.reason.LanguageNotFound;
import page.se3.datingapp.model.Scheme;
import page.se3.datingapp.service.LangService;

@RestController
@Validated
@RequiredArgsConstructor
public class LangController {

    private final LangService langService;

    @GetMapping("api-public/lang/json")
    public String json(
            @RequestParam
            @Size(max = Scheme.LEN_31)
            String lang

    ) throws LanguageNotFound {
        return langService.getJson(lang);
    }
}
