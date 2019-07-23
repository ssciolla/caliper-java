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
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.survey.Survey;
import org.joda.time.DateTime;

import javax.annotation.Nullable;

public class SurveyInvitation extends AbstractDigitalResource {

    @JsonProperty("rater")
    private final Person rater;

    @JsonProperty("survey")
    private final Survey survey;

    @JsonProperty("sentCount")
    private final int sentCount;

    @JsonProperty("dateSent")
    private final DateTime dateSent;

    /**
     * @param builder apply builder object properties to the object.
     */
    private SurveyInvitation(Builder<?> builder) {
        super(builder);
        this.rater = builder.rater;
        this.survey = builder.survey;
        this.sentCount = builder.sentCount;
        this.dateSent = builder.dateSent;
    }

    /**
     * @return rater (Person who will rate a Survey)
     */
    @Nullable
    public Person getRater() { return rater; }

    /**
     * @return the survey that the rater received an invitation for
     */
    @Nullable
    public Survey getSurvey() { return survey; }

    /**
     * @return the count of the times the invitation was sent
     */
    @Nullable
    public int getSentCount() { return sentCount; }

    /**
     * @return the date and time that an invitation was sent
     */
    @Nullable
    public DateTime dateSent() { return dateSent; }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractDigitalResource.Builder<T> {
        private Person rater;
        private Survey survey;
        private int sentCount;
        private DateTime dateSent;

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.SURVEY_INVITATION);
        }

        public T rater(Person rater) {
            this.rater = rater;
            return self();
        }

        public T survey(Survey survey) {
            this.survey = survey;
            return self();
        }

        public T sentCount(int sentCount) {
            this.sentCount = sentCount;
            return self();
        }

        public T dateSent(DateTime dateSent) {
            this.dateSent = dateSent;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the DigitalResource.
         */
        public SurveyInvitation build() {
            return new SurveyInvitation(this);
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