package Response;

/**
 * Event/EventID class API call
 */
public class eventIDResponse {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
    private boolean success;
    private String message;

    /**
     * Sets up the event constructor for the body
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

    public eventIDResponse(String eventID, String username, String personID, float latitude, float longitude,
                 String country, String city, String eventType, int year, boolean success) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }

    public eventIDResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }


    public String getEventID() {
        return eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage(){
        return message;
    }
}
