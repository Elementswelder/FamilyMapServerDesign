package Model;

//Event Getters and Setters

import java.util.Objects;

/**
 * Event class that has getters and setters based on it's object
 */
public class Event {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    /**
     * Sets up the event constructor
     * @param eventID The event ID
     * @param username the user associated with the event
     * @param personID the person ID associated with the event
     * @param latitude the lat of the event
     * @param longitude the lon of the event
     * @param country the country of the event
     * @param city the city of the event
     * @param eventType the type of event that occured
     * @param year the year that it happened
     */

    public Event(String eventID, String username, String personID, float latitude, float longitude,
                 String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o){
        Event e = (Event)o;
        if (o == null){
            return false;
        }
        if (getClass() != o.getClass()){
            return false;
        }
        if (o == this){
            return true;
        }
        //Is O null?
        //Is o == this?
        //do this and o have the same class?

        //do this and o have the same wordCount and nodeCount?
        if (!Objects.equals(e.eventID, this.eventID)){
            return false;
        }
        if (!Objects.equals(this.getAssociatedUsername(), e.getAssociatedUsername())){
            return false;
        }
        if (!Objects.equals(e.getPersonID(), this.getPersonID())){
            return false;
        }

        if (e.getLatitude() != this.getLatitude()){
            return false;
        }
        if (!Objects.equals(e.getCountry(), this.getCountry())){
            return false;
        }
        if (!Objects.equals(e.getCity(), this.getCity())){
            return false;
        }
        if (!Objects.equals(e.getEventType(), this.getEventType())){
            return false;
        }
        if (e.getYear() != this.getYear()){
            return false;
        }

        return true;
    }
}
