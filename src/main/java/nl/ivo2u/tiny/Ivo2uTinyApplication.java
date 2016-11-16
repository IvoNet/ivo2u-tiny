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

package nl.ivo2u.tiny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class Ivo2uTinyApplication {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

       return (container -> {
            final ErrorPage error204Page = new ErrorPage(HttpStatus.NO_CONTENT, "/error/204.html");
            final ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/error/403.html");
            final ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
            final ErrorPage error406Page = new ErrorPage(HttpStatus.NOT_ACCEPTABLE, "/error/406.html");
            final ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/error.html");

            container.addErrorPages(error204Page, error403Page, error404Page, error406Page, error500Page);
       });
    }

    public static void main(final String[] args) {
        SpringApplication.run(Ivo2uTinyApplication.class, args);
    }
}
