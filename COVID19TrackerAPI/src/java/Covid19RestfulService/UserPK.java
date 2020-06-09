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
public class UserPK implements Serializable {

    public String email;

    public UserPK(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof UserPK)) {
            return false;
        } else {
            UserPK other = (UserPK) obj;

            return email.equals(other.email);
        }

    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if (email != null) {
            hashCode ^= email.hashCode();
        }
        return hashCode;
    }

}
