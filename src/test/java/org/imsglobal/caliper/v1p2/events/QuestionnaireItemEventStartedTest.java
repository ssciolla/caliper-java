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
import org.imsglobal.caliper.entities.question.RatingScaleQuestion;
import org.imsglobal.caliper.entities.resource.QuestionnaireItem;
import org.imsglobal.caliper.entities.scale.LikertScale;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.QuestionnaireItemEvent;
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
public class QuestionnaireItemEventStartedTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private QuestionnaireItem object;
    private SoftwareApplication edApp;
    private CourseSection group;
    private Membership membership;
    private Session session;
    private QuestionnaireItemEvent event;

    private static final String BASE_IRI = "https://example.edu";
    private static final String ITEM_IRI = BASE_IRI.concat("/surveys/100/questionnaires/30/items/1");
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201801/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value());
        id = "urn:uuid:23995ed4-3c6b-11e9-b210-d663bd873d93";
        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        List<String> itemLabels = Lists.newArrayList();
        itemLabels.add("Strongly Disagree");
        itemLabels.add("Disagree");
        itemLabels.add("Agree");

        List<String> itemValues = Lists.newArrayList();
        itemValues.add("-2");
        itemValues.add("-1");
        itemValues.add("1");

        LikertScale scale = LikertScale.builder()
            .id(BASE_IRI.concat("/scale/2"))
            .scalePoints(4)
            .itemLabels(itemLabels)
            .itemLabel("Strongly Agree")
            .itemValues(itemValues)
            .itemValue("2")
            .build();

        RatingScaleQuestion question = RatingScaleQuestion.builder()
            .id(ITEM_IRI.concat("/question"))
            .questionPosed("How satisfied are you with our services?")
            .scale(scale)
            .build();

        List<String> categories = Lists.newArrayList();
        categories.add("teaching effectiveness");

        object = QuestionnaireItem.builder()
            .id(ITEM_IRI)
            .question(question)
            .categories(categories)
            .category("Course structure")
            .weight(1.0)
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI).coercedToId(true).build();

        group = CourseSection.builder()
            .id(SECTION_IRI)
            .courseNumber("CPS 435-01")
            .academicSession("Fall 2018")
            .build();

        membership = Membership.builder()
            .id(SECTION_IRI.concat("/rosters/1"))
            .role(Role.LEARNER)
            .member(Person.builder().id(BASE_IRI.concat("/users/554433")).coercedToId(true).build())
            .organization(CourseSection.builder().id(SECTION_IRI).coercedToId(true).build())
            .status(Status.ACTIVE)
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_IRI.concat("/sessions/f095bbd391ea4a5dd639724a40b606e98a631823"))
            .startedAtTime(new DateTime(2018, 11, 12, 10, 0, 0, 0, DateTimeZone.UTC))
            .build();

        // Build event
        event = buildEvent(Profile.SURVEY, Action.STARTED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p2/caliperEventQuestionnaireItemStarted.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void questionnaireItemEventRejectsTimedOutAction() {
        buildEvent(Profile.SURVEY, Action.TIMED_OUT);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build QuestionnaireItemEvent.
     * @param profile, action
     * @return event
     */
    private QuestionnaireItemEvent buildEvent(CaliperProfile profile, CaliperAction action) {
        return QuestionnaireItemEvent.builder()
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