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

package org.imsglobal.caliper.entities.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.EntityType;
import javax.annotation.Nullable;
import java.util.List;

public class MultiselectQuestion extends AbstractQuestion {

    @JsonProperty("points")
    private final int points;

    @JsonProperty("itemLabels")
    private final ImmutableList<String> itemLabels;

    @JsonProperty("itemValues")
    private final ImmutableList<String> itemValues;

    /**
     * @param builder apply builder object properties to the MultiselectQuestion object.
     */
    private MultiselectQuestion(Builder<?> builder) {
        super(builder);
        this.points = builder.points;
        this.itemLabels = ImmutableList.copyOf(builder.itemLabels);
        this.itemValues = ImmutableList.copyOf(builder.itemValues);
    }

    /**
     * @return number of points on MultiselectQuestion
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getPoints() {
        return points;
    }

    /**
     * @return natural language labels for each possible item to be selected
     */
    @Nullable
    public ImmutableList<String> getItemLabels() {
        return itemLabels;
    }

    /**
     * @return values associated with each possible item to be selected
     */
    @Nullable
    public ImmutableList<String> getItemValues() {
        return itemValues;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractQuestion.Builder<T> {
        private int points;
        private List<String> itemLabels = Lists.newArrayList();
        private List<String> itemValues = Lists.newArrayList();

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.MULTISELECT_QUESTION);
        }

        public T points(int points) {
            this.points = points;
            return self();
        }

        public T itemLabels(List<String> itemLabels) {
            if(itemLabels != null) {
                this.itemLabels.addAll(itemLabels);
            }
            return self();
        }

        public T itemValues(List<String> itemValues) {
            if(itemValues != null) {
                this.itemValues.addAll(itemValues);
            }
            return self();
        }

        /**
         * @param itemLabel
         * @return builder.
         */
        public T itemLabel(String itemLabel) {
            this.itemLabels.add(itemLabel);
            return self();
        }

        /**
         * @param itemValue
         * @return builder.
         */
        public T itemValue(String itemValue) {
            this.itemValues.add(itemValue);
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the MultiselectQuestion.
         */
        public MultiselectQuestion build() { return new MultiselectQuestion(this); }
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