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


import nl.ivo2u.tiny.boundary.TinyUrl;
import nl.ivo2u.tiny.model.Tiny;
import nl.ivo2u.tiny.model.Token;
import nl.ivo2u.tiny.model.Tokens;
import nl.ivo2u.tiny.repository.TinyRepository;
import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RestController
@RequestMapping("/api")
public class TinyRestController {

    private final TinyUrl tinyUrl;
    private final TinyRepository tinyRepository;

    @Autowired
    public TinyRestController(final TinyUrl tinyUrl, final TinyRepository tinyRepository) {
        this.tinyUrl = tinyUrl;
        this.tinyRepository = tinyRepository;
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
    public String api(@RequestBody final String body, final HttpServletRequest request)
            throws UnsupportedEncodingException {

        final String url = java.net.URLDecoder.decode(body.endsWith("=") ? body.substring(0, body.length() - 1) : body,
                                                      "UTF-8");
        if (url.isEmpty() || isWrongUrl(url) || url.contains(request.getServerName())) {
            throw new RuntimeException("The request was wrong in some way... Please try again.");
        }
        return createUrl(url);
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

    private static HttpServletRequest getCurrentRequest() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        final HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
        return servletRequest;
    }

}
