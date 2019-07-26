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
import org.imsglobal.caliper.entities.question.MultiselectQuestion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;
import java.util.Arrays;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class MultiselectQuestionTest {
    private MultiselectQuestion entity;

    private static final String BASE_IRI = "https://example.edu";

    @Before
    public void setUp() throws Exception {

        String[] labelArray = {"Calculus", "Number theory", "Combinatorics", "Algebra"};
        List<String> itemLabels = Lists.newArrayList();
        itemLabels.addAll(Arrays.asList(labelArray));

        String[] valueArray = {
            "https://example.edu/terms/201801/courses/7/sections/1/objectives/1",
            "https://example.edu/terms/201801/courses/7/sections/1/objectives/2",
            "https://example.edu/terms/201801/courses/7/sections/1/objectives/3",
            "https://example.edu/terms/201801/courses/7/sections/1/objectives/4"
        };
        List<String> itemValues = Lists.newArrayList();
        itemValues.addAll(Arrays.asList(valueArray));


        entity = MultiselectQuestion.builder()
            .context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value()))
            .id(BASE_IRI.concat("/surveys/100/questionnaires/30/items/4/question"))
            .questionPosed("What do you want to study today?")
            .points(4)
            .itemLabels(itemLabels)
            .itemValues(itemValues)
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p2/caliperEntityMultiselectQuestion.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}