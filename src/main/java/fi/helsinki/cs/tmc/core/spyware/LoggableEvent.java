package fi.helsinki.cs.tmc.core.spyware;

import fi.helsinki.cs.tmc.core.communication.serialization.JsonMaker;
import fi.helsinki.cs.tmc.core.domain.Course;
import fi.helsinki.cs.tmc.core.domain.Exercise;

import java.util.List;
import java.util.logging.Logger;

public class LoggableEvent implements TmcEvent {

    private static final Logger log = Logger.getLogger(LoggableEvent.class.getName());

    private String courseName;
    private String exerciseName;
    private String eventType;

    private byte[] data;

    private JsonMaker metadata;

    private long happenedAt; // millis from epoch
    private long systemNanotime;
    private transient String key;

    public LoggableEvent(String eventType, byte[] data) {
        this("", "", eventType, data, null);
    }

    public LoggableEvent(Exercise exercise, String eventType, byte[] data) {
        this(exercise, eventType, data, null);
    }

    public LoggableEvent(Course course, String eventType, byte[] data) {
        this(course.getName(), "", eventType, data, null);
    }

    public LoggableEvent(Exercise exercise, String eventType, byte[] data, JsonMaker metadata) {
        this(exercise.getCourseName(), exercise.getName(), eventType, data, metadata);
    }

    public LoggableEvent(String courseName, String exerciseName, String eventType, byte[] data) {
        this(courseName, exerciseName, eventType, data, null);
    }

    public LoggableEvent(
            String courseName,
            String exerciseName,
            String eventType,
            byte[] data,
            JsonMaker metadata) {
        this.courseName = courseName;
        this.exerciseName = exerciseName;
        this.eventType = eventType;
        this.data = data;
        this.metadata = JsonMaker.create().merge(metadata);
        this.happenedAt = System.currentTimeMillis();
        this.systemNanotime = System.nanoTime();

        this.key = courseName + "|" + exerciseName + "|" + eventType;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getEventType() {
        return eventType;
    }

    public byte[] getData() {
        return data;
    }

    /**
     * Optional JSON metadata.
     */
    public String getMetadata() {
        return metadata.toString();
    }

    public LoggableEvent addMetadata(String name, String value) {
        metadata.add(name, value);
        return this;
    }

    public LoggableEvent addMetadata(String name, long value) {
        metadata.add(name, value);
        return this;
    }

    public LoggableEvent addMetadata(String name, boolean value) {
        metadata.add(name, value);
        return this;
    }

    public LoggableEvent addMetadata(String name, List<String> values) {
        metadata.add(name, values);
        return this;
    }

    public LoggableEvent addMetadata(JsonMaker metadata) {
        this.metadata.merge(metadata);
        return this;
    }

    /**
     * {@code key = course name + "|" + exercise name + "|" + event type}.
     */
    public String getKey() {
        return key;
    }

    public long getHappenedAt() {
        return happenedAt;
    }

    public void setHappenedAt(long happenedAt) {
        this.happenedAt = happenedAt;
    }

    public long getSystemNanotime() {
        return systemNanotime;
    }

    @Override
    public String toString() {
        return "LoggableEvent{"
                + "courseName="
                + courseName
                + ", exerciseName="
                + exerciseName
                + ", eventType="
                + eventType
                + ", happenedAt="
                + happenedAt
                + ", systemNanotime="
                + systemNanotime
                + ", key="
                + key
                + ", metadata="
                + metadata
                + ", data="
                + new String(data)
                + "}";
    }
}
