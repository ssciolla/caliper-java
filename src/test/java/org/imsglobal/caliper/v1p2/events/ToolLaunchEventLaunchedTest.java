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
import com.google.common.collect.Maps;
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
import org.imsglobal.caliper.entities.resource.LtiMessageType;
import org.imsglobal.caliper.entities.resource.WebPage;
import org.imsglobal.caliper.entities.session.LtiSession;
import org.imsglobal.caliper.entities.session.Session;
import org.imsglobal.caliper.events.ToolLaunchEvent;
import org.imsglobal.caliper.lti.ContextClaim;
import org.imsglobal.caliper.lti.CustomClaim;
import org.imsglobal.caliper.lti.LaunchPresentationClaim;
import org.imsglobal.caliper.lti.LisClaim;
import org.imsglobal.caliper.lti.MessageParameterSession;
import org.imsglobal.caliper.lti.ResourceLinkClaim;
import org.imsglobal.caliper.lti.ToolPlatformClaim;
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
import java.util.Map;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

@Category(org.imsglobal.caliper.UnitTest.class)
public class ToolLaunchEventLaunchedTest {
    private JsonldContext context;
    private String id;
    private Person actor;
    private SoftwareApplication object;
    private WebPage referrer;
    private CourseSection group;
    private SoftwareApplication edApp;
    private Membership membership;
    private Session session;
    private LtiSession federatedSession;
    private ToolLaunchEvent event;

    private static final String BASE_IRI = "https://example.edu";
    private static final String SECTION_IRI = BASE_IRI.concat("/terms/201801/courses/7/sections/1");
    private static final String LTI_IRI = "https://purl.imsglobal.org/spec/lti/claim";
    private static final String LIS_IRI = "http://purl.imsglobal.org/vocab/lis/v2";

    @Before
    public void setUp() throws Exception {
        context = JsonldStringContext.create(CaliperJsonldContextIRI.V1P2.value());
        id = "urn:uuid:a2e8b214-4d4a-4456-bb4c-099945749117";
        actor = Person.builder().id(BASE_IRI.concat("/users/554433")).build();

        object = SoftwareApplication.builder()
            .id("https://example.com/lti/tool")
            .build();

        edApp = SoftwareApplication.builder().id(BASE_IRI).build();

        referrer = WebPage.builder()
            .id(BASE_IRI.concat("/terms/201801/courses/7/sections/1/pages/1"))
            .build();

        group = CourseSection.builder()
            .id(SECTION_IRI)
            .courseNumber("CPS 435-01")
            .academicSession("Fall 2018")
            .build();

        membership = Membership.builder()
            .id(SECTION_IRI.concat("/rosters/1"))
            .member(Person.builder().id(BASE_IRI.concat("/users/554433")).coercedToId(true).build())
            .organization(CourseSection.builder().id(SECTION_IRI).coercedToId(true).build())
            .role(Role.LEARNER)
            .status(Status.ACTIVE)
            .dateCreated(new DateTime(2018, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC))
            .build();

        session = Session.builder()
            .id(BASE_IRI.concat("/sessions/1f6442a482de72ea6ad134943812bff564a76259"))
            .startedAtTime(new DateTime(2018, 11, 15, 10, 0, 0, 0, DateTimeZone.UTC))
            .build();

        Map<String, Object> messageParameters = Maps.newHashMap();
        messageParameters.putAll(getMessageParameters());

        federatedSession = LtiSession.builder()
            .id(BASE_IRI.concat("/lti/sessions/b533eb02823f31024e6b7f53436c42fb99b31241"))
            .user(actor)
            .dateCreated(new DateTime(2018, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .startedAtTime(new DateTime(2018, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .messageParameters(messageParameters)
            .build();

        // Build event
        event = buildEvent(Profile.TOOL_LAUNCH, Action.LAUNCHED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        ObjectMapper mapper = TestUtils.createCaliperObjectMapper();
        String json = mapper.writeValueAsString(event);

        String fixture = jsonFixture("fixtures/v1p2/caliperEventToolLaunchLaunched.json");
        JSONAssert.assertEquals(fixture, json, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void toolLaunchEventRejectsNavigatedToAction() {
        buildEvent(Profile.TOOL_LAUNCH, Action.NAVIGATED_TO);
    }

    @After
    public void teardown() {
        event = null;
    }

    /**
     * Build ToolLaunchEvent.
     * @param profile, action
     * @return event
     */
    private ToolLaunchEvent buildEvent(CaliperProfile profile, CaliperAction action) {
        return ToolLaunchEvent.builder()
            .context(context)
            .id(id)
            .profile(profile)
            .actor(actor)
            .action(action)
            .object(object)
            .eventTime(new DateTime(2018, 11, 15, 10, 15, 0, 0, DateTimeZone.UTC))
            .edApp(edApp)
            .referrer(referrer)
            .group(group)
            .membership(membership)
            .session(session)
            .federatedSession(federatedSession)
            .build();
    }

    /**
     * LTI message parameters
     * @return messageParameters
     */
    private Map<String, Object> getMessageParameters() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("iss", "https://example.edu");
        params.put("sub", "https://example.edu/users/554433");

        List<String> auds = Lists.newArrayList();
        auds.add("https://example.com/lti/tool");
        params.put("aud", auds);

        params.put("exp", 1510185728);
        params.put("iat", 1510185228);
        params.put("azp", "962fa4d8-bcbf-49a0-94b2-2de05ad274af");
        params.put("nonce", "fc5fdc6d-5dd6-47f4-b2c9-5d1216e9b771");
        params.put("name", "Ms Jane Marie Doe");
        params.put("given_name", "Jane");
        params.put("family_name", "Doe");
        params.put("middle_name", "Marie");
        params.put("picture", "https://example.edu/jane.jpg");
        params.put("email", "jane@example.edu");
        params.put("locale", "en-US");
        params.put(LTI_IRI.concat("/deployment_id"), "07940580-b309-415e-a37c-914d387c1150");
        params.put(LTI_IRI.concat("/message_type"), LtiMessageType.LTI_RESOURCE_LINK_REQUEST);
        params.put(LTI_IRI.concat("/version"), "1.3.0");

        List<String> roles = Lists.newArrayList();
        roles.add(LIS_IRI.concat("/institution/person#Student"));
        roles.add(LIS_IRI.concat("/membership#Learner"));
        roles.add(LIS_IRI.concat("/membership#Mentor"));
        params.put(LTI_IRI.concat("/roles"), roles);

        List<String> mentors = Lists.newArrayList();
        mentors.add(LIS_IRI.concat("/institution/person#Administrator"));
        params.put(LTI_IRI.concat("/role_scope_mentor"), mentors);

        List<String> types = Lists.newArrayList();
        types.add(LIS_IRI.concat("/course#CourseSection"));

        ContextClaim contextClaim = ContextClaim.builder()
            .id(SECTION_IRI)
            .label("CPS 435-01")
            .title("CPS 435 Learning Analytics, Section 01")
            .type(types)
            .build();
        params.put(LTI_IRI.concat("/context"), contextClaim);

        ResourceLinkClaim resourceLinkClaim = ResourceLinkClaim.builder()
            .id("200d101f-2c14-434a-a0f3-57c2a42369fd")
            .description("Assignment to introduce who you are")
            .title("Introduction Assignment")
            .build();
        params.put(LTI_IRI.concat("/resource_link"), resourceLinkClaim);

        ToolPlatformClaim toolPlatformClaim = ToolPlatformClaim.builder()
            .guid("https://example.edu")
            .contactEmail("support@example.edu")
            .description("An Example Tool Platform")
            .name("Example Tool Platform")
            .url("https://example.edu")
            .productFamilyCode("ExamplePlatformVendor-Product")
            .version("1.0")
            .build();
        params.put(LTI_IRI.concat("/tool_platform"), toolPlatformClaim);

        LaunchPresentationClaim launchPresentationClaim = LaunchPresentationClaim.builder()
            .documentTarget("iframe")
            .height(320)
            .width(240)
            .returnUrl(SECTION_IRI.concat("/pages/1"))
            .build();
        params.put(LTI_IRI.concat("/launch_presentation"), launchPresentationClaim);

        CustomClaim customClaim = CustomClaim.builder()
            .xstart("2017-04-21T01:00:00Z")
            .requestURL("https://tool.com/link/123")
            .build();
        params.put(LTI_IRI.concat("/custom"), customClaim);

        LisClaim lisClaim = LisClaim.builder()
            .personSourcedId("example.edu:71ee7e42-f6d2-414a-80db-b69ac2defd4")
            .courseOfferingSourcedId("example.edu:SI182-F16")
            .courseSectionSourcedId("example.edu:SI182-001-F16")
            .build();
        params.put(LTI_IRI.concat("/lis"), lisClaim);

        MessageParameterSession messageParamSession = MessageParameterSession.builder()
            .id("89023sj890dju080")
            .build();
        params.put("http://www.ExamplePlatformVendor.com/session", messageParamSession);

        return params;
    }
}