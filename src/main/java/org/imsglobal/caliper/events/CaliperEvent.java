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

package org.imsglobal.caliper.events;

import org.imsglobal.caliper.CaliperSendable;
import org.imsglobal.caliper.actions.CaliperAction;
import org.imsglobal.caliper.context.JsonldContext;
import org.imsglobal.caliper.entities.CaliperEntity;
import org.imsglobal.caliper.entities.CaliperGeneratable;
import org.imsglobal.caliper.entities.CaliperReferable;
import org.imsglobal.caliper.entities.CaliperTargetable;
import org.imsglobal.caliper.entities.agent.CaliperAgent;
import org.imsglobal.caliper.entities.agent.CaliperOrganization;
import org.imsglobal.caliper.entities.agent.Membership;
import org.imsglobal.caliper.entities.agent.SoftwareApplication;
import org.imsglobal.caliper.entities.session.LtiSession;
import org.imsglobal.caliper.entities.session.Session;
import org.joda.time.DateTime;
import java.util.Map;

/**
 * Caliper Event interface.
 */
public interface CaliperEvent extends CaliperSendable {

    /**
     * The JSON-LD context provides a mapping of terms to IRIs.  The identifier
     * should be expressed as a unique IRI in conformance with the JSON-LD specification.
     * @return the context IRI.
     */
    JsonldContext getContext();

    /**
     * Identifier that MUST be set either by the emitting service or the receiving endpoint.
     * @return the identifier.
     */
    String getId();

    /**
     * Specifies the type of event or node in the graph as defined by JSON-LD.  The type should be
     * expressed as a unique IRI in conformance with the JSON-LD specification.
     * @return the event type IRI
     */
    CaliperEventType getType();

    /**
     * The actor engaged in the interaction.  Analogous to a subject.  Required.
     * @return the actor
     */
    CaliperAgent getActor();

    /**
     * The action undertaken by the actor.  Analogous to a predicate or verb.  The action should be
     * expressed as a unique IRI in conformance with the JSON-LD specification.  Required.
     * @return the action undertaken by the actor
     */
    CaliperAction getAction();

    /**
     * The object of the interaction.  The object should be expressed as a unique IRI in conformance
     * with the JSON-LD specification.  Required.
     * @return the object of the interaction
     */
    CaliperEntity getObject();

    /**
     * A combined date and time representation (including milliseconds) of when an event occurred,
     * formatted in accordance with the ISO 8601 standard.  Required.
     * @return the event time
     */
    DateTime getEventTime();

    /**
     * An Entity that represents the target of a learning interaction.  Navigating to a resource while engaged in a
     * learning activity is an example of a target entity.  Required.
     * @return target entity
     */
    CaliperTargetable getTarget();

    /**
     * An Entity generated as a result of the learning interaction.  Example entities typed as Generatable include
     * assignment attempts, assessment item responses and graded outcome results.  Optional.
     * @return generated entity
     */
    CaliperGeneratable getGenerated();

    /**
     * Represents the entity from where the navigation commenced.  Optional.
     * @return the referring context
     */
    CaliperReferable getReferrer();

    /**
     * The module, application, platform, system and/or service that provides the technological context within which
     * the learning activity occurs.  Optional.
     * @return the edApp context
     */
    SoftwareApplication getEdApp();

    /**
     * The group represents a collection of people organized together into a community or other social, commercial
     * or political structure.  The group has some common purpose or reason for existence which goes beyond the set
     * of people belonging to it and can act as an Agent.  For learning interactions, a typical group context would
     * comprise the course context within which the learning activity occurs.  Optional.
     * @return the group context
     */
    CaliperOrganization getGroup();

    /**
     * The Membership context defines an actor's roles and status as a member of an organization or group.
     * @return the membership context.  Optional.
     */
    Membership getMembership();

    /**
     * Represents the current user session.  Optional.
     * @return the current session context
     */
    Session getSession();

    /**
     * An LTI-scoped identifier representing the originating Tool Consumer user session during which one or more launch
     * requests to external Tool Providers are initiated.  Provision of a federated session identifier helps to
     * correlate Tool Provider learning activities with those of the Tool Consumer during the same "logical" session.
     * Optional.
     * @return the originating tool consumer context
     */
    LtiSession getFederatedSession();

    /**
     * Additional custom properties provided that are germane to the Event.  Optional.
     * @return extensions
     */
    //Object getExtensions();
    Map<String, Object> getExtensions();
}