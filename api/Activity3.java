package activities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

public class Activity3 {

	// Declare request and response specification
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;

	@BeforeClass
	public void setUp() {
		requestSpec = new RequestSpecBuilder()
				//set content type
				.setContentType(ContentType.JSON)
				//set base url
				.setBaseUri("https://petstore.swagger.io/v2/pet")
				//Build request specification
				.build();
		
		responseSpec = new ResponseSpecBuilder()
				//check status code 200
				.expectStatusCode(200)
				//check response content type
				.expectContentType("application/json")
				//check if response contains name property
				.expectBody("status",equalTo("alive"))
				//build request specification
				.build();
		
	}
	
	@DataProvider
	public Object petInfoProvider() {
		//setting data to be passed to test case
		Object testData = new Object[][] {
			{ 77151, "Riley", "alive" }, 
            { 77152, "Hansel", "alive" }
		};
		return testData;
	}

	@Test(priority=1)
	public void addPets() {
		String reqBody = "{\"id\": 77151, \"name\": \"Riley\", \"status\": \"alive\"}";
		Response response = given().spec(requestSpec) //use request spec
				.body(reqBody) //send request body
				.when().post(); //send post req
		
		reqBody = "{\"id\": 77152, \"name\": \"Hansel\", \"status\": \"alive\"}";
		response = given().spec(requestSpec) //use request spec
				.body(reqBody) //send request body
				.when().post(); //send post req
		
		//assertions
		response.then().spec(responseSpec); //use response spec
		
	}
	
	@Test(dataProvider = "petInfoProvider", priority=2)
	public void getPets(int id, String name, String status) {
		
		Response response = given().spec(requestSpec) //use request spec
				.pathParam("petId",id) //add path parameter
				.when().get("/{petId}"); // send path parameter
		
		//print response
		System.out.println(response.asPrettyString());
		
		//assertion
		response.then().spec(responseSpec) //use response spec
		.body("name", equalTo(name)); 
	}
	
	@Test(dataProvider = "petInfoProvider", priority=3)
	public void deletePets(int id, String name, String status) {
		
		Response response = given().spec(requestSpec) //use request spec
				.pathParam("petId",id) //add path parameter
				.when().delete("/{petId}"); // send path parameter
		
		
		//assertion
		response.then().body("code", equalTo(200)); 
	}
	
	

}
