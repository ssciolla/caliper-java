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

package org.imsglobal.caliper.v1p1.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContext;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.CaliperEntity;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.resource.*;
import org.imsglobal.caliper.entities.search.Query;
import org.imsglobal.caliper.entities.search.SearchResponse;
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
public class SearchResponseTest {
    private Person creator;
    private SoftwareApplication catalog;
    private SoftwareApplication provider;
    private SoftwareApplication target;
    private Query query;
    private SearchResponse entity;
    private List<CaliperEntity> results;
    private Document epub;
    private Document pdf;
    private VideoObject video;

    private static final String BASE_IRI = "https://example.edu";
    private static final String BASE_CATALOG_IRI = "https://example.edu/catalog";

    @Before
    public void setUp() throws Exception {
        catalog = SoftwareApplication.builder().id(BASE_CATALOG_IRI).coercedToId(true).build();
        creator = Person.builder().id(BASE_IRI.concat("/users/554433")).build();
        provider = SoftwareApplication.builder().id(BASE_IRI).build();
        target = SoftwareApplication.builder().id(BASE_CATALOG_IRI).build();

        query = Query.builder()
            .id(BASE_IRI.concat("/users/554433/search?query=IMS%20AND%20%28Caliper%20OR%20Analytics%29"))
            .creator(creator)
            .searchTarget(catalog)
            .searchTerms("IMS AND (Caliper OR Analytics)")
            .dateCreated(new DateTime(2018, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .build();

        pdf = Document.builder()
            .id(BASE_CATALOG_IRI.concat("/record/01234?query=IMS%20AND%20%28Caliper%20OR%20Analytics%29"))
            .mediaType("application/pdf")
            .build();

        video = VideoObject.builder()
            .id(BASE_CATALOG_IRI.concat("/record/09876?query=IMS%20AND%20%28Caliper%20OR%20Analytics%29"))
            .mediaType("video/ogg")
            .build();

        epub = Document.builder()
            .id(BASE_CATALOG_IRI.concat("/record/05432?query=IMS%20AND%20%28Caliper%20OR%20Analytics%29"))
            .mediaType("application/epub+zip")
            .build();

        results = Lists.newArrayList();
        results.add(pdf);
        results.add(video);
        results.add(epub);

        entity = SearchResponse.builder()
            .context(JsonldStringContext.create(CaliperJsonldContext.V1P1_SEARCH.value()))
            .id(BASE_IRI.concat("/users/554433/response?query=IMS%20AND%20%28Caliper%20OR%20Analytics%29"))
            .searchProvider(provider)
            .query(query)
            .searchTarget(target)
            .searchResultsItemCount(3)
            .searchResults(results)
            .dateCreated(new DateTime(2018, 11, 15, 10, 5, 0, 0, DateTimeZone.UTC))
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p1/caliperEntitySearchResponse.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}


