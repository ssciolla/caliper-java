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

package org.imsglobal.caliper.entities.use;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.*;

import javax.annotation.Nullable;
import java.util.List;

public class AggregateMeasureCollection extends AbstractEntity implements CaliperCollection<AggregateMeasure>, CaliperGeneratable {

    @JsonProperty("items")
    private final ImmutableList<AggregateMeasure> items;

    /**
     * @param builder apply builder object properties to the object.
     */
    protected AggregateMeasureCollection(Builder<?> builder) {
        super(builder);
        this.items = ImmutableList.copyOf(builder.items);
    }

    /**
     * Return an immutable list of the Collection's items.
     * @return the items
     */
    @Override
    @Nullable
    public ImmutableList<AggregateMeasure> getItems() {
        return items;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T> {
        private List<AggregateMeasure> items = Lists.newArrayList();

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.AGGREGATE_MEASURE_COLLECTION);
        }

        /**
         * @param items
         * @return builder.
         */
        public T items(List<AggregateMeasure> items) {
            if(items != null) {
                this.items.addAll(items);
            }
            return self();
        }

        /**
         * @param item
         * @return builder.
         */
        public T item(AggregateMeasure item) {
            this.items.add(item);
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the AggregateMeasureCollection.
         */
        public AggregateMeasureCollection build() {
            return new AggregateMeasureCollection(this);
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