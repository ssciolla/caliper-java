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

package org.imsglobal.caliper.v1p1.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fest.util.Lists;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.CaliperJsonldContext;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.*;
import org.imsglobal.caliper.entities.resource.DigitalResource;
import org.imsglobal.caliper.entities.resource.DigitalResourceCollection;
import org.imsglobal.caliper.entities.scale.LikertScale;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.entities.survey.Comment;
import org.imsglobal.caliper.entities.survey.Rating;
import org.imsglobal.caliper.entities.question.RatingScaleQuestion;
import org.imsglobal.caliper.events.FeedbackEvent;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.ArrayList;
import java.util.List;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class FeedbackEventRankedTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private CourseSection section;
    private DigitalResource object;
    private Rating generated;
    private DigitalResourceCollection collection;
    private CourseSection group;
    private List<CaliperAgent> creators;
    private Membership membership;
    private RatingScaleQuestion question;
    private Comment ratingComment;
    private LikertScale scale;
    private FeedbackEvent event;
    private Session session;
    private SoftwareApplication edApp;

    private static final String BASE_IRI = "https://example.edu";
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201801/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContext.V1P1_FEEDBACK.value());
        id = "urn:uuid:a502e4fc-24c1-11e9-ab14-d663bd873d93";
        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();
        creators = new ArrayList<CaliperAgent>();
        creators.add(actor);
        section = CourseSection.builder().id(SECTION_IRI).build();

        collection = DigitalResourceCollection.builder()
            .id(SECTION_IRI.concat("/resources/1"))
            .name("Course Assets")
            .isPartOf(section)
            .build();

        object = DigitalResource.builder()
            .id(SECTION_IRI.concat("/resources/1/syllabus.pdf"))
            .name("Course Syllabus")
            //.creators(creators)
            .mediaType("application/pdf")
            .isPartOf(collection)
            .dateCreated(new DateTime(2018, 8, 2, 11, 32, 0, 0, DateTimeZone.UTC))
            .build();

        List<String> itemLabels = Lists.newArrayList();
        itemLabels.add("Strongly Disagree");
        itemLabels.add("Disagree");
        itemLabels.add("Agree");
        itemLabels.add("Strongly Agree");

        List<String> itemValues = Lists.newArrayList();
        itemValues.add("-2");
        itemValues.add("-1");
        itemValues.add("1");
        itemValues.add("2");

        scale = LikertScale.builder()
            .id(BASE_IRI.concat("/scale/2"))
            .scalePoints(4)
            .itemLabels(itemLabels)
            .itemValues(itemValues)
            .build();

        question = RatingScaleQuestion.builder()
            .id(BASE_IRI.concat("/question/2"))
            .questionPosed("Do you agree with the opinion presented?")
            .scale(scale)
            .build();

        ratingComment = Comment.builder()
            .id(SECTION_IRI.concat("/assess/1/items/6/users/665544/responses/1/comment/1"))
            .commenter(actor)
            .commentedOn(object)
            .value("I like what you did here but you need to improve on...")
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        generated = Rating.builder()
            .id(BASE_IRI.concat("/users/554433/rating/1"))
            .rater(actor)
            .rated(object)
            .question(question)
            .selection("1")
            .ratingComment(ratingComment)
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI).coercedToId(true).build();

        group = CourseSection.builder()
            .id(SECTION_IRI)
            .courseNumber("CPS 435-01")
            .academicSession("Fall 2018")
            .build();

        membership = Membership.builder()
            .id(BASE_IRI.concat("/terms/201801/courses/7/sections/1/rosters/1"))
            .member(Person.builder().id(actor.getId()).coercedToId(true).build())
            .organization(CourseSection.builder().id(group.getId()).coercedToId(true).build())
            .status(Status.ACTIVE)
            .role(Role.LEARNER)
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
            .startedAtTime(new DateTime(2018, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
            .build();

        // Build event
        event = buildEvent(Action.RANKED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p1/caliperEventFeedbackRanked.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
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
    private FeedbackEvent buildEvent(Action action) {
        return FeedbackEvent.builder()
            .context(context)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .generated(generated)
            .eventTime(new DateTime(2018, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .group(group)
            .membership(membership)
            .session(session)
            .build();
    }
}