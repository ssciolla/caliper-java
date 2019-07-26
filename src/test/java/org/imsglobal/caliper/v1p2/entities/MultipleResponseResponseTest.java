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

package org.imsglobal.caliper.v1p2.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.outcome.Attempt;
import org.imsglobal.caliper.entities.resource.Assessment;
import org.imsglobal.caliper.entities.resource.AssessmentItem;
import org.imsglobal.caliper.entities.response.MultipleResponseResponse;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.Arrays;
import java.util.List;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class MultipleResponseResponseTest {
    private MultipleResponseResponse entity;

    private static final String BASE_IRI = "https://example.edu";
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201601/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {

        Assessment assessment = Assessment.builder()
            .id(SECTION_IRI.concat("/assess/1"))
            .build();

        AssessmentItem assessmentItem = AssessmentItem.builder()
            .id(SECTION_IRI.concat("/assess/1/items/3"))
            .isPartOf(assessment)
            .build();

        Attempt attempt = Attempt.builder()
            .id(SECTION_IRI.concat("/assess/1/items/3/users/554433/attempts/1"))
            .assignee(Person.builder().id(BASE_IRI.concat("/users/554433")).build())
            .assignable(assessmentItem)
            .count(1)
            .startedAtTime(new DateTime(2016, 11, 15, 10, 15, 22, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2016, 11, 15, 10, 15, 30, 0, DateTimeZone.UTC))
            .build();

        String[] valueArray = {"A", "D", "E"};
        List<String> values = Lists.newArrayList();
        values.addAll(Arrays.asList(valueArray));

        entity = MultipleResponseResponse.builder()
            .context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value()))
            .id(SECTION_IRI.concat("/assess/1/items/3/users/554433/responses/1"))
            .attempt(attempt)
            .dateCreated(new DateTime(2016, 11, 15, 10, 15, 22, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 15, 22, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2016, 11, 15, 10, 15, 30, 0, DateTimeZone.UTC))
            .values(values)
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p2/caliperEntityMultipleResponseResponse.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}