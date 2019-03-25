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

package org.imsglobal.caliper.context;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public enum CaliperJsonldContext {
    V1P0("http://purl.imsglobal.org/ctx/caliper/v1/Context"),
    V1P1("http://purl.imsglobal.org/ctx/caliper/v1p1"),
    V1P1_FEEDBACK("http://purl.imsglobal.org/ctx/caliper/v1p1/FeedbackProfile-extension"),
    V1P1_RESOURCE_MANAGEMENT("http://purl.imsglobal.org/ctx/caliper/v1p1/ResourceManagementProfile-extension"),
    V1P1_SEARCH("http://purl.imsglobal.org/ctx/caliper/v1p1/SearchProfile-extension"),
    V1P1_TOOL_LAUNCH("http://purl.imsglobal.org/ctx/caliper/v1p1/ToolLaunchProfile-extension"),
    V1P1_TOOL_USE("http://purl.imsglobal.org/ctx/caliper/v1p1/ToolUseProfile-extension"),
    V1P2("https://purl.imsglobal.org/ctx/caliper/v1p2");

    private final String value;
    private static Map<String, CaliperJsonldContext> lookup;

    /**
     * Create reverse lookup hash map
     */
    static {
        Map<String, CaliperJsonldContext> map = new HashMap<String, CaliperJsonldContext>();
        for (CaliperJsonldContext constants : CaliperJsonldContext.values()) {
            map.put(constants.value(), constants);
        }
        lookup = ImmutableMap.copyOf(map);
    }

    /**
     * Private constructor
     *
     * @param value
     */
    private CaliperJsonldContext(final String value) {
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