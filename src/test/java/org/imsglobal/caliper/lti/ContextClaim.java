/**
 * This file is part of IMS Caliper Analytics™ and is licensed to
 * IMS Global Learning Consortium, Inc. (http://www.imsglobal.org)
 * under one or more contributor license agreements.  See the NOTICE
 * file distributed with this work for additional information.
 *
 * IMS Caliper is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * IMS Caliper is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.imsglobal.caliper.lti;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fest.util.Lists;

import java.util.List;

public class ContextClaim {
    @JsonProperty("id")
    private String id;
    @JsonProperty("label")
    private String label;
    @JsonProperty("title")
    private String title;
    @JsonProperty("type")
    private List<String> type;

    /**
     * Constructor
     * @param builder
     */
    private ContextClaim(Builder builder) {
        this.id = builder.id;
        this.label = builder.label;
        this.title = builder.title;
        this.type = Lists.newArrayList(builder.type);
    }

    /**
     * Builder
     */
    public static class Builder {
        private String id;
        private String label;
        private String title;
        private List<String> type = Lists.newArrayList();

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder type(List<String> type) {
            if(type != null) {
                this.type.addAll(type);
            }
            return this;
        }

        public ContextClaim build() {
            return new ContextClaim(this);
        }
    }

    /**
     * Factory method
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }
}
