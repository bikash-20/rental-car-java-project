package carrental.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Very small REST controller that proves the application is up.
 * Visiting http://localhost:8080/ will now return a plain text message
 * instead of the Whitelabel error page.
 */
@RestController
public class HomeRestController {

    @GetMapping("/")
    public String home() {
        return "🚗 AutoRent Web is running – use /hello for a test endpoint";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from AutoRent!";
    }
}
