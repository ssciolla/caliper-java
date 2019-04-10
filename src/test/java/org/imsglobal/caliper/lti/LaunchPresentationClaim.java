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

public class LaunchPresentationClaim {
    @JsonProperty("document_target")
    private String documentTarget;
    @JsonProperty("height")
    private int height;
    @JsonProperty("width")
    private int width;
    @JsonProperty("return_url")
    private String returnUrl;

    /**
     * Constructor
     * @param builder
     */
    private LaunchPresentationClaim(Builder builder) {
        this.documentTarget = builder.documentTarget;
        this.height = builder.height;
        this.width = builder.width;
        this.returnUrl = builder.returnUrl;
    }

    /**
     * Builder
     */
    public static class Builder {
        private String documentTarget;
        private int height;
        private int width;
        private String returnUrl;

        public Builder documentTarget(String documentTarget) {
            this.documentTarget = documentTarget;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder returnUrl(String returnUrl) {
            this.returnUrl = returnUrl;
            return this;
        }

        public LaunchPresentationClaim build() {
            return new LaunchPresentationClaim(this);
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
