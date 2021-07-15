package projects;

import static io.restassured.RestAssured.given;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GitHub_Project {

	// Declare request and response specification
	RequestSpecification requestSpec;

	String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCSLBayhh4LCykwhEM4V3w0p1p5YRWksuWJSx/BBN6KLvlOHQmGBVIQ2UjyiNbea4k5TcBhJqR3VCGrUW3ZyjZ6GLtkt1ureCHr99USj+VBrucqs5Uv3A0r5g724qfEcE6GfIM1ObTD+Nk28vmDZgNC6p9DMCxMBa0HBYd/BNBennHjRuTcx/Mt2HVR5Txsdfn0kgqXJ6W1cp6Vo3pNrQzBfBr40IgqgEaSfBG+oUKFBF1fD7VCbct2wthXDsmbO+/6CmzDvNq1sfoYcjOFrDA8ZRCX3pN8iUtFTOesygRfJ37SyFdx0oL/RFTYWFf11NxT68eTeyltR3ofzv4B";
	int sshKeyId = 0;

	@BeforeClass
	public void setUp() {
		requestSpec = new RequestSpecBuilder()
				// set content type
				.setContentType(ContentType.JSON)
				// set header
				.addHeader("Authorization", "token")
				// set base url
				.setBaseUri("https://api.github.com")
				// Build request specification
				.build();
	}

	@Test(priority = 1)
	public void addKey() {

		String reqBody = String.format("{\"title\": \"TestAPIKey\", \"key\": \"%s\"}", sshKey);

		Response response = given().spec(requestSpec) // use request spec
				.body(reqBody) // send request payload
				.when().post("/user/keys"); // post request

		sshKeyId = response.then().extract().path("id");

		// assertion
		response.then().log().body();
		response.then().statusCode(201);
	}

	@Test(priority = 2)
	public void getKey() {

		Response response = given().spec(requestSpec) // use request spec
				.get("/user/keys"); // get request

		// print response to console and TestNG report
		response.then().log().body();
		Reporter.log(response.body().asPrettyString());

		// assertion
		response.then().statusCode(200);

	}

	@Test(priority = 3)
	public void deleteKey() {

		Response response = given().spec(requestSpec) // use request spec
				.pathParam("keyId", sshKeyId) // add path parameter
				.when().delete("/user/keys/{keyId}"); // send path parameter

		// print response to console and TestNG report
		response.then().log().body();
		Reporter.log(response.asPrettyString());

		// assertion
		response.then().statusCode(204);

	}

}
