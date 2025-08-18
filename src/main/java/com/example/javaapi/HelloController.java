package com.example.javaapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Java API!";
    }

        // POST endpoint to greet by name
    @PostMapping("/greet")
    public String greet(@RequestBody NameRequest request) {
        return "Hello, " + request.getName() + " from Java API!";
    }
}
// DTO for request
class NameRequest {
    private String name;

    public NameRequest() {}  // default constructor

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
