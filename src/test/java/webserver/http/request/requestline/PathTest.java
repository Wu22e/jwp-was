package webserver.http.request.requestline;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PathTest {
    @Test
    @DisplayName("Path 객체를 생성한다.")
    void createPath() {
        Path path = new Path("/users", null);
        assertThat(path).isNotNull().isInstanceOf(Path.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("path 요청 값이 null 또는 비어있을 경우 예외가 발생한다.")
    void throwExceptionPathNullOrEmpty(String pathString) {
        assertThatThrownBy(() -> Path.parse(pathString)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("HTTP 요청 path 가 '/' 로 시작하지 않을 경우 예외가 발생한다.")
    void throwExceptionPathNotStartSlash() {
        assertThatThrownBy(() -> Path.parse("users")).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("Path 가 html 파일인지 아닌지 구분한다.")
    @CsvSource(value = {
            "/index.html, true",
            "/style.css, false",
            "/users, false"
    })
    void isHtmlFilePath(String pathString, boolean trueOrFalse) {
        Path path = new Path(pathString, new QueryString(Collections.emptyMap()));
        assertThat(path.isHtmlFilePath()).isEqualTo(trueOrFalse);
    }

    @ParameterizedTest
    @DisplayName("Path 가 static 파일인지 아닌지 구분한다.")
    @CsvSource(value = {
            "/index.html, false",
            "/style.css, true",
            "/users, false"
    })
    void isStaticFilePath(String pathString, boolean trueOrFalse) {
        Path path = new Path(pathString, new QueryString(Collections.emptyMap()));
        assertThat(path.isStaticFilePath()).isEqualTo(trueOrFalse);
    }
}