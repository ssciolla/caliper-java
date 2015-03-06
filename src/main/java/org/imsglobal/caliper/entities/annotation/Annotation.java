package org.imsglobal.caliper.entities.annotation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableMap;
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.entities.schemadotorg.CreativeWork;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * The super-class of all Annotation types.
 * 
 * Direct sub-types can include - Highlight, Attachment, etc. - all of
 * which are specified in the Caliper Annotation Metric Profile
 */
@JsonPropertyOrder({ "@id", "@type", "name", "description", "properties", "dateCreated", "dateModified" })
public abstract class Annotation extends Entity {

    public enum Type {
        ANNOTATION("http://purl.imsglobal.org/caliper/v1/Annotation"),
        BOOKMARK_ANNOTATION("http://purl.imsglobal.org/caliper/v1/BookmarkAnnotation"),
        HIGHLIGHT_ANNOTATION("http://purl.imsglobal.org/caliper/v1/HighlightAnnotation"),
        SHARED_ANNOTATION("http://purl.imsglobal.org/caliper/v1/SharedAnnotation"),
        TAG_ANNOTATION("http://purl.imsglobal.org/caliper/v1/TagAnnotation");

        private final String uri;
        private static Map<String, Type> lookup;

        /**
         * Create reverse lookup hash map
         */
        static {
            Map<String, Type> map = new HashMap<String, Type>();
            for (Type constants : Type.values()) {
                map.put(constants.uri(), constants);
            }
            lookup = ImmutableMap.copyOf(map);
        }

        /**
         * Private constructor
         * @param uri
         */
        private Type(final String uri) {
            this.uri = uri;
        }

        /**
         * @return URI string
         */
        public String uri() {
            return uri;
        }

        /**
         * Retrieve enum type from reverse lookup map.
         * @param uri
         * @return Event.Type enum
         */
        public static Annotation.Type lookupConstantWithTypeURI(String uri) {
            return lookup.get(uri);
        }
    }

    @JsonProperty("@type")
    private final String type;

    //@JsonProperty("target")
    @JsonIgnore
    private CreativeWork target;

    /**
     * @param builder apply builder object properties to the Annotation object.
     */
    protected Annotation(Builder<?> builder) {
        super(builder);
        this.type = builder.type;
        this.target = builder.target;
    }

    /**
     * @return the type
     */
    @Override
    @Nonnull
    public String getType() {
        return type;
    }

    /**
     * @return the target
     */
    @Nonnull
    public CreativeWork getTarget() {
        return target;
    }


    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Entity.Builder<T>  {
        private String type;
        private CreativeWork target;

        /**
         * Initialize type with default value.  Required if builder().type() is not set by user.
         */
        public Builder() {
            type(Annotation.Type.ANNOTATION.uri());
        }

        /**
         * @param type
         * @return builder.
         */
        private T type(String type) {
            this.type = type;
            return self();
        }

        /**
         * @param target
         * @return annotation target.
         */
        public T target(CreativeWork target) {
            this.target = target;
            return self();
        }
    }

    /**
     *
     */
    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }
}