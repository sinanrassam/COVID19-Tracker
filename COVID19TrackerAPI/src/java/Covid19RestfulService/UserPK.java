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
    public String username;

    public UserPK(String email, String username) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj == null || !(obj instanceof UserPK)) {
            return isEqual;
        } else {
            UserPK other = (UserPK) obj;

            if (email != null && other.email != null) {
                isEqual = email.equals(other.email);
            }
            if (!isEqual && (username != null && other.username != null)) {
                isEqual = username.equals(other.username);
            }

            return isEqual;
        }

    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if (email != null) {
            hashCode ^= email.hashCode();
        }
        if (username != null) {
            hashCode ^= username.hashCode();
        }
        return hashCode;
    }

}
