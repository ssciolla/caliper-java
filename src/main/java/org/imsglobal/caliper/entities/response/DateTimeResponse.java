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

package org.imsglobal.caliper.entities.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.EntityType;
import org.joda.time.DateTime;
import javax.annotation.Nullable;

public class DateTimeResponse extends AbstractResponse {

    @JsonProperty("dateTimeSelected")
    private final DateTime dateTimeSelected;

    /**
     * @param builder apply builder object properties to the Response object.
     */
    private DateTimeResponse(Builder<?> builder) {
        super(builder);
        this.dateTimeSelected = builder.dateTimeSelected;
    }

    /**
     * @return date and time selected as part of the response
     */
    @Nullable
    public DateTime getDateTimeSelected() {
        return dateTimeSelected;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractResponse.Builder<T>  {
        private DateTime dateTimeSelected;

        /**
         * Constructor
         */
        public Builder() { super.type(EntityType.DATE_TIME_RESPONSE); }

        public T dateTimeSelected(DateTime dateTimeSelected) {
            this.dateTimeSelected = dateTimeSelected;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of DateTimeResponse.
         */
        public DateTimeResponse build() {
            return new DateTimeResponse(this);
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