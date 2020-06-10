/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Covid19RestfulService;

import java.io.Serializable;

/**
 *
 * @author sinan
 */
public class TrackPK implements Serializable {

    public Integer id;

    public TrackPK(Integer id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof TrackPK)) {
            return false;
        } else {
            TrackPK other = (TrackPK) obj;
            return id.equals(other.id);
        }
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        } else {
            return id.hashCode();
        }
    }
}