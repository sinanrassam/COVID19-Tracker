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
public class TagPK implements Serializable {

    public String id;

    public TagPK(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null || !(obj instanceof TagPK))) {
            return false;
        } else {
            TagPK other = (TagPK) obj;
            if (id != null && other.id != null) {
                return id.equals(other.id);
            }
            return false;
        }

    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if (id != null) {
            hashCode ^= id.hashCode();
        }
        return hashCode;
    }

}
