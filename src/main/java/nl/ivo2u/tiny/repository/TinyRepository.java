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

package nl.ivo2u.tiny.repository;

import nl.ivo2u.tiny.model.Tiny;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TinyRepository extends JpaRepository<Tiny, Long> {

    Tiny readDistinctByUrl(String url);

    List<Tiny> findTop5ByOrderByCounterDesc();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE #{#entityName} t SET t.counter = (t.counter + 1) WHERE t.id = :id")
    void updateCounter(@Param("id") Long id);

    @Query("SELECT max(t.id) FROM #{#entityName} t")
    Long getMaxId();

    // TODO: 17-11-2016 Implement Lucky
}
