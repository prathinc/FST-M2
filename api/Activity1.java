package activities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class Activity1 {
	
private String ROOT_URI = "https://petstore.swagger.io/v2/pet";
  
@Test(priority=1)
public void AddNewPet() {
	  String reqBody = "{\"id\": 79000,\"name\": \"Riley\",\"status\": \"alive\"}";
	  Response response = given().contentType(ContentType.JSON).body(reqBody).when().post(ROOT_URI);
	
	  // Assertion
	  response.then().body("id", equalTo(79000));
	  response.then().body("name", equalTo("Riley"));
	  response.then().body("status", equalTo("alive"));
}

@Test(priority=2)
public void GetPetInfo() {
	  Response response = given().contentType(ContentType.JSON).when().pathParam("PetId",79000).get(ROOT_URI+"/{PetId}");
	
	  // Assertion
	  response.then().body("id", equalTo(79000));
	  response.then().body("name", equalTo("Riley"));
	  response.then().body("status", equalTo("alive"));
}
@Test(priority=3)
public void DeletePetInfo() {
	  Response response = given().contentType(ContentType.JSON).when().pathParam("PetId",79000).delete(ROOT_URI+"/{PetId}");
	
	  // Assertion
	  response.then().body("code", equalTo(200));
	  response.then().body("message", equalTo("79000"));
}
  
  
  

}
