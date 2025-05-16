package com.bsuir.DB;

import com.bsuir.hotelorg.Authorization;
import com.bsuir.hotelorg.Role;

public interface ISQLAuthorization {
    Role getRole(Authorization obj);
}
