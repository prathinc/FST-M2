package activities;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Activity2 {
	
	private String ROOT_URI = "https://petstore.swagger.io/v2/user";
	
  @Test(priority=1)
  public void postUser() throws IOException {
	  //Read JSON file as string
	  
	 
	 String filePath = "src/test/resources/userPost.json";
	 
	 
	 //convert JSON to String
	 String reqBody = new String(Files.readAllBytes(Paths.get(filePath)));
	 
			 
			 
	 Response response = given().log().body().contentType(ContentType.JSON)// set headers
			 .body(reqBody)// pass request body
			 .when().post("ROOT_URI");// send post request
	 
	 //inputJSON.close();
	 
	 response.then().log().body();
	 //assertion
	 response.then().body("code", equalTo(200));
	 response.then().body("message", equalTo("91235"));
  }
  
  @Test(priority=2)
  public void getUserInfo() {
      // Import JSON file to write to
      File outputJSON = new File("src/test/resources/userGETResponse.json");

      Response response = 
          given().contentType(ContentType.JSON) // Set headers
          .pathParam("username", "prathinc") // Pass request body from file
          .when().get(ROOT_URI + "/{username}"); // Send POST request
      
      // Get response body
      String resBody = response.getBody().asPrettyString();

      try {
          // Create JSON file
          outputJSON.createNewFile();
          // Write response body to external file
          FileWriter writer = new FileWriter(outputJSON.getPath());
          writer.write(resBody);
          writer.close();
      } catch (IOException excp) {
          excp.printStackTrace();
      }
      
      // Assertion
      response.then().body("id", equalTo(91235));
      response.then().body("username", equalTo("prathinc"));
      response.then().body("firstName", equalTo("Justin"));
      response.then().body("lastName", equalTo("Case"));
      response.then().body("email", equalTo("justincase@mail.com"));
      response.then().body("password", equalTo("password123"));
      response.then().body("phone", equalTo("9812763450"));
  }
  
  @Test(priority=3)
  public void deleteUser() throws IOException {
      Response response = 
          given().contentType(ContentType.JSON) // Set headers
          .pathParam("username","prathinc") // Add path parameter
          .when().delete(ROOT_URI+"/{username}"); // Send POST request
      
      response.then().log().body();

      // Assertion
      response.then().body("code", equalTo(200));
      response.then().body("message", equalTo("prathinc"));
  }
  
}
