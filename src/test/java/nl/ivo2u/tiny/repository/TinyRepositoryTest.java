/*
 * Copyright 2021 Ivo Woltring <WebMaster@ivonet.nl>
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

package nl.ivo2u.tiny.repository;

import nl.ivo2u.tiny.model.Tiny;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringRunner.class)
@DataJpaTest
public class TinyRepositoryTest {

    private static final String HTTP_IVO_WAS_HERE_YUP = "http://IvoWasHere.yup";

    @Autowired
    private TinyRepository repository;


    @Test
    public void findOne() throws Exception {
        final Tiny one = this.repository.findOne(1L);
        assertThat(one.getUrl(), is("http://ivonet.nl"));
    }

    @Test
    public void readDistinctByUrl() throws Exception {
        final Tiny tiny = this.repository.readDistinctByUrl("http://ivonet.nl");
        assertThat(tiny.getId(), is(1L));
    }

    @Test
    public void createNew() throws Exception {
        final Tiny tiny = new Tiny();
        tiny.setUrl(HTTP_IVO_WAS_HERE_YUP);
        assertNull(tiny.getId());
        final Tiny ret = this.repository.saveAndFlush(tiny);
        assertNotNull(ret.getId());

        final Tiny one = this.repository.getOne(ret.getId());
        assertThat(one.getUrl(), is(HTTP_IVO_WAS_HERE_YUP));
        assertThat(one.getCounter(), is(0L));


    }

    @Test
    public void top5() throws Exception {
        final List<Tiny> tinies = this.repository.findTop5ByOrderByCounterDesc();
        assertThat(tinies.size(), is(5));
        assertThat(tinies.get(0)
                         .getId(), is(98L));
    }

    @Test
    public void updateCounter() throws Exception {
        Tiny one = this.repository.getOne(1L);
        Long startCounter = one.getCounter();
        this.repository.updateCounter(one.getId());
        one = this.repository.getOne(1L);
        assertThat(++startCounter, is(one.getCounter()));
    }

    @Test
    public void maxId() throws Exception {
        final Long maxId = this.repository.getMaxId();
        assertThat(maxId, is(155L));
    }
}
