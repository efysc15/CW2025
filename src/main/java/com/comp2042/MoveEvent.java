package com.comp2042;

/**
 * Represents a movement-related input event in the game
 * <p>
 * A {@code MoveEvent} encapsulates both the type of action performed (e.g., down, left, right, rotate) and
 * the source of the event (e.g., user input or automated system).
 * 
 * <p>
 * This class is immutable and serves as a simple data carrier between the GUI layer {@link GuiController} and the game logic ({@link InputEventListener} / {@link GameController}).
 * 
 */
public final class MoveEvent {
    /** The type of movement event (e.g., DOWN, LEFT, RIGHT, ROTATE) */
    private final EventType eventType;
    /** The source of the event (e.g., USER, SYSTEM) */
    private final EventSource eventSource;

    /**
     * Constructs a new {@code MoveEvent}
     * @param eventType the {@link EventType} representing the kind of movement
     * @param eventSource the {@link EventSource} indicating where the event originated
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Returns the type of this movement event
     * @return the {@link EventType} of the event
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Returns the source of this movement event
     * @return the {@link EventSource} of the event
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
