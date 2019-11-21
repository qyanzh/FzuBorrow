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

//    @Test
    public void registerTest() throws IOException {
        Api.get().login("zqy", "12345")
                .flatMap(loginResponse -> Api.get().findUserByUid(loginResponse.getToken(),17))
                .subscribe();
    }


    @Test
    public void updateTest() {
        Api.get().userInfoUpdate(token,"zqy","123456","510").doOnNext(userUpdateResponse -> System.out.println(userUpdateResponse.getMessage())).subscribe();
    }
}