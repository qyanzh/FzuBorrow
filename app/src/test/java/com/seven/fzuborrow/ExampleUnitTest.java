package com.seven.fzuborrow;

import com.seven.fzuborrow.data.User;
import com.seven.fzuborrow.network.Api;
import com.seven.fzuborrow.network.response.LoginResponse;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
        Api.get().findUser("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NzQyNjU3NjIsImV4cCI6MTU3NDUyNDk2MiwidWlkIjoiMTkiLCJpc3MiOiJsd3MifQ.X3u_q3C5SUcuXoSbGbPQPAjgn6wioL0fmWU-7y-gqII",
                15L).subscribe(v->v.getUser().getUsername(),e->e.printStackTrace());

    }
}