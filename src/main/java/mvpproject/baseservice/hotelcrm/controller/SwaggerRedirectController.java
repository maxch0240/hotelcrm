package mvpproject.baseservice.hotelcrm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/property-view/swagger-ui")
    public String redirectToSwaggerUi() {
        return "redirect:/property-view/swagger-ui/index.html?configUrl=/property-view/v3/api-docs/swagger-config";
    }
}
