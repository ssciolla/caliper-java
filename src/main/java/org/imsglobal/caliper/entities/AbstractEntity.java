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

package org.imsglobal.caliper.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.identifiers.SystemIdentifier;
import org.imsglobal.caliper.validators.EntityValidator;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * This class provides a skeletal implementation of the Entity interface
 * in order to minimize the effort required to implement the interface.
 */
public abstract class AbstractEntity implements CaliperEntity, CaliperCoercible {

    @JsonProperty("@context")
    private final JsonldContext context;

    @JsonIgnore
    private final boolean coercedToId;

    @JsonProperty("id")
    protected final String id;

    @JsonProperty("type")
    private final CaliperEntityType type;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("dateCreated")
    private final DateTime dateCreated;

    @JsonProperty("dateModified")
    private final DateTime dateModified;

    @JsonProperty("otherIdentifiers")
    private final ImmutableList<SystemIdentifier> otherIdentifiers;

    @JsonProperty("extensions")
    private final Map<String, Object> extensions;

    /**
     * @param builder apply builder object properties to the object.
     */
    protected AbstractEntity(Builder<?> builder) {

        EntityValidator.checkId("id", builder.id);

        this.context = builder.context;
        this.coercedToId = builder.coercedToId;
        this.id = builder.id;
        this.type = builder.type;
        this.name = builder.name;
        this.description = builder.description;
        this.dateCreated = builder.dateCreated;
        this.dateModified = builder.dateModified;
        this.otherIdentifiers = ImmutableList.copyOf(builder.otherIdentifiers);
        this.extensions = builder.extensions;
    }

    /**
     * @return the context.
     */
    @Nullable
    public JsonldContext getContext() {
        return context;
    }

    /**
     * @return coerceToId flag
     */
    @Nonnull
    public boolean isCoercedToId() {
        return coercedToId;
    }

    /**
     * @return the id.
     */
    @Nonnull
    public String getId() {
        return id;
    }

    /**
     * @return the type.
     */
    @Nonnull
    public CaliperEntityType getType() {
        return type;
    }

    /**
     * @return name.
     */
    @Nullable
    public String getName() {
        return name;
    }

    /**
     * @return description.
     */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * @return date created.
     */
    @Nullable
    public DateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * @return the date modified.
     */
    @Nullable
    public DateTime getDateModified() {
        return dateModified;
    }

    /**
     * @return the array of SystemIdentifiers
     */
    @Nullable
    public ImmutableList<SystemIdentifier> getOtherIdentifiers() {
        return otherIdentifiers;
    }

    /**
     * @return custom extensions object.
     */
    @Nullable
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> {
        private boolean coercedToId;
        private JsonldContext context;
        private String id;
        private CaliperEntityType type;
        private String name;
        private String description;
        private DateTime dateCreated;
        private DateTime dateModified;
        private List<SystemIdentifier> otherIdentifiers = Lists.newArrayList();
        private Map<String, Object> extensions;

        /**
         * Constructor
         */
        public Builder() {
            type(EntityType.ENTITY);
            coercedToId(false);
        }

        protected abstract T self();

        /**
         * @param coercedToId
         * @return builder.
         */
        public T coercedToId(boolean coercedToId) {
            this.coercedToId = coercedToId;
            return self();
        }

        /**
         * @param context
         * @return builder.
         */
        public T context(JsonldContext context) {
            this.context = context;
            return self();
        }

        /**
         * @param id
         * @return builder.
         */
        public T id(String id) {
            this.id = id;
            return self();
        }

        /**
         * @param type
         * @return builder.
         */
        public T type(CaliperEntityType type) {
            this.type = type;
            return self();
        }

        /**
         * @param name
         * @return builder.
         */
        public T name(String name) {
            this.name = name;
            return self();
        }

        /**
         * @param description
         * @return builder.
         */
        public T description(String description) {
            this.description = description;
            return self();
        }

        /**
         * @param dateCreated
         * @return builder.
         */
        public T dateCreated(DateTime dateCreated) {
            this.dateCreated = dateCreated;
            return self();
        }

        /**
         * @param dateModified
         * @return builder.
         */
        public T dateModified(DateTime dateModified) {
            this.dateModified = dateModified;
            return self();
        }

        /**
         * @param otherIdentifiers
         * @return builder.
         */
        public T otherIdentifiers(List<SystemIdentifier> otherIdentifiers) {
            if(otherIdentifiers != null) {
                this.otherIdentifiers.addAll(otherIdentifiers);
            }
            return self();
        }

        /**
         * @param extensions
         * @return builder.
         */
        public T extensions(Map<String, Object> extensions) {
            this.extensions = extensions;
            return self();
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
}