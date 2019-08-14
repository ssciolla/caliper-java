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

package org.imsglobal.caliper.v1p2.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.imsglobal.caliper.TestUtils;
import org.imsglobal.caliper.context.CaliperJsonldContextIRI;
import org.imsglobal.caliper.context.JsonldStringContext;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.identifiers.SystemIdentifier;
import org.imsglobal.caliper.identifiers.SystemIdentifierType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class PersonTest {
    private Person entity;

    private static final String BASE_IRI = "https://example.edu";
    private static final String USER_IRI = BASE_IRI.concat("/users/554433");

    @Before
    public void setUp() throws Exception {

        SystemIdentifier identifierOne = SystemIdentifier.builder()
            .identifier("example.edu:71ee7e42-f6d2-414a-80db-b69ac2defd4")
            .identifierType(SystemIdentifierType.LIS_SOURCED_ID)
            .build();

        SystemIdentifier identifierTwo = SystemIdentifier.builder()
            .identifier(USER_IRI)
            .identifierType(SystemIdentifierType.LTI_USER_ID)
            .source(SoftwareApplication.builder().id(BASE_IRI).build())
            .build();

        SystemIdentifier identifierThree = SystemIdentifier.builder()
            .identifier("jane@example.edu")
            .identifierType(SystemIdentifierType.EMAIL_ADDRESS)
            .source(SoftwareApplication.builder().id(BASE_IRI).coercedToId(true).build())
            .build();

        Map<String, Object> extensionsForFour = Maps.newHashMap();
        extensionsForFour.put("com.examplePlatformVendor.identifier_type", "UserIdentifier");

        SystemIdentifier identifierFour = SystemIdentifier.builder()
            .identifier("4567")
            .identifierType(SystemIdentifierType.SYSTEM_ID)
            .extensions(extensionsForFour)
            .build();

        SystemIdentifier[] systemIdentifierArray = { identifierOne,  identifierTwo, identifierThree, identifierFour };

        List<SystemIdentifier> otherIdentifiers = Lists.newArrayList();
        otherIdentifiers.addAll(Arrays.asList(systemIdentifierArray));

        entity = Person.builder()
            .context(JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value()))
            .id(BASE_IRI.concat("/users/554433"))
            .otherIdentifiers(otherIdentifiers)
            .dateCreated(new DateTime(2016, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .dateModified(new DateTime(2016, 9, 2, 11, 30, 0, 0, DateTimeZone.UTC))
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p2/caliperEntityPerson.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}

