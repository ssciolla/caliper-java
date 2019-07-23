package org.imsglobal.caliper.profiles;

/**
 * The CaliperProfile interface allows an implementor to extend the otherwise static list of Caliper profiles with
 * additional profiles.  The implementer can follow up by writing an enum manager in order to join the core and custom
 * action enums together.
 */
public interface CaliperProfile {

    /**
     * Action string.
     * @return profile value
     */
    String value();

}
