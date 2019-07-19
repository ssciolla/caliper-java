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

package org.imsglobal.caliper.v1p2.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fest.util.Lists;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.actions.CaliperAction;
import org.imsglobal.caliper.context.CaliperJsonldContext;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.*;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.entities.use.AggregateMeasure;
import org.imsglobal.caliper.entities.use.AggregateMeasureCollection;
import org.imsglobal.caliper.entities.use.Metric;
import org.imsglobal.caliper.events.ToolUseEvent;
import org.imsglobal.caliper.profiles.CaliperProfile;
import org.imsglobal.caliper.profiles.Profile;
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
public class ToolUseEventUsedWithProgressTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private SoftwareApplication object, edApp;
    private AggregateMeasureCollection generated;
    private AggregateMeasure minutesOnTask;
    private AggregateMeasure unitsCompleted;
    private CourseOffering course;
    private CourseSection group, organization;
    private Membership membership;
    private Session session;
    private ToolUseEvent event;

    private static final String BASE_IRI = "https://example.edu";
    private static final String BASE_COURSE_IRI = "https://example.edu/terms/201601/courses/7";

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContext.V1P2.value());

        id = "urn:uuid:7e10e4f3-a0d8-4430-95bd-783ffae4d916";

        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        object = SoftwareApplication.builder().id(BASE_IRI).build();

        edApp = SoftwareApplication.builder().id(object.getId()).coercedToId(true).build();

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

        generated = AggregateMeasureCollection.builder()
            .id("urn:uuid:7e10e4f3-a0d8-4430-95bd-783ffae4d912")
            .items(measures)
            .build();

        course = CourseOffering.builder()
            .id(BASE_COURSE_IRI)
            .courseNumber("CPS 435")
            .build();

        group = CourseSection.builder()
            .id(BASE_COURSE_IRI.concat("/sections/1"))
            .courseNumber("CPS 435-01")
            .name("CPS 435 Learning Analytics, Section 01")
            .category("seminar")
            .academicSession("Fall 2016")
            .subOrganizationOf(course)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        organization = CourseSection.builder()
            .id(BASE_COURSE_IRI.concat("/sections/1"))
            .subOrganizationOf(CourseOffering.builder().id(course.getId()).build())
            .build();

        membership = Membership.builder()
            .id(BASE_COURSE_IRI.concat("/sections/1/rosters/1/members/554433"))
            .member(actor)
            .organization(organization)
            .status(Status.ACTIVE)
            .role(Role.LEARNER)
            .dateCreated(new DateTime(2016, 11, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
            .user(actor)
            .startedAtTime(new DateTime(2016, 9, 15, 10, 0, 0, 0, DateTimeZone.UTC))
            .build();

        // Build event
        event = buildEvent(Profile.TOOL_USE, Action.USED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p2/caliperEventToolUseUsedWithProgress.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void viewEventRejectsNavigatedToAction() {
        buildEvent(Profile.TOOL_USE, Action.NAVIGATED_TO);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build View event
     * @param action
     * @return event
     */
    private ToolUseEvent buildEvent(CaliperProfile profile, CaliperAction action) {
        return ToolUseEvent.builder()
            .context(context)
            .profile(profile)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .eventTime(new DateTime(2019, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .generated(generated)
            .group(group)
            .membership(membership)
            .session(session)
            .build();
    }
}