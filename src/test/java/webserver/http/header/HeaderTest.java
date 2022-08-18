package webserver.http.header;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class HeaderTest {
    @Test
    @DisplayName("HttpHeader 객체를 생성한다.")
    void createHttpHeader() {
        Header header = new Header();
        assertThat(header).isNotNull().isInstanceOf(Header.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("header set 요청 값이 null 또는 비어있을 경우 예외가 발생한다.")
    void throwExceptionSetHttpHeaderNullOrEmpty(String headerString) {
        Header header = new Header();
        assertThatThrownBy(() -> header.addField(headerString)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Cookie 셋팅 요청 값이 null 일 경우 예외가 발생한다.")
    void throwExceptionSetCookieNull(String cookieString) {
        Header header = new Header();
        assertThatThrownBy(() -> header.setCookies(cookieString)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Header 생성 시, Header fields, cookie 값이 null 일 경우 예외가 발생한다.")
    void throwExceptionFieldsOrCookieNull() {
        assertAll(
                () -> assertThatThrownBy(() -> new Header(null, new Cookie())),
                () -> assertThatThrownBy(() -> new Header(Collections.emptyMap(), null))
        );
    }
}