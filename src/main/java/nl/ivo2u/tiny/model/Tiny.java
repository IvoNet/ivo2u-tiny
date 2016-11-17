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

package nl.ivo2u.tiny.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;
import java.util.Date;


@Entity
@XmlRootElement
@Table
public class Tiny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4242)
    @NotNull
    private String url;

    @NotNull
    private Long counter = 0L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_date", updatable=false, nullable = false)
    private Date creationDate;


    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public Long getCounter() {
        return this.counter;
    }

    public void setCounter(final Long counter) {
        this.counter = counter;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tiny{");
        sb.append("id=")
          .append(id);
        sb.append(", url='")
          .append(url)
          .append('\'');
        sb.append(", counter=")
          .append(counter);
        sb.append(", creationDate=")
          .append(creationDate);
        sb.append('}');
        return sb.toString();
    }

    @PrePersist
    public void prePersist() {
        this.creationDate = Calendar.getInstance()
                                    .getTime();
    }
}
