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

import nl.ivo2u.tiny.model.Tokens;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivo Woltring
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TinyRestControllerIT {

    @Autowired
    private TestRestTemplate client;

    @Test
    public void get() {
        final ResponseEntity<String> response = this.client.getForEntity("http://localhost:8080/W", String.class);
        assertTrue(response.getStatusCode().is3xxRedirection());
    }

    @Test
    public void popular() throws Exception {
        final ResponseEntity<Tokens> ret = this.client.getForEntity("http://localhost:8080/api/popular", Tokens.class);
        assertThat(ret.getStatusCode(), equalTo(HttpStatus.OK));

        final Tokens tokens = ret.getBody();
        assertThat(tokens.size(), is(5));

    }

    @Test
    public void api() {
        final ResponseEntity<String> ret = this.client.postForEntity("http://localhost:8080/api",
                                                                     "http://ivonet.nl", String.class);

        assertThat(ret.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(ret.getBody(), is("http://localhost:8080/W"));

    }

    @Test
    public void api2() {
        final ResponseEntity<String> ret = this.client.postForEntity("http://localhost:8080/api",
                                                                     "https://www.ivonet.nl/2019/02/05/java-ee-8-+-payara-5-+-microprofile-2.1-+-docker-in-about-a-minute/",
                                                                     String.class);

        assertThat(ret.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(ret.getBody(), is("http://localhost:8080/df"));
        final ResponseEntity<String> response = this.client.getForEntity("http://localhost:8080/df", String.class);
        assertTrue(response.getStatusCode()
                           .is3xxRedirection());
        assertEquals(
              "https://www.ivonet.nl/2019/02/05/java-ee-8-+-payara-5-+-microprofile-2.1-+-docker-in-about-a-minute/",
              response.getHeaders()
                      .getLocation()
                      .toString());

    }

    @Test
    public void apiPost() {
        final ResponseEntity<String> ret = this.client.postForEntity("http://localhost:8080/api",
                                                                     "https://www.dropbox.com/s/o2zkhd2zrtqoa9e/2%20Dutch.shortcut?dl=1",
                                                                     String.class);

        assertThat(ret.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(ret.getBody(), is("http://localhost:8080/de"));

    }

}