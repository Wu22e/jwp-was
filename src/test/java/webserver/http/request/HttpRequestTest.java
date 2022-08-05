package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Header;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.request.requestline.Protocol;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;
import webserver.http.request.requestline.Version;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;


class HttpRequestTest {
    @Test
    @DisplayName("HttpRequest 를 생성한다.")
    void create_HttpRequest() throws Exception {
        // given
        RequestLine requestLine = RequestLine.parse("POST /user/create HTTP/1.1");
        Header header = new Header(Map.of(
                "Host", "localhost:8080",
                "Connection", "keep-alive",
                "Content-Length", "71",
                "Content-Type", "application/x-www-form-urlencoded",
                "Accept", "*/*"
        ));
        QueryString body = QueryString.parse("userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net");
        HttpRequest expectedHttpRequest = new HttpRequest(requestLine, header, body);

        // when
        InputStream in = new FileInputStream("./src/test/resources/request.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        HttpRequest actualHttpRequest = HttpRequest.of(br);

        // then
        assertThat(expectedHttpRequest).isEqualTo(actualHttpRequest);
    }

    @Test
    @DisplayName("HttpRequest 요청라인, 헤더, 바디가 null 일 경우 예외가 발생한다.")
    void throw_exception_request_null() {
        assertAll(
                () -> assertThatThrownBy(() -> new HttpRequest(null, new Header(), new QueryString())).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new HttpRequest(new RequestLine(Method.GET, new Path("HTTP", new QueryString()), new Protocol("HTTP", Version.ONE_ONE)), null, new QueryString())).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new HttpRequest(new RequestLine(Method.GET, new Path("HTTP", new QueryString()), new Protocol("HTTP", Version.ONE_ONE)), new Header(), null)).isInstanceOf(IllegalArgumentException.class)
        );
    }
}