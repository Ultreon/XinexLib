package dev.ultreon.mods.xinexlib.event.system;

import dev.ultreon.mods.xinexlib.Constants;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/// An event system for handling events.
///
/// @author XyperCode
/// @since 0.1.0 (December 11, 2024)
public class EventSystem {
    public static final EventSystem MAIN = new EventSystem();
    private final Map<Class<?>, Many<Object>> eventStreams = new HashMap<>();
    private final boolean autoRegister;

    public EventSystem() {
        this(true);
    }

    public EventSystem(boolean autoRegister) {
        this.autoRegister = autoRegister;
    }

    /// Registers an event type to the system.
    ///
    /// @param eventType the class of the event to register
    /// @return the sink for the event
    public <T> Many<Object> register(Class<T> eventType) {
        Many<Object> value = Sinks.many().multicast().onBackpressureBuffer();
        eventStreams.putIfAbsent(eventType, value);
        return value;
    }

    /// Publishes an event to the system.
    ///
    /// @param event the event object to publish
    /// @return the same event that was published
    public <T> T publish(T event) {
        Class<?> aClass = event.getClass();
        publishInternal(event, aClass);

        return event;
    }

    private <T> void publishInternal(T event, final Class<?> aClass) {
        for (Class<?> c : aClass.getInterfaces()) publishInternal(event, c);
        if (aClass != Object.class) {
            Class<?> superclass = aClass.getSuperclass();
            if (superclass != Object.class && superclass != null) publishInternal(event, superclass);

            Many<Object> sink = eventStreams.get(aClass);
            if (sink != null) try {
                sink.tryEmitNext(event);
            } catch (Exception e) {
                Constants.LOG.error("Failed to publish event", e);
            }
        }
    }

    /// Subscribes to an event type.
    ///
    /// @param eventType the class of the event to subscribe to
    /// @param handler   the handler to execute when the event is published
    public <T> void on(Class<T> eventType, Consumer<T> handler) {
        Many<Object> sink = eventStreams.get(eventType);
        if (sink == null && autoRegister) sink = register(eventType);
        if (sink != null) {
            sink.asFlux()
                    .cast(eventType) // Cast the event to the correct type
                    .subscribe(handler);
        }
    }

    /// Subscribes to an event type.
    ///
    /// @param eventType the class of the event to subscribe to
    /// @param handler   the handler to execute when the event is published
    public <T> void on(Class<T> eventType, Subscriber<T> handler) {
        Many<Object> sink = eventStreams.get(eventType);
        if (sink == null && autoRegister) sink = register(eventType);
        if (sink != null) {
            sink.asFlux()
                    .cast(eventType) // Cast the event to the correct type
                    .subscribe(handler);
        }
    }
}