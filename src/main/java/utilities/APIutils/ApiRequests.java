package utilities.APIutils;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Assert;
import utilities.ConfigFileReader;

import java.io.IOException;

public class ApiRequests {
	
	String endPointUrl ;
	ConfigFileReader config ;


	public ApiRequests() throws IOException {
		 config = new ConfigFileReader();
	}


	public Response getRequestforUserDetails(){
		RestAssured.baseURI = config.getURLforUserDetails();
		String path = "/users";
		Response response = RestAssured
				.given()
				.contentType(ContentType.JSON)
				.when()
				.get(path);
		Assert.assertEquals(response.getStatusCode(),200);
		return response;
	}


	public Response getRequestForTodoList(int userId){
		endPointUrl = config.getURLfortoDo();
		Response response =RestAssured
				.given()
				.contentType(ContentType.JSON)
				.queryParam("userId", userId)
				.when()
				.get(endPointUrl)
				.then()
				.contentType(ContentType.JSON)
				.extract()
				.response();
		Assert.assertEquals(response.getStatusCode(),200);
        return response ;
	}

}
