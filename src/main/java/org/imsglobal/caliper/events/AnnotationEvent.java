package org.imsglobal.caliper.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.profiles.Profile.Action;
import org.imsglobal.caliper.validators.ValidatorResult;
import org.imsglobal.caliper.validators.events.AnnotationEventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;


@SupportedActions({
        Action.ATTACHED,
        Action.BOOKMARKED,
        Action.CLASSIFIED,
        Action.COMMENTED,
        Action.DESCRIBED,
        Action.DISLIKED,
        Action.HIGHLIGHTED,
        Action.IDENTIFIED,
        Action.LIKED,
        Action.LINKED,
        Action.RANKED,
        Action.QUESTIONED,
        Action.RECOMMENDED,
        Action.REPLIED,
        Action.SHARED,
        Action.SUBSCRIBED,
        Action.TAGGED
})
public class AnnotationEvent extends Event {

    @JsonProperty("@context")
    private final String context;

    @JsonProperty("@type")
    private final String type;

    @JsonProperty("action")
    private final Action action;

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(AnnotationEvent.class);

    /**
     * Utilize builder to construct AnnotationEvent.  Validate Annotation object copy rather than the
     * Annotation builder.  This approach protects the class against parameter changes from another
     * thread during the "window of vulnerability" between the time the parameters are checked
     * until when they are copied.  Validate properties of builder copy and if conformance violations
     * are found throw an IllegalStateException (Bloch, Effective Java, 2nd ed., items 2, 39, 60, 63).
     *
     * @param builder
     */
    protected AnnotationEvent(Builder<?> builder) {
        super(builder);
        this.context = builder.context;
        this.type = builder.type;
        this.action = builder.action;

        ValidatorResult result = new AnnotationEventValidator().validate(this);
        if (!result.isValid()) {
            throw new IllegalStateException(result.errorMessage().toString());
        }
    }

    /**
     * Required.
     * @return the context
     */
    @Override
    @Nonnull
    public String getContext() {
        return context;
    }

    /**
     * Required.
     * @return the type
     */
    @Override
    @Nonnull
    public String getType() {
        return type;
    }

    /**
     * Required.
     * @return the action
     */
    @Override
    @Nonnull
    public Action getAction() {
        return action;
    }

    /**
     * Initialize default parameter values in the builder.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Event.Builder<T>  {
        private String context;
        private String type;
        private Action action;

        /*
         * Constructor
         */
        public Builder() {
            context(Context.ANNOTATION.uri());
            type(Type.ANNOTATION.uri());
        }

        /**
         * @param context
         * @return builder.
         */
        private T context(String context) {
            this.context = context;
            return self();
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
         * @param action
         * @return builder.
         */
        @Override
        public T action(Action action) {
            this.action = action;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable profile object.
         * @return a new AnnotationEvent instance.
         */
        public AnnotationEvent build() {
            return new AnnotationEvent(this);
        }
    }

    /**
     * Self-reference that permits sub-classing of builder.
     */
    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    /**
     * Static factory method.
     * @return a new instance of the builder.
     */
    public static Builder<?> builder() {
        return new Builder2();
    }
}