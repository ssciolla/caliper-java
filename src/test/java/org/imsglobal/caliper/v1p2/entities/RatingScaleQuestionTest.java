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
import org.fest.util.Lists;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.scale.LikertScale;
import org.imsglobal.caliper.entities.question.RatingScaleQuestion;
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
public class RatingScaleQuestionTest {
    private RatingScaleQuestion entity;
    private LikertScale scale;
    private List<String> labels;
    private List<String> values;

    private static final String BASE_IRI = "https://example.edu";

    @Before
    public void setUp() throws Exception {

        labels = Lists.newArrayList();
        labels.add("Strongly Disagree");
        labels.add("Disagree");
        labels.add("Agree");
        labels.add("Strongly Agree");

        values = Lists.newArrayList();
        values.add("-2");
        values.add("-1");
        values.add("1");
        values.add("2");

        scale = LikertScale.builder()
            .id(BASE_IRI.concat("/scale/2"))
            .scalePoints(4)
            .itemLabels(labels)
            .itemValues(values)
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        entity = RatingScaleQuestion.builder()
            .context(JsonldStringContext.create(CaliperJsonldContext.V1P2.value()))
            .id(BASE_IRI.concat("/question/2"))
            .questionPosed("Do you agree with the opinion presented?")
            .scale(scale)
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p2/caliperEntityRatingScaleQuestion.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}