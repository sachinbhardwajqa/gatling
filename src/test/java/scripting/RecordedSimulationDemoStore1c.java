package scripting;

import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class RecordedSimulationDemoStore1c extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://demostore.gatling.io")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");
  
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"),
    Map.entry("accept-encoding", "gzip, deflate, br, zstd"),
    Map.entry("accept-language", "en-US,en;q=0.9,hi;q=0.8"),
    Map.entry("pragma", "no-cache"),
    Map.entry("priority", "u=0, i"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Google Chrome\";v=\"134"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "macOS"),
    Map.entry("sec-fetch-dest", "document"),
    Map.entry("sec-fetch-mode", "navigate"),
    Map.entry("sec-fetch-site", "none"),
    Map.entry("sec-fetch-user", "?1"),
    Map.entry("upgrade-insecure-requests", "1")
  );
  
  private Map<CharSequence, String> headers_1 = Map.ofEntries(
    Map.entry("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"),
    Map.entry("accept-encoding", "gzip, deflate, br, zstd"),
    Map.entry("accept-language", "en-US,en;q=0.9,hi;q=0.8"),
    Map.entry("pragma", "no-cache"),
    Map.entry("priority", "u=0, i"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Google Chrome\";v=\"134"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "macOS"),
    Map.entry("sec-fetch-dest", "document"),
    Map.entry("sec-fetch-mode", "navigate"),
    Map.entry("sec-fetch-site", "same-origin"),
    Map.entry("sec-fetch-user", "?1"),
    Map.entry("upgrade-insecure-requests", "1")
  );
  
  private Map<CharSequence, String> headers_4 = Map.ofEntries(
    Map.entry("accept", "*/*"),
    Map.entry("accept-encoding", "gzip, deflate, br, zstd"),
    Map.entry("accept-language", "en-US,en;q=0.9,hi;q=0.8"),
    Map.entry("pragma", "no-cache"),
    Map.entry("priority", "u=1, i"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Google Chrome\";v=\"134"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "macOS"),
    Map.entry("sec-fetch-dest", "empty"),
    Map.entry("sec-fetch-mode", "cors"),
    Map.entry("sec-fetch-site", "same-origin"),
    Map.entry("x-requested-with", "XMLHttpRequest")
  );
  
  private Map<CharSequence, String> headers_7 = Map.ofEntries(
    Map.entry("Upgrade-Insecure-Requests", "1"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Google Chrome\";v=\"134"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "macOS")
  );


  private ScenarioBuilder scn = scenario("RecordedSimulationDemoStore1")
    .exec(
      http("RecordedSimulationDemoStore1_0:GET_https://demostore.gatling.io/")
        .get("/")
              .check(css("#_csrf", "content").saveAs("csrfValue"))
        .headers(headers_0),
      pause(2),
      http("RecordedSimulationDemoStore1_1:GET_https://demostore.gatling.io/about-us")
        .get("/about-us")
        .headers(headers_1),
      pause(2),
      http("RecordedSimulationDemoStore1_2:GET_https://demostore.gatling.io/category/all")
        .get("/category/all")
        .headers(headers_1),
      pause(1),
      http("RecordedSimulationDemoStore1_3:GET_https://demostore.gatling.io/product/black-and-red-glasses")
        .get("/product/black-and-red-glasses")
        .headers(headers_1),
      pause(1),
      http("RecordedSimulationDemoStore1_4:GET_https://demostore.gatling.io/cart/add/19")
        .get("/cart/add/19")
        .headers(headers_4),
      pause(1),
      http("RecordedSimulationDemoStore1_5:GET_https://demostore.gatling.io/cart/view")
        .get("/cart/view")
        .headers(headers_1),
      pause(4),
      http("RecordedSimulationDemoStore1_6:GET_https://demostore.gatling.io/login")
        .post("/login")
        .headers(headers_1)
              .formParam("_csrf","#{csrfValue}")
              .formParam("username","user1")
              .formParam("password","pass"),
      http("RecordedSimulationDemoStore1_7:GET_https://demostore.gatling.io/cart/checkout")
        .get("/cart/checkout")
        .headers(headers_1)
        .resources(
          http("RecordedSimulationDemoStore1_8:GET_https://demostore.gatling.io/cart/checkoutConfirmation")
            .get("/cart/checkoutConfirmation")
            .headers(headers_7)
        )
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
