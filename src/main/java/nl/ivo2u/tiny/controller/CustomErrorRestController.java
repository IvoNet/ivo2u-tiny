package nl.ivo2u.tiny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

//@RestController
public class CustomErrorRestController implements ErrorController {

    private static final String PATH = "/error";

    @Value("${debug}")
    private boolean debug;

    @Autowired
    private ErrorAttributes errorAttributes;

//    @RequestMapping(value = PATH)
//    ErrorJson error(final HttpServletRequest request, final HttpServletResponse response) {
////         Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
////         Here we just define response body.
//        return new ErrorJson(response.getStatus(), getErrorAttributes(request, debug));
//    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private Map<String, Object> getErrorAttributes(final HttpServletRequest request, final boolean includeStackTrace) {
        final RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

}