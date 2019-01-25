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

package nl.ivo2u.tiny.boundary;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * @author Ivo Woltring
 */

public class TinyUrlTest {

    private TinyUrl tinyUrl;

    @Before
    public void setUp() throws Exception {
        this.tinyUrl = new TinyUrl();

    }

    @Test
    public void testRange() throws Exception {
        final String encode = this.tinyUrl.encode(237346918);
        assertEquals(237346918, this.tinyUrl.decode(encode));

    }

    @Test
    public void one() throws Exception {
        final String encode = this.tinyUrl.encode(1);
        assertThat(encode, is("W"));
    }
    @Test
    public void d() throws Exception {
        final int ret = this.tinyUrl.decode("d");
        assertThat(ret, is(2));
    }

    @Test
    public void dropbox() throws Exception {
        final int ret = this.tinyUrl.decode("d");
        assertThat(ret, is(2));
    }

}