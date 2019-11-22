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
        Api.get().login("czhhh", "123")
                .doOnNext(response -> token = response.getToken()).blockingSubscribe();
    }

//    @Test
    public void registerTest() throws IOException {
        Api.get().login("zqy", "12345")
                .flatMap(loginResponse -> Api.get().findUserByUid(loginResponse.getToken(),17))
                .subscribe();
    }

    @Test
    public void deleteTest() {
        Api.get().deleteGood(token,22L).subscribe();
    }

    @Test
    public void rejectApplyTest() {
        Api.get().findApply(token).blockingSubscribe();
        Api.get().handleApply(token,Constants.APPLY_TYPE_REJECT,26,24).blockingSubscribe();
        Api.get().findApply(token).blockingSubscribe();
    }
}