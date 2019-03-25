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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.resource.Document;
import org.imsglobal.caliper.entities.resource.WebPage;
import org.imsglobal.caliper.entities.session.LtiSession;
import org.imsglobal.caliper.entities.session.Session;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;
import java.util.Map;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class ViewEventViewedFedSessionTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private Document object;
    private SoftwareApplication edApp;
    private CourseSection group;
    private Membership membership;
    private WebPage referrer;
    private LtiSession federatedSession;
    private Session session;
    private ViewEvent event;

    private static final String BASE_IRI_COM = "https://example.com";
    private static final String BASE_IRI_EDU = "https://example.edu";

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.getDefault();

        id = "urn:uuid:4be6d29d-5728-44cd-8a8f-3d3f07e46b61";

        actor = Person.builder().id(BASE_IRI_EDU.concat("/users/554433")).build();
        Person actorToId = Person.builder().id(actor.getId()).coercedToId(true).build();

        object = Document.builder()
            .id(BASE_IRI_COM.concat("/lti/reader/202.epub"))
            .mediaType("application/epub+zip")
            .name("Caliper Case Studies")
            .dateCreated(new DateTime(2016, 8, 1, 9, 0, 0, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI_COM).coercedToId(true).build();

        Map<String, Object> groupExtensions = Maps.newHashMap();
        groupExtensions.put("edu_example_course_section_instructor", "https://example.edu/faculty/1234");

        group = CourseSection.builder()
            .id(BASE_IRI_EDU.concat("/terms/201601/courses/7/sections/1"))
            .extensions(groupExtensions)
            .build();

        membership = Membership.builder()
            .id(BASE_IRI_EDU.concat("/terms/201601/courses/7/sections/1/rosters/1"))
            .member(actorToId)
            .organization(CourseSection.builder().id(group.getId()).coercedToId(true).build())
            .status(Status.ACTIVE)
            .role(Role.LEARNER)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_IRI_COM.concat("/sessions/c25fd3da-87fa-45f5-8875-b682113fa5ee"))
            .dateCreated(new DateTime(2016, 11, 15, 10, 20, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 20, 0, 0, DateTimeZone.UTC))
            .build();

        Map<String, Object> messageParameters = getMessageParameters();

        federatedSession = LtiSession.builder()
            .id("urn:uuid:1c519ff7-3dfa-4764-be48-d2fb35a2925a")
            .user(actorToId)
            .messageParameters(messageParameters)
            .dateCreated(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .build();

        // Build event
        event = buildEvent(Action.VIEWED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p1/caliperEventViewViewedFedSession.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void navigationEventRejectsViewedAction() {
        buildEvent(Action.NAVIGATED_TO);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Navigation event
     *
     * @param action
     * @return event
     */
    private ViewEvent buildEvent(Action action) {
        return ViewEvent.builder()
            .context(context)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .eventTime(new DateTime(2016, 11, 15, 10, 20, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .group(group)
            .membership(membership)
            .session(session)
            .federatedSession(federatedSession)
            .build();
    }

    /**
     * LTI message parameters
     * @return messageParameters
     */
    private Map<String, Object> getMessageParameters() {

        Map<String, Object> params = Maps.newHashMap();
        params.put("lti_message_type", "basic-lti-launch-request");
        params.put("lti_version", "LTI-1p0");
        params.put("context_id", "4f1a161f-59c3-43e5-be37-445ad09e3f76");
        params.put("context_type", "urn:lti:context-type:ims/lis/CourseSection");
        params.put("context_label", "SI182");
        params.put("context_title", "Design of Personal Environments");
        params.put("resource_link_id", "6b37a950-42c9-4117-8f4f-03e6e5c88d24");

        List<String> roles = Lists.newArrayList();
        roles.add("urn:lti:role:ims/lis/Learner");

        params.put("roles", roles);
        params.put("tool_consumer_instance_guid", "SomeLMS.example.edu");
        params.put("tool_consumer_instance_description", "Sample University (SomeLMS)");
        params.put("user_id", "0ae836b9-7fc9-4060-006f-27b2066ac545");
        params.put("custom_xstart", "2016-08-21T01:00:00Z");
        params.put("ext_com_somelms_example_course_section_instructor","https://example.edu/faculty/1234");

        return params;
    }
}