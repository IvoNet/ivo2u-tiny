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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Ivo Woltring
 */
@XmlRootElement
public class Token {

    @XmlElement
    private String tinyUrl;
    @XmlElement
    private Long id;
    @XmlElement
    private String destinationUrl;
    @XmlElement
    private Long counter;
    @XmlElement
    private Date creationDate;

    public Token() {
    }

    public Token(final String tinyUrl, final Tiny tiny) {
        this.tinyUrl = tinyUrl;
        this.id = tiny.getId();
        this.destinationUrl = tiny.getUrl();
        this.counter = tiny.getCounter();
        this.creationDate = tiny.getCreationDate();
    }

    public String getTinyUrl() {
        return this.tinyUrl;
    }

    public Long getId() {
        return this.id;
    }

    public String getDestinationUrl() {
        return this.destinationUrl;
    }

    public Long getCounter() {
        return this.counter;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }
}
