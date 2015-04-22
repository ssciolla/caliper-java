package org.imsglobal.caliper.events;

import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.entities.LearningContext;
import org.imsglobal.caliper.TestAgentEntities;
import org.imsglobal.caliper.TestAssessmentEntities;
import org.imsglobal.caliper.TestDates;
import org.imsglobal.caliper.TestLisEntities;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.assessment.Assessment;
import org.imsglobal.caliper.entities.assessment.AssessmentItem;
import org.imsglobal.caliper.entities.assignable.Attempt;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.junit.Assert.assertEquals;

@Category(org.imsglobal.caliper.UnitTest.class)
public class AssessmentItemStartedEventTest extends EventTest {
    private LearningContext learningContext;
    private Assessment assessment;
    private AssessmentItem object;
    private Attempt generated;
    private AssessmentItemEvent event;
    private DateTime dateCreated = TestDates.getDefaultDateCreated();
    private DateTime dateStarted = TestDates.getDefaultStartedAtTime();
    // private static final Logger log = LoggerFactory.getLogger(AssessmentItemStartedEventTest.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        // Build the Learning Context
        learningContext = LearningContext.builder()
            .edApp(TestAgentEntities.buildAssessmentApp())
            .group(TestLisEntities.buildGroup())
            .agent(TestAgentEntities.buildStudent554433())
            .build();

        // Build assessment
        assessment = TestAssessmentEntities.buildAssessment();

        // Build assessment and get assessment item 1
        object = TestAssessmentEntities.buildAssessment().getAssessmentItems().get(0);

        // Generate attempt
        generated = Attempt.builder()
            .id(assessment.getId() + "/item1/attempt1")
            .assignable(assessment)
            .actor(((Person) learningContext.getAgent()))
            .count(1)
            .dateCreated(dateCreated)
            .startedAtTime(dateStarted)
            .build();

        // Build event
        event = buildEvent(Action.STARTED);
    }

    @Test
    public void caliperEventSerializesToJSON() throws Exception {
        assertEquals("Test if AssessmentItem Start event is serialized to JSON with expected values",
            jsonFixture("fixtures/caliperAssessmentItemStartedEvent.json"), serialize(event));
    }

    @Test(expected=IllegalArgumentException.class)
    public void assessmentItemEventRejectsChangedVolumeAction() {
        buildEvent(Action.CHANGED_VOLUME);
    }

    /**
     * Build AssessmentItem event.
     * @param action
     * @return event
     */
    private AssessmentItemEvent buildEvent(Action action) {
        return AssessmentItemEvent.builder()
            .edApp(learningContext.getEdApp())
            .group(learningContext.getGroup())
            .actor((Person) learningContext.getAgent())
            .action(action)
            .object(object)
            .generated(generated)
            .startedAtTime(dateStarted)
            .build();
    }
}