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

package org.imsglobal.caliper.profiles;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Caliper action vocabulary.
 */
public enum Profile implements CaliperProfile {
    ANNOTATION("AnnotationProfile"),
    ASSESSMENT("AssessmentProfile"),
    ASSIGNABLE("AssignableProfile"),
    FEEDBACK("FeedbackProfile"),
    FORUM("ForumProfile"),
    GENERAL("GeneralProfile"),
    GRADING("GradingProfile"),
    MEDIA("MediaProfile"),
    READING("ReadingProfile"),
    RESOURCE_MANAGEMENT("ResourceManagementProfile"),
    SEARCH("SearchProfile"),
    SESSION("SessionProfile"),
    SURVEY("SurveyProfile"),
    TOOL_LAUNCH("ToolLaunchProfile"),
    TOOL_USE("ToolUseProfile");

    private String value;

    /**
     * Constructor
     * @param value
     */
    private Profile(String value){
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