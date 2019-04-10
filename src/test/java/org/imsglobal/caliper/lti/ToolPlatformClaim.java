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

public class ToolPlatformClaim {
    @JsonProperty("guid")
    private String guid;
    @JsonProperty("contact_email")
    private String contactEmail;
    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("product_family_code")
    private String productFamilyCode;
    @JsonProperty("version")
    private String version;

    /**
     * Constructor
     * @param builder
     */
    private ToolPlatformClaim(Builder builder) {
        this.guid = builder.guid;
        this.contactEmail = builder.contactEmail;
        this.description = builder.description;
        this.name = builder.name;
        this.url = builder.url;
        this.productFamilyCode = builder.productFamilyCode;
        this.version = builder.version;
    }

    /**
     * Builder
     */
    public static class Builder {
        private String guid;
        private String contactEmail;
        private String description;
        private String name;
        private String url;
        private String productFamilyCode;
        private String version;

        public Builder guid(String guid) {
            this.guid = guid;
            return this;
        }

        public Builder contactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder productFamilyCode(String productFamilyCode) {
            this.productFamilyCode = productFamilyCode;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public ToolPlatformClaim build() {
            return new ToolPlatformClaim(this);
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
