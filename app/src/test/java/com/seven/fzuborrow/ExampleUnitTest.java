package com.seven.fzuborrow;

import com.seven.fzuborrow.network.Api;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    String token;

    @Before
    public void getToken() {
        Api.get().login("zqy", "12345")
                .doOnNext(response -> token = response.getToken()).blockingSubscribe();
    }

    @Test
    public void rejectApplyTest() {
       Api.get().delteDisc(token,24L).blockingSubscribe();
    }

}