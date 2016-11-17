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
import nl.ivo2u.tiny.repository.TinyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ivo Woltring
 */
@Controller
@RequestMapping("/")
@Transactional
public class TinyHomeController {

    @Autowired
    private TinyUrl tinyUrl;

    @Autowired
    private TinyRepository tinyRepository;


    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("forward:/home/index.html");
    }

    @GetMapping("{id}")
    public void get(@PathVariable("id") final String token, final HttpServletResponse response) {
        final Tiny tiny = this.tinyRepository.getOne((long) this.tinyUrl.decode(token));
        this.tinyRepository.updateCounter(tiny.getId());
        try {
            response.sendRedirect(tiny.getUrl()
                                      .replace(" ", "%20"));
        } catch (final IOException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

}
