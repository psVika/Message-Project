

package by.messagetransport.model;

import java.util.Objects;

public final class Message {
    private final String data;

    public Message(final String data) {
        this.data = data;
    }
    public String getData(){
        return this.data;
    }
    @Override
    public boolean equals (final Object otherObject) {
        if(this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (this.getClass() != otherObject.getClass()) {
            return false;
        }
        final Message other = (Message) otherObject;
        return Objects.equals(this.data, other.data);
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.data);
    }
    @Override
    public String toString(){
        return this.getClass().getName() + "[data = "+ this.data + "]";
    }
}
