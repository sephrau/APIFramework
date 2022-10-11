package test;

import apiconfigs.APIPaths;
import apiconfigs.HeaderConfig;
import apiverification.ResponseVerification;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetPeopleAPI extends InitTest {

	Response response;
	ArrayList<Response> responses = new ArrayList<>();

	// Test for successful response validation
	@Test
	public void getPeopleAPITest() {
		test.log(LogStatus.INFO, "Starting getAPITest......");
		response = given().headers(HeaderConfig.defaultHeaders()).when().get(APIPaths.GET_LIST_OF_PEOPLE);

		test.log(LogStatus.INFO, response.getBody().prettyPrint());
		ResponseVerification.responseCodeValidation(response, 200);
		ResponseVerification.responseContentTypeValidation(response, "application/json");
		ResponseVerification.responseTimeValidation(response);
		test.log(LogStatus.INFO, "Ended getAPITest......");
	}

	// Test for validating total no of people
	@Test
	public void getPeopleTotalCountTest() {
		test.log(LogStatus.INFO, "Validating Count of People......");
		ResponseVerification.responseKeyValidationFromJSONObject(response, "count", 82);
		test.log(LogStatus.INFO, "Validated Count of People......");
	}

	// Test for counting people who have height>200 and validating if that count=10
	@Test
	public void verifyNumberOfFemalesInPeople() {
		test.log(LogStatus.INFO, "Validating Count of Females......");

		int countOfFemale = ResponseVerification.responseValidationFromJSONObjectForGender(getAllPeopleAPI(), "results",
				"female");

		// Note : This assertion is expected to fail as there's only one person with
		// height>200
		Assert.assertEquals(countOfFemale, 17);
		test.log(LogStatus.INFO, "Validated Count of People having height greater than 200......");
	}

	// Test for counting people who have gender female and validating if that
	// count=10
	@Test
	public void getPeopleWithHeightGreaterThanTwoHundredTest() {
		test.log(LogStatus.INFO, "Validating Count of People having height greater than 200......");

		int countOfHeightPplGTTwoHund = ResponseVerification
				.responseValidationFromJSONObjectForHeight(getAllPeopleAPI(), "results");

		// Note : This assertion is expected to fail as there's only one person with
		// height>200
		Assert.assertEquals(countOfHeightPplGTTwoHund, 10);
		test.log(LogStatus.INFO, "Validated Count of People having height greater than 200......");
	}

	public ArrayList<Response> getAllPeopleAPI() {

		if (responses.size() == 9)
			return responses;
		test.log(LogStatus.INFO, "Starting getAPITest......");
		response = given().headers(HeaderConfig.defaultHeaders()).when().get(APIPaths.GET_LIST_OF_PEOPLE);

		test.log(LogStatus.INFO, response.getBody().prettyPrint());
		ResponseVerification.responseCodeValidation(response, 200);

		responses.add(response);
		String key = "next";

		// test.log(LogStatus.INFO, "Ended getAPITest......");
		JSONObject json = new JSONObject(response.getBody().asString());

		int i = 2;
		do {
			if (json.isNull(key))
				break;
			Boolean b = json.isNull(key);
			String page = "/people/?page=" + i;
			Response resp = given().headers(HeaderConfig.defaultHeaders()).when().get(page);

			// test.log(LogStatus.INFO,response.getBody().prettyPrint());
			ResponseVerification.responseCodeValidation(resp, 200);

			responses.add(resp);
			json = new JSONObject(resp.getBody().asString());

			i++;
		} while (!json.isNull(key));

		return responses;
	}

}
