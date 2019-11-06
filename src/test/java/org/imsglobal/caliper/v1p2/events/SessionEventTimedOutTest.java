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
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.SessionEvent;
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
public class SessionEventTimedOutTest {
    private JsonldContext context;
    private String id;
    private SoftwareApplication actor, edApp;
    private Session object;
    private SessionEvent event;

    private static final String BASE_IRI = "https://example.edu";

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value());

        id = "urn:uuid:4e61cf6c-ffbe-45bc-893f-afe7ad4079dc";

        actor = SoftwareApplication.builder().id(BASE_IRI).build();

        object = Session.builder()
            .id(BASE_IRI.concat("/sessions/7d6b88adf746f0692e2e873308b78c60fb13a864"))
            .user(Person.builder().id(BASE_IRI.concat("/users/112233")).build())
            .dateCreated(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .endedAtTime(new DateTime(2016, 11, 15, 11, 15, 0, 0, DateTimeZone.UTC))
            .duration("PT3600S")
            .build();

        edApp = SoftwareApplication.builder().id(actor.getId()).coercedToId(true).build();

        // Build event
        event = buildEvent(Profile.SESSION, Action.TIMED_OUT);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p2/caliperEventSessionTimedOut.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void sessionEventRejectsLikedAction() {
        buildEvent(Profile.SESSION, Action.LIKED);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build SessionEvent.
     * Note that the actor is the edApp and the membership of the actor is not specified.
     * @param profile, action
     * @return event
     */
    private SessionEvent buildEvent(CaliperProfile profile, CaliperAction action) {
        return SessionEvent.builder()
            .context(context)
            .id(id)
            .profile(profile)
            .actor(actor)
            .action(action)
            .object(object)
            .eventTime(new DateTime(2016, 11, 15, 11, 15, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .build();
    }
}