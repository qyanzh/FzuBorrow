package com.seven.fzuborrow;

import com.seven.fzuborrow.network.Api;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void registerTest() throws IOException {

//        Api.get().login("zqy", "12345")
//                .doOnNext(loginResponse -> {
//                    System.out.println(loginResponse.getMessage());
//                }).doOnNext(loginResponse -> {
//                    System.out.println("12231");
//                }
//        ).subscribe();

        Api.get().register("asd", "123", "031702420", "218456..", "510")
                .subscribe();
    }
}