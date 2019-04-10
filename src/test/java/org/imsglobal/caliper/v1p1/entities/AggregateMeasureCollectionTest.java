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

package org.imsglobal.caliper.v1p1.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fest.util.Lists;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.use.AggregateMeasure;
import org.imsglobal.caliper.entities.use.AggregateMeasureCollection;
import org.imsglobal.caliper.entities.use.Metric;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class AggregateMeasureCollectionTest {
    private AggregateMeasureCollection entity;
    private AggregateMeasure unitsCompleted;
    private AggregateMeasure minutesOnTask;

    private static final String BASE_IRI = "https://example.edu";
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201601/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {

        minutesOnTask = AggregateMeasure.builder()
            .id("urn:uuid:21c3f9f2-a9ef-4f65-bf9a-0699ed85e2c7")
            .metric(Metric.MINUTES_0N_TASK)
            .name("Minutes On Task")
            .metricValue(873.0)
            .startedAtTime(new DateTime(2019, 8, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2019, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .build();

        unitsCompleted = AggregateMeasure.builder()
            .id("urn:uuid:c3ba4c01-1f17-46e0-85dd-1e366e6ebb81")
            .metric(Metric.UNITS_COMPLETED)
            .name("Units Completed")
            .metricValue(12.0)
            .maxMetricValue(25.0)
            .startedAtTime(new DateTime(2019, 8, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2019, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .build();

        List<AggregateMeasure> measures = Lists.newArrayList();
        measures.add(minutesOnTask);
        measures.add(unitsCompleted);

        entity = AggregateMeasureCollection.builder()
            .context(JsonldStringContext.create(CaliperJsonldContext.V1P1_TOOL_USE.value()))
            .id("urn:uuid:60b4db01-f1e5-4a7f-add9-6a8f761625b1")
            .items(measures)
            .build();

    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p1/caliperEntityAggregateMeasureCollection.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}