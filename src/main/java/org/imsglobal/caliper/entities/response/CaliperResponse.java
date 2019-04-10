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

package org.imsglobal.caliper.entities.response;

import org.imsglobal.caliper.entities.CaliperEntity;
import org.imsglobal.caliper.entities.CaliperGeneratable;
import org.imsglobal.caliper.entities.outcome.Attempt;
import org.joda.time.DateTime;

/**
 * The response interface marks an object type that represents a learner's response to an AssessmentItem.
 */
public interface CaliperResponse extends CaliperEntity, CaliperGeneratable {

    /**
     * Attempt contains a count of the number of times an assignee, typically a Person, has interacted with an
     * assignable resource.
     */
    Attempt getAttempt();

    /**
     * A combined date and time representation (including milliseconds) indicating the Response start time, formatted
     * in accordance with the ISO 8601 standard.  Optional.
     * @return startedAtTime
     */
    DateTime getStartedAtTime();

    /**
     * A combined date and time representation (including milliseconds) indicating the Response end time, formatted
     * in accordance with the ISO 8601 standard.  Optional.
     * @return endedAtTime
     */
    DateTime getEndedAtTime();

    /**
     * Session duration formatted in accordance with the ISO 8601 standard.  Optional.
     * @return duration
     */
    String getDuration();
}
