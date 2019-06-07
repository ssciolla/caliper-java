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
import com.google.common.collect.Lists;
import jdk.jfr.events.SocketReadEvent;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.CaliperJsonldContext;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.CaliperEntity;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.resource.DigitalResource;
import org.imsglobal.caliper.entities.search.Query;
import org.imsglobal.caliper.entities.search.SearchResponse;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.SearchEvent;
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
public class SearchEventSearchedTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private Person creator;
    private Query query;
    private List<CaliperEntity> results;
    private SoftwareApplication catalog;
    private SoftwareApplication object;
    private SearchResponse generated;
    private SoftwareApplication edApp;
    private CourseSection group;
    private Membership membership;
    private Session session;
    private SearchEvent event;

    private static final String BASE_IRI = "https://example.edu";
    private static final String BASE_CATALOG_IRI = "https://example.edu/catalog";

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContext.V1P1_SEARCH.value());
        id = "urn:uuid:cb3878ed-8240-4c6d-9fee-77221810f5e4";
        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();
        creator = Person.builder().id(BASE_IRI.concat("/users/554433")).coercedToId(true).build();
        object = SoftwareApplication.builder().id(BASE_CATALOG_IRI).build();
        edApp = SoftwareApplication.builder().id(BASE_IRI).coercedToId(true).build();
        catalog = SoftwareApplication.builder().id(BASE_CATALOG_IRI).coercedToId(true).build();

        query = Query.builder()
            .id(BASE_IRI.concat("/users/554433/search?query=IMS%20AND%20%28Caliper%20OR%20Analytics%29"))
            .creator(creator)
            .searchTarget(catalog)
            .searchTerms("IMS AND (Caliper OR Analytics)")
            .dateCreated(new DateTime(2018, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .build();

        generated = SearchResponse.builder()
            .id(BASE_IRI.concat("/users/554433/response?query=IMS%20AND%20%28Caliper%20OR%20Analytics%29"))
            .searchProvider(edApp)
            .query(query)
            .searchTarget(catalog)
            .searchResultsItemCount(3)
            .build();

        group = CourseSection.builder()
            .id(BASE_IRI.concat("/terms/201801/courses/7/sections/1"))
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
        event = buildEvent(Action.SEARCHED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();

        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p1/caliperEventSearchSearched.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void viewEventRejectsNavigatedToAction() {
        buildEvent(Action.NAVIGATED_TO);
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
    private SearchEvent buildEvent(Action action) {
        return SearchEvent.builder()
                .context(context)
                .id(id)
                .actor(actor)
                .action(action)
                .object(object)
                .eventTime(new DateTime(2018, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
                .generated(generated)
                .edApp(edApp)
                .group(group)
                .membership(membership)
                .session(session)
                .build();
    }
}