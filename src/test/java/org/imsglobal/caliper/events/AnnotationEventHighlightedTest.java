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
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.annotation.HighlightAnnotation;
import org.imsglobal.caliper.entities.resource.Document;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.selectors.TextPositionSelector;
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
public class AnnotationEventHighlightedTest {
    private JsonldStringContext context;
    private String id;
    private Person actor;
    private Document object;
    private HighlightAnnotation generated;
    private SoftwareApplication edApp;
    private CourseSection group;
    private Membership membership;
    private Session session;
    private AnnotationEvent event;

    private static final String BASE_EDU_IRI = "https://example.edu";
    private static final String BASE_COM_IRI = "https://example.com";
    private static final String SECTION_IRI = BASE_EDU_IRI.concat("/terms/201601/courses/7/sections/1");

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.getDefault();

        id = "urn:uuid:0067a052-9bb4-4b49-9d1a-87cd43da488a";

        actor = Person.builder().id(BASE_EDU_IRI.concat("/users/554433")).build();
        Person annotator = Person.builder().id(actor.getId()).coercedToId(true).build();

        object = Document.builder()
            .id(BASE_COM_IRI.concat("/#/texts/imscaliperimplguide"))
            .name("IMS Caliper Implementation Guide")
            .dateCreated(new DateTime(2016, 10, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .version("1.1")
            .build();

        generated = HighlightAnnotation.builder()
            .id(BASE_COM_IRI.concat("/users/554433/texts/imscaliperimplguide/highlights?start=2300&end=2370"))
            .annotated(Document.builder().id(object.getId()).coercedToId(true).build())
            .annotator(annotator)
            .selection(new TextPositionSelector(2300, 2370))
            .selectionText("ISO 8601 formatted date and time expressed with millisecond precision.")
            .dateCreated(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .build();

        edApp = SoftwareApplication.builder()
            .id(BASE_COM_IRI.concat("/reader"))
            .name("ePub Reader")
            .version("1.2.3").build();

        group = CourseSection.builder()
            .id(BASE_EDU_IRI.concat("/terms/201601/courses/7/sections/1"))
            .courseNumber("CPS 435-01")
            .academicSession("Fall 2016")
            .build();

        membership = Membership.builder()
            .id(SECTION_IRI.concat("/rosters/1"))
            .member(annotator)
            .organization(CourseSection.builder().id(group.getId()).coercedToId(true).build())
            .status(Status.ACTIVE)
            .role(Role.LEARNER)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_COM_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
            .startedAtTime(new DateTime(2016, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
            .build();

        // Build event
        event = buildEvent(Action.HIGHLIGHTED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p1/caliperEventAnnotationHighlighted.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void annotationEventRejectsSearchedAction() {
        buildEvent(Action.SEARCHED);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build Annotation event.
     * @param action
     * @return event
     */
    private AnnotationEvent buildEvent(Action action) {
        return AnnotationEvent.builder()
            .context(context)
            .id(id)
            .actor(actor)
            .action(action)
            .object(object)
            .generated(generated)
            .eventTime(new DateTime(2016, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .group(group)
            .membership(membership)
            .session(session)
            .build();
    }
}