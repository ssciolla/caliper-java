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
import org.imsglobal.caliper.entities.agent.CourseOffering;
import org.imsglobal.caliper.entities.agent.CourseSection;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.agent.Role;
import org.imsglobal.caliper.entities.agent.Status;
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
public class MembershipTest {
    private Membership entity;
    private Person person;
    private CourseOffering courseOffering;
    private CourseSection courseSection;
    private List<Role> roles;

    private static final String BASE_IRI = "https://example.edu";

    @Before
    public void setUp() throws Exception {

        person = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        courseOffering = CourseOffering.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7"))
            .build();

        courseSection = CourseSection.builder()
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1"))
            .subOrganizationOf(courseOffering)
            .build();

        roles = Lists.newArrayList();
        roles.add(Role.LEARNER);

        entity = Membership.builder()
            .context(JsonldStringContext.create(CaliperJsonldContext.V1P1.value()))
            .id(BASE_IRI.concat("/terms/201601/courses/7/sections/1/rosters/1/members/554433"))
            .member(person)
            .organization(courseSection)
            .roles(roles)
            .status(Status.ACTIVE)
            .dateCreated(new DateTime(2016, 11, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();
    }

    @Test
    public void caliperEntitySerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(entity);

        String fixture = jsonFixture("fixtures/v1p1/caliperEntityMembership.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @After
    public void teardown() {
        entity = null;
    }
}