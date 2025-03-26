package perf;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class RecordedSimulation3Feeders extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://computer-database.gatling.io")
            .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
            .acceptEncodingHeader("gzip, deflate, br")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .upgradeInsecureRequestsHeader("1")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:126.0) Gecko/20100101 Firefox/126.0");

    FeederBuilder.Batchable searchFeeder = csv("data/search.csv").random();
    FeederBuilder.Batchable computerFeeder = csv("data/computer.csv").circular();
    ChainBuilder getDataFromComputerDatabase = exec(
            http("RecordedSimulation1_0:GET : Get Computer Data")
                    .get("/computers"))
            .pause(2);
    ChainBuilder getDetailsFromComputerDatabase = exec(
            http("RecordedSimulation1_1:GET : Get Computer Details")
                    .get("/computers/381"))
            .pause(2);

    ChainBuilder search =
            exec(
                    http("Search Computer")
                            .get("/computers"))
                    .pause(2)
                    .feed(searchFeeder)
                    .exec(
                            http("RecordedSimulation1_2:GET : Search Computer #{searchCriterion}")
                                    .get("/computers?f=#{searchCriterion}")
                                    .check(css("a:contains('#{searchComputerName}')", "href").saveAs("computerURL")))

                    .pause(2)
                    .exec(
                            http("RecordedSimulation1_3:GET : Load Computer Details #{searchComputerName}")
                                    .get("/${searchComputerURL}"))
                    .pause(2);

    ChainBuilder browse = repeat(4, "n").on(  // repeat 5 times
            exec(
                    http("RecordedSimulation1_4:GET : Get Computer Data for Page #{n}")
                            .get("/computers?p=#{n}&n=10&s=name&d=asc"))
                    .pause(2));

    ChainBuilder createNewComputerInComputerDatabase =
                    exec(
                            http("RecordedSimulation1_5:GET : Load Create New Computer Page")
                            .get("/computers/new"))
                            .pause(2)
                            .feed(computerFeeder)
                    .exec(
                            http("RecordedSimulation1_6:POST : Create New Computer #{computerName}")
                            .post("/computers")
                            .formParam("name", "#{computerName}")
                            .formParam("introduced", "#{introduced}")
                            .formParam("discontinued", "#{discontinued}")
                            .formParam("company", "#{companyId}")
                                    .check(status().is(200)))
            .pause(2);
//    curl 'https://computer-database.gatling.io/computers' \
//  -H 'accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7' \
//  -H 'accept-language: en-US,en;q=0.9,hi;q=0.8' \
//  -H 'cache-control: no-cache' \
//  -H 'content-type: application/x-www-form-urlencoded' \
//  -b '_hjSessionUser_2865857=eyJpZCI6ImZiZDkzNTEzLTZhMTMtNWI0ZC04NmEzLWNmNDUxODNjOWYyNiIsImNyZWF0ZWQiOjE3MTQxODMzNTI1MzUsImV4aXN0aW5nIjp0cnVlfQ==; _hjSessionUser_3881095=eyJpZCI6IjQxZjUyNzRiLWRhYzMtNTFhYi1hYTJiLTEyZDI1MWE1NjY5YSIsImNyZWF0ZWQiOjE3MTYwODI0NTc3MzAsImV4aXN0aW5nIjp0cnVlfQ==; _uetvid=3584f470043a11efad95bbb28d1e71db; _ga=GA1.1.251592316.1714183352' \
//  -H 'origin: https://computer-database.gatling.io' \
//  -H 'pragma: no-cache' \
//  -H 'priority: u=0, i' \
//  -H 'referer: https://computer-database.gatling.io/computers/new' \
//  -H 'sec-ch-ua: "Chromium";v="134", "Not:A-Brand";v="24", "Google Chrome";v="134"' \
//  -H 'sec-ch-ua-mobile: ?0' \
//  -H 'sec-ch-ua-platform: "macOS"' \
//  -H 'sec-fetch-dest: document' \
//  -H 'sec-fetch-mode: navigate' \
//  -H 'sec-fetch-site: same-origin' \
//  -H 'sec-fetch-user: ?1' \
//  -H 'upgrade-insecure-requests: 1' \
//  -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36' \
//  --data-raw 'name=Manisha&introduced=2023-12-12&discontinued=2024-12-12&company=1'
    private ScenarioBuilder admins = scenario("RecordedSimulation1 for Admin")
            .exec(
//                    getDataFromComputerDatabase, getDetailsFromComputerDatabase, browse,
                    createNewComputerInComputerDatabase);

    private ScenarioBuilder users = scenario("RecordedSimulation1 for Users")
            .exec(getDataFromComputerDatabase, getDetailsFromComputerDatabase, browse);

    {
        setUp(admins.injectOpen(atOnceUsers(3))
//                ,users.injectOpen(atOnceUsers(3)))
                .protocols(httpProtocol));
    }
}
