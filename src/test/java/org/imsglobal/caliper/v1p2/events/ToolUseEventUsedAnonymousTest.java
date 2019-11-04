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
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.actions.CaliperAction;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.EntityType;
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

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class ToolUseEventUsedAnonymousTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private SoftwareApplication object, edApp;
    private CourseSection group;
    private Membership membership;
    private ToolUseEvent event;

    private static final String BASE_IRI = "https://example.edu";

    @Before
    public void setUp() throws Exception {

        CourseSection anonymousSection = CourseSection.builder()
            .id(EntityType.COURSE_SECTION.expandToIRI())
            .build();

        Person anonymousPerson = Person.builder().id(EntityType.PERSON.expandToIRI()).build();

        context = JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value());

        id = "urn:uuid:7c0fc54b-cf2a-426f-9203-b2c97fb77bfd";

        actor = anonymousPerson;

        object = SoftwareApplication.builder().id(BASE_IRI).build();

        edApp = SoftwareApplication.builder().id(BASE_IRI).coercedToId(true).build();

        group = anonymousSection;

        membership = Membership.builder()
            .id(EntityType.MEMBERSHIP.expandToIRI())
            .member(anonymousPerson)
            .organization(anonymousSection)
            .status(Status.ACTIVE)
            .role(Role.LEARNER)
            .build();

        // Build event
        event = buildEvent(Profile.TOOL_USE, Action.USED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p2/caliperEventToolUseUsedAnonymous.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Tool Use Event
     * @params profile, action
     * @return event
     */
    private ToolUseEvent buildEvent(CaliperProfile profile, CaliperAction action) {
        return ToolUseEvent.builder()
            .context(context)
            .id(id)
            .profile(profile)
            .actor(actor)
            .action(action)
            .object(object)
            .eventTime(new DateTime(2018, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .group(group)
            .membership(membership)
            .build();
    }
}