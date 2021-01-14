package com.example.toucan_remake.util;

import com.example.toucan_remake.user.EntityUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class UtilJWTTest {

    @Test
    public void extractUsername_allValuesOK_resultOK() {
        //given
        UtilJWT jwtUtil = new UtilJWT();
        EntityUser entityUser = new EntityUser("emailTest", "passTest");
        String jwt = jwtUtil.generateToken(entityUser);

        //when
        String result = jwtUtil.extractEmail(jwt);

        //then
        Assertions.assertEquals("emailTest", result);
    }

    @Test
    public void isJWTExpired_allValuesOK_resultTrue() {
        //given
        UtilJWT jwtUtil = new UtilJWT();
        EntityUser entityUser = new EntityUser("emailTest", "passTest");
        String jwt = jwtUtil.generateToken(entityUser);

        //when
        boolean result = jwtUtil.isJWTValid(jwt, entityUser);

        //then
        Assertions.assertTrue(result);
    }
}
