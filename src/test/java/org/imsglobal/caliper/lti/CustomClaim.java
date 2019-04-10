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

public class CustomClaim {
    @JsonProperty("xstart")
    private String xstart;
    @JsonProperty("request_url")
    private String requestURL;

    /**
     * Constructor
     * @param builder
     */
    private CustomClaim(Builder builder) {
        this.xstart = builder.xstart;
        this.requestURL = builder.requestURL;
    }

    /**
     * Builder
     */
    public static class Builder {
        private String xstart;
        private String requestURL;

        public Builder xstart(String xstart) {
            this.xstart = xstart;
            return this;
        }

        public Builder requestURL(String requestURL) {
            this.requestURL = requestURL;
            return this;
        }

        public CustomClaim build() {
            return new CustomClaim(this);
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
