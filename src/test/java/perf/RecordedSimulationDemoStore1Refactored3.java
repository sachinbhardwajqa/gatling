package perf;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class RecordedSimulationDemoStore1Refactored3 extends Simulation {
    private static final String DOMAIN = "demostore.gatling.io";
    private static final HttpProtocolBuilder HTTP_PROTOCOL = http.baseUrl("https://" + DOMAIN);
    private static final FeederBuilder<String> categoryFeeder = csv("data/categoryDetails.csv").circular();
    private static class CmsPage {
        private static final ChainBuilder homePage =
                exec(http("Load Home Page")
                        .get("/")
                        .check(css("#_csrf", "content").saveAs("csrfValue"))
                        .check(regex("<title>Gatling Demo-Store</title>").exists()));
        private static final ChainBuilder aboutUs =
                exec(http("Load About Us Page")
                        .get("/about-us")
                        .check(substring("About Us")));
    }

    private static class Catalog {
        private static class Category {
            private static final ChainBuilder view =
                    feed(categoryFeeder)
                            .repeat(2, "n").on(
                            exec(http("View #{n} Category - #{categoryName}")
                                    .get("/category/#{categorySlug}")
                                    .check(css("#CategoryName").isEL("${categoryName}")))); // EL = Expression Language
        }
    }
    private static final ScenarioBuilder scn = scenario("RecordedSimulationDemoStore1")
            .exec(CmsPage.homePage)
            .pause(2)
            .exec(CmsPage.aboutUs)
            .pause(2)
            .exec(Catalog.Category.view)
            .pause(2)
            .exec(
                    http("Select product - black-and-red-glasses")
                            .get("/product/black-and-red-glasses"),
                    pause(1),
                    http("Add to Cart")
                            .get("/cart/add/19"),
                    pause(1),
                    http("View Cart")
                            .get("/cart/view"),
                    pause(3),
                    http("Login")
                            .post("/login")
                            .formParam("_csrf", "#{csrfValue}")
                            .formParam("username", "user1")
                            .formParam("password", "pass"),
                    http("Checkout")
                            .get("/cart/checkout")
                            .resources(
                                    http("Checkout Confirmation")
                                            .get("/cart/checkoutConfirmation")
                            )
            );

    {
        setUp(scn.injectOpen(atOnceUsers(4))).protocols(HTTP_PROTOCOL);
    }
}
