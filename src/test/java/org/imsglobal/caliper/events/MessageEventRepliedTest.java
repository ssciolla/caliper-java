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
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.resource.Forum;
import org.imsglobal.caliper.entities.resource.Message;
import org.imsglobal.caliper.entities.resource.Thread;
import org.imsglobal.caliper.entities.session.Session;
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
public class MessageEventRepliedTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private Message object;
    private SoftwareApplication edApp;
    private CourseSection group;
    private Membership membership;
    private Session session;
    private MessageEvent event;

    private static final String BASE_IRI = "https://example.edu";
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201601/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.getDefault();

        id = "urn:uuid:aed54386-a3fb-45ff-90f9-a35d3daaf031";

        actor = Person.builder().id(BASE_IRI.concat("/users/778899")).build();

        Forum forum = Forum.builder()
            .id(SECTION_IRI.concat("/forums/2"))
            .build();

        Thread thread = Thread.builder()
            .id(forum.getId().concat("/topics/1"))
            .isPartOf(forum)
            .build();

        Message reply = Message.builder().id(SECTION_IRI.concat("/forums/2/topics/1/messages/2")).build();

        object = Message.builder()
            .id(SECTION_IRI.concat("/forums/2/topics/1/messages/3"))
            .creator(actor)
            .replyTo(reply)
            .isPartOf(thread)
            .dateCreated(new DateTime(2016, 11, 15, 10, 15, 30, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI.concat("/forums")).version("v2").build();

        group = CourseSection.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
            .courseNumber("CPS 435-01")
            .academicSession("Fall 2016")
            .build();

        membership = Membership.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/rosters/1"))
            .member(Person.builder().id(actor.getId()).coercedToId(true).build())
            .organization(CourseSection.builder().id(group.getId()).coercedToId(true).build())
            .status(Status.ACTIVE)
            .role(Role.LEARNER)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_IRI.concat("/sessions/1d6fa9adf16f4892650e4305f6cf16610905cd50"))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 12, 0, 0, DateTimeZone.UTC))
            .build();

        // Build event
        event = buildEvent(Action.POSTED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p1/caliperEventMessageReplied.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void messageEventRejectsStartedAction() {
        buildEvent(Action.STARTED);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Media event.
     * @param action
     * @return event
     */
    private MessageEvent buildEvent(Action action) {
        return MessageEvent.builder()
            .context(context)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .eventTime(new DateTime(2016, 11, 15, 10, 15, 30, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .group(group)
            .membership(membership)
            .session(session)
            .build();
    }
}