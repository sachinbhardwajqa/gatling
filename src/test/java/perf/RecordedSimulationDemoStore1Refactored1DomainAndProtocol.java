package perf;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class RecordedSimulationDemoStore1Refactored1DomainAndProtocol extends Simulation {
  private static final String DOMAIN = "demostore.gatling.io";
  private static final HttpProtocolBuilder HTTP_PROTOCOL = http.baseUrl("https://" + DOMAIN);
  private static final ScenarioBuilder scn = scenario("RecordedSimulationDemoStore1")
    .exec(
      http("RecordedSimulationDemoStore1_0:GET_https://demostore.gatling.io/")
        .get("/")
              .check(css("#_csrf", "content").saveAs("csrfValue"))
              .check(regex("<title>Gatling Demo-Store</title>").exists()),
      pause(2),
      http("RecordedSimulationDemoStore1_1:GET_https://demostore.gatling.io/about-us")
        .get("/about-us"),
      pause(2),
      http("RecordedSimulationDemoStore1_2:GET_https://demostore.gatling.io/category/all")
        .get("/category/all"),
      pause(1),
      http("RecordedSimulationDemoStore1_3:GET_https://demostore.gatling.io/product/black-and-red-glasses")
        .get("/product/black-and-red-glasses"),
      pause(1),
      http("RecordedSimulationDemoStore1_4:GET_https://demostore.gatling.io/cart/add/19")
        .get("/cart/add/19"),
      pause(1),
      http("RecordedSimulationDemoStore1_5:GET_https://demostore.gatling.io/cart/view")
        .get("/cart/view"),
      pause(3),
      http("RecordedSimulationDemoStore1_6:GET_https://demostore.gatling.io/login")
        .post("/login")
              .formParam("_csrf","#{csrfValue}")
              .formParam("username","user1")
              .formParam("password","pass"),
      http("RecordedSimulationDemoStore1_7:GET_https://demostore.gatling.io/cart/checkout")
        .get("/cart/checkout")
        .resources(
          http("RecordedSimulationDemoStore1_8:GET_https://demostore.gatling.io/cart/checkoutConfirmation")
            .get("/cart/checkoutConfirmation")
        )
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(2))).protocols(HTTP_PROTOCOL);
  }
}
