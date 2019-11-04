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

package org.imsglobal.caliper.identifiers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SystemIdentifier {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("identifierType")
    private final SystemIdentifierType identifierType;

    @JsonProperty("identifier")
    private final String identifier;

    @JsonProperty("source")
    private final SoftwareApplication source;

    @JsonProperty("extensions")
    private final Map<String, Object> extensions;

    /**
     * @param builder apply builder object properties to the object.
     */
    private SystemIdentifier(Builder<?> builder) {
        this.type = builder.type;
        this.identifierType = builder.identifierType;
        this.identifier = builder.identifier;
        this.source = builder.source;
        this.extensions = builder.extensions;
    }

    /**
     * @return the type
     */
    @Nonnull
    public String getType() {
        return type;
    }

    /**
     * @return the identifierType
     */
    @Nonnull
    public SystemIdentifierType getIdentifierType() {
        return identifierType;
    }

    /**
     * @return the identifier
     */
    @Nonnull
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @return the source
     */
    @Nullable
    public SoftwareApplication getSource() {
        return source;
    }

    /**
     * @return the extensions
     */
    @Nullable
    public Map<String, Object> getExtensions() { return extensions; }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> {
        private String type;
        private SystemIdentifierType identifierType;
        private String identifier;
        private SoftwareApplication source;
        private Map<String, Object> extensions;

        /**
         * Constructor
         */
        public Builder() {
            type("SystemIdentifier");
        }

        protected abstract T self();

        public T type(String type) {
            this.type = type;
            return self();
        }

        public T identifierType(SystemIdentifierType identifierType) {
            this.identifierType = identifierType;
            return self();
        }

        public T identifier(String identifier) {
            this.identifier = identifier;
            return self();
        }

        public T source(SoftwareApplication source) {
            this.source = source;
            return self();
        }

        public T extensions(Map<String, Object> extensions) {
            this.extensions = extensions;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of SystemIdentifier.
         */
        public SystemIdentifier build() {
            return new SystemIdentifier(this);
        }
    }

    /**
     *
     */
    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    /**
     * Static factory method.
     * @return a new instance of the builder.
     */
    public static Builder<?> builder() {
        return new Builder2();
    }
}