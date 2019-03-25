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

package org.imsglobal.caliper.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.outcome.Score;
import org.imsglobal.caliper.entities.resource.Assessment;
import org.imsglobal.caliper.entities.resource.Attempt;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class GradeEventGradedTest {
    private JsonldContext context;
    private String id;
    private SoftwareApplication actor, edApp;
    private Person learner;
    private Attempt object;
    private Assessment assignable;
    private Score generated;
    private CourseSection group;
    private GradeEvent event;

    private static final String BASE_IRI = "https://example.edu";

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.getDefault();

        id = "urn:uuid:a50ca17f-5971-47bb-8fca-4e6e6879001d";

        actor = SoftwareApplication.builder().id(BASE_IRI.concat("/autograder")).version("v2").build();
        learner = Person.builder().id(BASE_IRI.concat("/users/554433")).build();
        assignable = Assessment.builder().id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/assess/1")).build();

        object = Attempt.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/assess/1/users/554433/attempts/1"))
            .assignable(assignable)
            .assignee(learner)
            .count(1)
            .dateCreated(new DateTime(2016, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2016, 11, 15, 10, 55, 12, 0, DateTimeZone.UTC))
            .duration("PT50M12S")
            .build();

        generated = Score.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/assess/1/users/554433/attempts/1/scores/1"))
            .attempt(Attempt.builder().id(object.getId()).coercedToId(true).build())
            .maxScore(15)
            .scoreGiven(10)
            .scoredBy(SoftwareApplication.builder().id(BASE_IRI.concat("/autograder")).coercedToId(true).build())
            .comment("auto-graded exam")
            .dateCreated(new DateTime(2016, 11, 15, 10, 56, 0, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI).coercedToId(true).build();

        group = CourseSection.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
            .courseNumber("CPS 435-01")
            .academicSession("Fall 2016")
            .build();

        // Build Outcome Event
        event = buildEvent(Action.GRADED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p1/caliperEventGradeGraded.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void gradeEventRejectsHidAction() {
        buildEvent(Action.HID);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Grade event.
     * @param action
     * @return event
     */
    private GradeEvent buildEvent(Action action) {
        return GradeEvent.builder()
            .context(context)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .generated(generated)
            .edApp(edApp)
            .group(group)
            .eventTime(new DateTime(2016, 11, 15, 10, 57, 6, 0, DateTimeZone.UTC))
            .build();
    }
}