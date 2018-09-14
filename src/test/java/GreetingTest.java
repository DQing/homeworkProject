import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingTest {
    @Test
    void should_return_correct_string() {
        Greeting greeting = new Greeting();
        String greetString = greeting.greeting();
        String excepted = "Hello World!";
        assertEquals(excepted, greetString);
    }
}
