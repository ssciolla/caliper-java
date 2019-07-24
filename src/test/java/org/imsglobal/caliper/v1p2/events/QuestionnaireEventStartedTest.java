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
import com.google.common.collect.Lists;
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
import org.imsglobal.caliper.entities.resource.Questionnaire;
import org.imsglobal.caliper.entities.resource.QuestionnaireItem;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.QuestionnaireEvent;
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
public class QuestionnaireEventStartedTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private Questionnaire object;
    private CourseSection group;
    private SoftwareApplication edApp;
    private Membership membership;
    private Session session;
    private QuestionnaireEvent event;

    private static final String BASE_IRI = "https://example.edu";
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201801/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value());
        id = "urn:uuid:23995ed4-3c6b-11e9-b210-d663bd873d93";
        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        QuestionnaireItem itemOne = QuestionnaireItem.builder()
            .id(BASE_IRI.concat("/surveys/100/questionnaires/30/items/1"))
            .build();

        QuestionnaireItem itemTwo = QuestionnaireItem.builder()
            .id(BASE_IRI.concat("/surveys/100/questionnaires/30/items/2"))
            .build();

        List<QuestionnaireItem> items = Lists.newArrayList();
        items.add(itemOne);
        items.add(itemTwo);

        object = Questionnaire.builder()
            .id(BASE_IRI.concat("/surveys/100/questionnaires/30"))
            .items(items)
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        group = CourseSection.builder()
            .id(BASE_IRI.concat("/terms/201801/courses/7/sections/1"))
            .courseNumber("CPS 435-01")
            .academicSession("Fall 2018")
            .build();

        List<Role> roles = Lists.newArrayList();
        roles.add(Role.LEARNER);

        membership = Membership.builder()
            .id(SECTION_IRI.concat("/rosters/1"))
            .roles(roles)
            .member(Person.builder().id(BASE_IRI.concat("/users/554433")).coercedToId(true).build())
            .organization(CourseSection.builder().id(SECTION_IRI).coercedToId(true).build())
            .status(Status.ACTIVE)
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_IRI.concat("/sessions/f095bbd391ea4a5dd639724a40b606e98a631823"))
            .startedAtTime(new DateTime(2018, 11, 12, 10, 0, 0, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI).coercedToId(true).build();

        // Build event
        event = buildEvent(Profile.SURVEY, Action.STARTED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p2/caliperEventQuestionnaireStarted.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build QuestionnaireEvent.
     * @param profile, action
     * @return event
     */
    private QuestionnaireEvent buildEvent(CaliperProfile profile, CaliperAction action) {
        return QuestionnaireEvent.builder()
            .context(context)
            .id(id)
            .profile(profile)
            .actor(actor)
            .action(action)
            .object(object)
            .eventTime(new DateTime(2018, 11, 12, 10, 15, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .group(group)
            .membership(membership)
            .session(session)
            .build();
    }
}