package org.imsglobal.caliper.entities.use;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Metric implements CaliperMetric {
    ASSESSMENTS_SUBMITTED("AssessmentsSubmitted"),
    ASSESSMENTS_PASSED("AssessmentsPassed"),
    MINUTES_0N_TASK("MinutesOnTask"),
    SKILLS_MASTERED("SkillsMastered"),
    STANDARDS_MASTERED("StandardsMastered"),
    UNITS_COMPLETED("UnitsCompleted"),
    UNITS_PASSED("UnitsPassed"),
    WORDS_READ("WordsRead");

    private String value;

    /**
     * Constructor
     * @param value
     */
    private Metric(String value){
        this.value = value;
    }

    /**
     * Enum string value.
     * @return string value.
     */
    @Override
    @JsonValue
    public String value() {
        return value;
    }
}
