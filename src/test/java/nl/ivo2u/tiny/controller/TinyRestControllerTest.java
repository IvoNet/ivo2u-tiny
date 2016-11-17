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

import nl.ivo2u.tiny.model.Tiny;
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

/**
 * @author Ivo Woltring
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT )
public class TinyRestControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void get() throws Exception {
        final ResponseEntity<Tiny> response = testRestTemplate.getForEntity("http://localhost:8080/W", Tiny.class);
        System.out.println("response = " + response);
        System.out.println("response.getBody() = " + response.getBody());
        assertThat( response.getStatusCode() , equalTo(HttpStatus.OK));

//   	    ObjectMapper objectMapper = new ObjectMapper();
//   	    JsonNode responseJson = objectMapper.readTree(response.getBody());
//
//   	    assertThat( responseJson.isMissingNode() , is(false) );
//   	    assertThat( responseJson.toString() , equalTo("[]") );

    }

    @Test
    public void popular() throws Exception {

    }

    @Test
    public void api() throws Exception {

    }

}