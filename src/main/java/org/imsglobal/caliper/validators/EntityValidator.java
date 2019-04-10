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

package org.imsglobal.caliper.validators;

import com.google.common.base.Strings;
import org.imsglobal.caliper.events.CaliperEventType;
import org.imsglobal.caliper.entities.agent.CaliperAgent;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.Status;
import org.imsglobal.caliper.entities.agent.SupportedStatuses;
import org.imsglobal.caliper.entities.outcome.Attempt;
import org.joda.time.DateTime;

import static com.google.common.base.Preconditions.checkArgument;

public class EntityValidator {

    /**
     * Constructor
     */
    public EntityValidator() {

    }

    /**
     * Check Entity context URI equality.
     * @param context
     * @param expected
     * @throws IllegalArgumentException
     */
    /**
    public static void checkContext(Context context, Context expected) throws IllegalArgumentException {
        checkArgument(context.equals(expected), "expected @context %s but was %s", expected, context);
    }
     */

    /**
     * Check if identifier is null or empty.
     * @param id
     * @throws IllegalArgumentException
     */
    public static void checkId(String name, String id) throws IllegalArgumentException {
        checkArgument(!(Strings.isNullOrEmpty(id)), "%s identifier must be specified", name);
    }

    /**
     * Check Entity type URI equality.
     * @param type
     * @param expected
     * @throws IllegalArgumentException
     */
    public static void checkType(CaliperEventType type, CaliperEventType expected) throws IllegalArgumentException {
        checkArgument(type.value().equals(expected.value()), "expected @type %s but was %s", expected.value(), type);
    }

    /**
     * Check object Type
     * @param actor
     * @param type
     * @throws IllegalArgumentException
     */
    public static void checkActorType(CaliperAgent actor, Class<?> type) throws IllegalArgumentException {
        TypeValidator.checkActorType(actor, type);
    }

    /**
     * Check attempt
     * @param attempt
     * @throws IllegalArgumentException
     */
    public static void checkAttempt(Attempt attempt) throws IllegalArgumentException {
        checkArgument(attempt != null, "attempt must be specified");
    }

    /**
     * Check count
     * @param count
     * @throws IllegalArgumentException
     */
    public static void checkCount(int count) throws IllegalArgumentException {
        checkArgument(count > 0, "count >= 1 must be specified");
    }

    /**
     * Check if Membership status is supported.
     * @param status
     * @throws IllegalArgumentException
     */
    public static void checkMembershipStatus(Status status) throws IllegalArgumentException {
        checkArgument(status != null, "membership status must be specified");

        SupportedStatuses statuses = Membership.class.getAnnotation(SupportedStatuses.class);
        boolean isSupported = false;

        for (Status supportedStatus : statuses.value()) {
            if (supportedStatus.equals(status)) {
                isSupported = true;
                break;
            }
        }
        checkArgument(isSupported, "membership status %s is not supported", status);
    }

    /**
     * Check start time
     * @param start
     * @param end
     * @throws IllegalArgumentException
     */
    public static void checkStartTime(DateTime start, DateTime end) throws IllegalArgumentException {
        TimeValidator.checkStartTime(start, end);
    }

    /**
     * Check end time
     * @param start
     * @param end
     * @throws IllegalArgumentException
     */
    public static void checkEndTime(DateTime start, DateTime end) throws IllegalArgumentException {
        TimeValidator.checkEndTime(start, end);
    }

    /**
     * Check duration format
     * @param duration
     * @throws IllegalArgumentException
     */
    public static void checkDuration(String duration) throws IllegalArgumentException {
        TimeValidator.checkDuration(duration);
    }
}