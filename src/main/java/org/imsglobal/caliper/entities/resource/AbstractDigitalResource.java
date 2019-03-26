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

package org.imsglobal.caliper.entities.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.AbstractEntity;
import org.imsglobal.caliper.entities.CaliperEntity;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.agent.CaliperAgent;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This class provides a skeletal implementation of the Resource interface
 * in order to minimize the effort required to implement the interface.
 */
public abstract class AbstractDigitalResource extends AbstractEntity implements CaliperDigitalResource {

    @JsonProperty("mediaType")
    private final String mediaType;

    @JsonProperty("storageName")
    private final String storageName;

    @JsonProperty("creators")
    private final ImmutableList<CaliperAgent> creators;

    @JsonProperty("learningObjectives")
    private final ImmutableList<LearningObjective> learningObjectives;

    @JsonProperty("keywords")
    private final ImmutableList<String> keywords;

    @JsonProperty("isPartOf")
    private final CaliperEntity isPartOf;

    @JsonProperty("datePublished")
    private final DateTime datePublished;

    @JsonProperty("version")
    private final String version;

    /**
     * @param builder apply builder object properties to the object.
     */
    protected AbstractDigitalResource(Builder<?> builder) {
        super(builder);

        this.mediaType = builder.mediaType;
        this.storageName = builder.storageName;
        this.creators = ImmutableList.copyOf(builder.creators);
        this.learningObjectives = ImmutableList.copyOf(builder.learningObjectives);
        this.keywords = ImmutableList.copyOf(builder.keywords);
        this.isPartOf = builder.isPartOf;
        this.datePublished = builder.datePublished;
        this.version = builder.version;
    }

    /**
     * @return the mediaType
     */
    @Nullable
    public String getMediaType() {
        return mediaType;
    }

    /**
     * @return the storage name
     */
    @Nullable
    public String getStorageName() { return storageName; }

    /**
     * Return an immutable view of the creators list.
     * @return the creators of this resource
     */
    @Nullable
    public ImmutableList<CaliperAgent> getCreators() {
        return creators;
    }

    /**
     * Return an immutable view of the learningObjectives list.
     * @return the learning objectives
     */
    @Nullable
    public ImmutableList<LearningObjective> getLearningObjectives() {
        return learningObjectives;
    }

    /**
     * Return an immutable view of the keywords list.
     * @return the keywords
     */
    @Nullable
    public ImmutableList<String> getKeywords() {
        return keywords;
    }

    /**
     * @return the parent reference.
     */
    @Nullable
    public CaliperEntity getIsPartOf() {
        return isPartOf;
    }

    /**
     * @return date published
     */
    @Nullable
    public DateTime getDatePublished() {
        return datePublished;
    }

    /**
     * @return the version
     */
    @Nullable
    public String getVersion() {
        return version;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T>  {
        private String mediaType;
        private String storageName;
        private List<CaliperAgent> creators = Lists.newArrayList();
        private List<LearningObjective> learningObjectives = Lists.newArrayList();
        private List<String> keywords = Lists.newArrayList();
        private CaliperEntity isPartOf;
        private DateTime datePublished;
        private String version;

        /*
         * Constructor
         */
        public Builder() {
            super.type(EntityType.DIGITAL_RESOURCE);
        }

        /**
         * @param mediaType
         * @return builder.
         */
        public T mediaType(String mediaType) {
            this.mediaType = mediaType;
            return self();
        }

        /**
         * @param storageName
         * @return builder.
         */
        public T storageName(String storageName) {
            this.storageName = storageName;
            return self();
        }

        /**
         * @param creators
         * @return builder.
         */
        public T creators(List<CaliperAgent> creators) {
            if(creators != null) {
                this.creators.addAll(creators);
            }
            return self();
        }

        /**
         * @param creator
         * @return builder.
         */
        public T creator(CaliperAgent creator) {
            this.creators.add(creator);
            return self();
        }

        /**
         * @param learningObjectives
         * @return builder.
         */
        public T learningObjectives(List<LearningObjective> learningObjectives) {
            if(learningObjectives != null) {
                this.learningObjectives.addAll(learningObjectives);
            }
            return self();
        }

        /**
         * @param learningObjective
         * @return builder.
         */
        public T learningObjective(LearningObjective learningObjective) {
            this.learningObjectives.add(learningObjective);
            return self();
        }

        /**
         * @param keywords
         * @return builder.
         */
        public T keywords(List<String> keywords) {
            if(keywords != null) {
                this.keywords.addAll(keywords);
            }
            return self();
        }

        /**
         * @param keyword
         * @return builder.
         */
        public T keyword(String keyword) {
            this.keywords.add(keyword);
            return self();
        }

        /**
         * @param isPartOf
         * @return builder.
         */
        public T isPartOf(CaliperEntity isPartOf) {
            this.isPartOf = isPartOf;
            return self();
        }

        /**
         * @param datePublished
         * @return builder.
         */
        public T datePublished(DateTime datePublished) {
            this.datePublished = datePublished;
            return self();
        }

        /**
         * @param version
         * @return builder.
         */
        public T version(String version) {
            this.version = version;
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