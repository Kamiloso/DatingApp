package page.se3.datingapp.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import page.se3.datingapp.services.LangService;

@RestController
@Validated
@RequiredArgsConstructor
public class LangController {

    private final LangService langService;

    @GetMapping("api-public/lang/json")
    public String json(
            @RequestParam
            @NotBlank
            String lang
    ) {
        return langService.getJson(lang);
    }
}
