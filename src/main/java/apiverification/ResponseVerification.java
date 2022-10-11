package apiverification;

import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import utils.ExtentReportListener;

import java.util.ArrayList;
import java.util.List;

public class ResponseVerification extends ExtentReportListener {

	/*
	 * Method to validate response for response code
	 * 
	 * @param response - response that needs to be validated
	 * 
	 * @param statusCode - Expected status code to be validated against Actual
	 */
	public static void responseCodeValidation(Response response, int statusCode) {

		try {
			Assert.assertEquals(statusCode, response.getStatusCode());
			test.log(LogStatus.PASS,
					"Status code successfully validated, status code is :: " + response.getStatusCode());
		} catch (AssertionError e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
			test.log(LogStatus.FAIL,
					"Expected status code is :: " + statusCode + " , instead getting :: " + response.getStatusCode());
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	/*
	 * Method to validate response for Content Type
	 * 
	 * @param response - response that needs to be validated
	 * 
	 * @param expectedContentType - Expected Content Type to be validated against
	 * Actual
	 */
	public static void responseContentTypeValidation(Response response, String expectedContentType) {

		try {
			Assert.assertEquals(expectedContentType, response.getContentType());
			test.log(LogStatus.PASS,
					"ContentType successfully validated, ContentType is :: " + response.getContentType());
		} catch (AssertionError e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
			test.log(LogStatus.FAIL, "Expected ContentType is :: " + expectedContentType + " , instead getting :: "
					+ response.getContentType());
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	/*
	 * Method to validate response for a specific key for its associated value
	 * 
	 * @param response - response that needs to be validated
	 * 
	 * @param key - json key to be validated for a value
	 * 
	 * @param expectedValue - Expected value associated with the key to be validated
	 * against Actual
	 */
	public static void responseKeyValidationFromJSONObject(Response response, String key, int expectedValue) {
		try {
			JSONObject json = new JSONObject(response.getBody().asString());
			if (json.has(key) && json.get(key) != null) {
				Assert.assertEquals(json.get(key), expectedValue);
				System.out.println("Successfully validated value of " + key + " It is " + json.get(key));
				test.log(LogStatus.PASS, "Successfully validated value of " + key + " It is " + json.get(key));
			} else {
				test.log(LogStatus.FAIL, "Key is not available");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	/*
	 * Method to filter response for the associated height > 200 for every person in
	 * the json object and further validating if they are part of certain list of
	 * people
	 * 
	 * @param response - response that needs to be validated
	 * 
	 * @param key - json key to be validated for a value
	 */
	public static int responseValidationFromJSONObjectForHeight(ArrayList<Response> responses, String key) {
		int count = 0;
		List<String> listOfPplNamesWithHeightGTTwoHundred = listOfValidNames();
		for (Response response : responses) {
			try {
				JSONObject json = new JSONObject(response.getBody().asString());
				if (json.has(key) && json.get(key) != null) {
					JSONArray jsonArray = (JSONArray) json.get(key);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = (JSONObject) jsonArray.get(i);
						int height = 0;
						String name = "";
						String url = "";
						try {
							url = (String) jsonObject.get("url");
							name = jsonObject.getString("name");
							height = Integer.valueOf(String.valueOf(jsonObject.get("height")));
						} catch (NumberFormatException e) {
							// System.out.println(name +" " +height+url);
						}

						if (height > 200) {
							count++;
							name = (String) jsonObject.get("name");

							// Note: This assertion is expected to fail as the expected Name is not present
							// in the list
							Assert.assertTrue(listOfPplNamesWithHeightGTTwoHundred.contains(name), name);
						}
					}
				} else {
					test.log(LogStatus.INFO, "Key is not available");
				}
			} catch (Exception e) {
				test.log(LogStatus.FAIL, e.fillInStackTrace());
			}
		}
		return count;
	}

	/*
	 * Method to return list of people
	 * 
	 * @return List<String> - list of names of people
	 */
	private static List<String> listOfValidNames() {
		List<String> listOfPplNamesWithHeightGTTwoHundred = new ArrayList<>();
		listOfPplNamesWithHeightGTTwoHundred.add("Darth Vader");
		listOfPplNamesWithHeightGTTwoHundred.add("Chewbacca");
		listOfPplNamesWithHeightGTTwoHundred.add("Roos Tarpals");
		listOfPplNamesWithHeightGTTwoHundred.add("Rugor Nass");
		listOfPplNamesWithHeightGTTwoHundred.add("Yarael Poof");
		listOfPplNamesWithHeightGTTwoHundred.add("Lama Su");
		listOfPplNamesWithHeightGTTwoHundred.add("Tuan Wu");
		listOfPplNamesWithHeightGTTwoHundred.add("Grievous");
		listOfPplNamesWithHeightGTTwoHundred.add("Tarfful");
		listOfPplNamesWithHeightGTTwoHundred.add("Tion Medon");

		return listOfPplNamesWithHeightGTTwoHundred;
	}

	/*
	 * Method to log the response time for the response
	 * 
	 * @param response
	 */
	public static void responseTimeValidation(Response response) {
		try {
			long time = response.time();
			test.log(LogStatus.INFO, "API Response time is :: " + time);
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	public static int responseValidationFromJSONObjectForGender(ArrayList<Response> responses, String key,
			String gender) {
		int count = 0;
		List<String> listOfPplNamesWithHeightGTTwoHundred = listOfValidNames();
		for (Response response : responses) {
			try {
				JSONObject json = new JSONObject(response.getBody().asString());
				if (json.has(key) && json.get(key) != null) {
					JSONArray jsonArray = (JSONArray) json.get(key);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = (JSONObject) jsonArray.get(i);

						String _gender = (String) jsonObject.get("gender");

						if (_gender.equalsIgnoreCase(gender)) {
							count++;
						}
					}
				} else {
					test.log(LogStatus.INFO, "Key is not available");
				}
			} catch (Exception e) {
				test.log(LogStatus.FAIL, e.fillInStackTrace());
			}
		}
		return count;

	}
}