/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Covid19RestfulService;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 *
 * @author sinan
 */
@Entity
@Table(name = "tracks")
@IdClass(value = TrackPK.class)
public class Track implements Serializable {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    @Column(name = "location")
    private String location;
    @Column(name = "username")
    private String username;
    @Column(name = "check_in_timestamp")
    private LocalDateTime checkInTimestamp;
    
    public Track() {

    }

    public Track(String location, String username, LocalDateTime checkInTimestamp) {
        setUsername(username);
        setLocation(location);
        setCheckInTime(checkInTimestamp);
    }

    public Track(String location, String username) {
        this(location, username, null);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setCheckInTime(LocalDateTime checkInDate) {
        if (checkInDate != null) {
            this.checkInTimestamp = checkInDate;
        } else {
            this.checkInTimestamp = LocalDateTime.now();
        }
    }

    public LocalDateTime getCreationDate() {
        return checkInTimestamp;
    }

    public String getDateString(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String creationDateString = dtf.format(date);
        return creationDateString;
    }

    public JsonObject getJSONObject() {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("location", location);
        jsonBuilder.add("username", username);
        jsonBuilder.add("checkInDate", getDateString(checkInTimestamp));
        return jsonBuilder.build();
    }

}
