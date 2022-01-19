package com.jasb.toiletproject.service.toiletuser;

import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;

public interface ToiletUserService {
    ToiletUser fetchToiletUser() throws ToiletUserNotFoundException;
}
