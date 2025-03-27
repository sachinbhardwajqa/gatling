package scripting;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class RecordedSimulationDemoStore1a extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://computer-database.gatling.io")
            .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
            .acceptEncodingHeader("gzip, deflate, br")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .upgradeInsecureRequestsHeader("1")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:126.0) Gecko/20100101 Firefox/126.0");

    ChainBuilder getDataFromComputerDatabase = exec(
            http("RecordedSimulation1_0:GET : Get Computer Data")
                    .get("/computers"))
            .pause(2);
    ChainBuilder getDetailsFromComputerDatabase = exec(
            http("RecordedSimulation1_1:GET : Get Computer Details")
                    .get("/computers/381"))
            .pause(2);

    ChainBuilder browse = repeat(4, "n").on(  // repeat 5 times
            exec(
                    http("RecordedSimulation1_2:GET : Get Computer Data for Page #{n}")
                            .get("/computers?p=#{n}&n=10&s=name&d=asc"))
                    .pause(2));

    ChainBuilder createNewComputerInComputerDatabase = exec(
            http("RecordedSimulation1_3:POST : Create New Computer")
                    .post("/computers")
                    .formParam("name", "My Computer")
                    .formParam("introduced", "2021-06-01")
                    .formParam("discontinued", "2021-06-01")
                    .formParam("company", "1"))
            .pause(2);
    private ScenarioBuilder admins = scenario("RecordedSimulation1 for Admin")
            .exec(getDataFromComputerDatabase, getDetailsFromComputerDatabase, browse, createNewComputerInComputerDatabase);

    private ScenarioBuilder users = scenario("RecordedSimulation1 for Users")
            .exec(getDataFromComputerDatabase, getDetailsFromComputerDatabase, browse);

    {
        setUp(admins.injectOpen(atOnceUsers(5)),
                users.injectOpen(atOnceUsers(5)))
                .protocols(httpProtocol);
    }
}
