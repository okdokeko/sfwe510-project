package samazon;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.time.Duration;

// Based on https://www.baeldung.com/gatling-load-testing-rest-endpoint
// Setup all the system specific shenanigans
public class StressProductReadSimulation extends Simulation {
    HttpProtocolBuilder protocol = http
            .baseUrl("http://localhost:8072")
            .acceptHeader("application/json");

    ScenarioBuilder scn = scenario("Browse products")
            .exec(http("login")
                    .post("http://localhost:8080/auth/realms/samazon-realm/protocol/openid-connect/token")
                    .formParam("grant_type", "password")
                    .formParam("client_id", "samazon-client")
                    .formParam("username", "ds_user")
                    .formParam("password", "user123")
                    .check(status().is(200), jsonPath("$.access_token").saveAs("token")))
            .repeat(100).on(
                    exec(http("get products")
                            .get("/samazon/products")
                            .header("Authorization", "Bearer #{token}")
                            .check(status().is(200)))
                    .pause(Duration.ofMillis(200)));

    {
        setUp(scn.injectOpen(rampUsers(100).during(20))).protocols(protocol);
    }
}