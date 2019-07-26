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

package org.imsglobal.caliper.entities.resource;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * IMS LTI Message Types
 */
public enum LtiMessageType {
    LTI_DEEP_LINKING_REQUEST("LtiDeepLinkingRequest"),
    LTI_RESOURCE_LINK_REQUEST("LtiResourceLinkRequest");

    private final String value;
    private static Map<String, LtiMessageType> lookup;

    /**
     * Create reverse lookup hash map
     */
    static {
        Map<String, LtiMessageType> map = new HashMap<String, LtiMessageType>();
        for (LtiMessageType constant : LtiMessageType.values()) {
            map.put(constant.value(), constant);
        }
        lookup = ImmutableMap.copyOf(map);
    }

    /**
     * Private constructor
     * @param value
     */
    private LtiMessageType(final String value) {
        this.value = value;
    }

    /**
     * @param key
     * @return true if lookup returns a key match; false otherwise.
     */
    public static boolean hasKey(String key) {
        return lookup.containsKey(key);
    }

    /**
     * @return the URI value
     */
    @JsonValue
    public String value() {
        return value;
    }
}
