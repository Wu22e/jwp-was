package http.controller;

import http.requests.HttpRequest;
import http.responses.HttpResponse;
import http.session.HttpSession;
import service.UserService;
import service.exceptions.UserNotFoundException;

public class SignInController implements Controller {

    private final UserService userService = new UserService();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        final String userId = request.getAttributeFromForm("userId");
        final String password = request.getAttributeFromForm("password");

        try {
            final boolean isRightPassword = userService.authenticate(userId, password);
            if (isRightPassword) {
                final HttpSession session = request.getHttpSession();
                session.setAttribute("logined", true);
                response.sendRedirect("/index.html");
                return;
            }
            response.sendRedirect("/user/login_failed.html");
        } catch (UserNotFoundException e) {
            response.sendRedirect("/user/login.html");
        }
    }
}