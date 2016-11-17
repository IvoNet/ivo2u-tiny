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
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

/**
 * @author Ivo Woltring
 */
public class TinyHomeControllerTest {

    @InjectMocks
    private TinyHomeController controller;

    private MockHttpServletResponse response;

    @Mock
    private TinyUrl tinyUrl;

    @Mock
    private TinyRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.response = new MockHttpServletResponse();
    }

    @Test
    public void get() throws Exception {

        final Tiny tiny = new Tiny();
        tiny.setUrl("http://ivonet.nl");
        tiny.setId(1L);
        when(this.tinyUrl.decode("W")).thenReturn(1);
        when(this.repository.getOne(1L)).thenReturn(tiny);

        this.controller.get("W", this.response);
        assertThat(this.response.getRedirectedUrl(), is("http://ivonet.nl"));
    }

}