/*
 * Copyright 2016 Ivo Woltring <WebMaster@ivonet.nl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.ivo2u.tiny.controller;


import lombok.extern.slf4j.Slf4j;
import nl.ivo2u.tiny.boundary.TinyUrl;
import nl.ivo2u.tiny.model.Tiny;
import nl.ivo2u.tiny.model.Token;
import nl.ivo2u.tiny.model.Tokens;
import nl.ivo2u.tiny.repository.TinyRepository;
import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Slf4j
@RestController
@RequestMapping("/api")
public class TinyRestController {

    private static final String UTF_8 = "UTF-8";
    private final TinyUrl tinyUrl;
    private final TinyRepository tinyRepository;

    @Autowired
    public TinyRestController(final TinyUrl tinyUrl,
                              final TinyRepository tinyRepository) {
        this.tinyUrl = tinyUrl;
        this.tinyRepository = tinyRepository;
    }

    private static HttpServletRequest getCurrentRequest() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        final HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
        return servletRequest;
    }

    @GetMapping(value = "popular", produces = APPLICATION_JSON_UTF8_VALUE)
    public List<Tiny> popular(final HttpServletRequest request) {
        final List<Tiny> resultList = this.tinyRepository.findTop5ByOrderByCounterDesc();
        final Tokens tokens = new Tokens();
        resultList.stream()
                  .map(p -> new Token(request.getRequestURI() + "/" + this.tinyUrl.encode(p.getId()), p))
                  .forEach(tokens::add);
        return resultList;
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String api(@RequestBody final String body,
                      final HttpServletRequest request) {

        log.debug(body);

        if (body.isEmpty()) {
            throw new RuntimeException("You should provide a url");
        }
        if (isWrongUrl(body)) {
            throw new RuntimeException("The url seems to be invalid");
        }
        if (body.contains(request.getServerName())) {
            throw new RuntimeException("The url can not be of the ivo2u domain itself.");
        }
        return createUrl(body);
    }

    private String createUrl(final String url) {
        Tiny tiny = this.tinyRepository.readDistinctByUrl(url);
        if (tiny == null) {
            tiny = new Tiny();
            tiny.setUrl(url);
            this.tinyRepository.saveAndFlush(tiny);
        }
        return makeUrl(tiny.getId());
    }

    private String makeUrl(final Long id) {
        final String port = (getCurrentRequest().getServerPort() == 80) ? "" : (":" + getCurrentRequest().getServerPort());
        return "http://" + getCurrentRequest().getServerName() + port + "/" + this.tinyUrl.encode(id);
    }

    private boolean isWrongUrl(final String url) {
        final String[] schemes = {"http", "https"};
        final UrlValidator urlValidator = new UrlValidator(schemes);
        return !urlValidator.isValid(url);
    }

}
