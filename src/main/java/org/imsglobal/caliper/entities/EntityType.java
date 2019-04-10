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

package org.imsglobal.caliper.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityType implements CaliperEntityType {
    AGENT("Agent"),
    AGGREGATE_MEASURE("AggregateMeasure"),
    AGGREGATE_MEASURE_COLLECTION("AggregateMeasureCollection"),
    ANNOTATION("Annotation"),
    ASSESSMENT("Assessment"),
    ASSESSMENT_ITEM("AssessmentItem"),
    ASSIGNABLE_DIGITAL_RESOURCE("AssignableDigitalResource"),
    ATTEMPT("Attempt"),
    AUDIO_OBJECT("AudioObject"),
    BOOKMARK_ANNOTATION("BookmarkAnnotation"),
    CHAPTER("Chapter"),
    COLLECTION("Collection"),
    COURSE_OFFERING("CourseOffering"),
    COURSE_SECTION("CourseSection"),
    DIGITAL_RESOURCE("DigitalResource"),
    DIGITAL_RESOURCE_COLLECTION("DigitalResourceCollection"),
    DOCUMENT("Document"),
    ENTITY("Entity"),
    EPUB_CHAPTER("EpubChapter"),
    EPUB_PART("EpubPart"),
    EPUB_SUB_CHAPTER("EpubSubChapter"),
    EPUB_VOLUME("EpubVolume"),
    FILLINBLANK("FillinBlankResponse"),
    FORUM("Forum"),
    FRAME("Frame"),
    GROUP("Group"),
    HIGHLIGHT_ANNOTATION("HighlightAnnotation"),
    IMAGE_OBJECT("ImageObject"),
    LEARNING_OBJECTIVE("LearningObjective"),
    LTI_SESSION("LtiSession"),
    MEDIA_LOCATION("MediaLocation"),
    MEDIA_OBJECT("MediaObject"),
    MEMBERSHIP("Membership"),
    MESSAGE("Message"),
    MULTIPLECHOICE("MultipleChoiceResponse"),
    MULTIPLERESPONSE("MultipleResponseResponse"),
    ORGANIZATION("Organization"),
    PAGE("Page"),
    PERSON("Person"),
    QUERY("Query"),
    READING("Reading"),
    RESPONSE("Response"),
    RESULT("Result"),
    SCORE("Score"),
    SEARCHRESPONSE("SearchResponse"),
    SELECTTEXT("SelectTextResponse"),
    SESSION("Session"),
    SHARED_ANNOTATION("SharedAnnotation"),
    SOFTWARE_APPLICATION("SoftwareApplication"),
    TAG_ANNOTATION("TagAnnotation"),
    THREAD("Thread"),
    TRUEFALSE("TrueFalseResponse"),
    VIDEO_OBJECT("VideoObject"),
    WEB_PAGE("WebPage");

    private final String value;

    /**
     * Private constructor
     * @param value
     */
    private EntityType(final String value) {
        this.value = value;
    }

    /**
     * @return URI string
     */
    @JsonValue
    public String value() {
        return value;
    }
}