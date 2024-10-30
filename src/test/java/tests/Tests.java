package tests;

import files.Payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import reusableMethods.Reusable;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Tests {

    String placeId;
    String address = "Vivek Khand";

    @BeforeMethod
    public void setBaseUrl(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
    }

    @Test(priority=1)
    public void postPlaceAPITest(){

        String response= given().queryParam("key","qaclick123").header("Content-Type","application/json").
                body(Payloads.postAPIPayload())
                .when().post("maps/api/place/add/json").
                then().assertThat().statusCode(200).body("scope",equalTo("APP")).extract().response().asString();

        JsonPath js = Reusable.rawToJson(response);
        placeId = js.get("place_id");

    }

    @Test(priority=2)
    public void updatePlaceAPI(){

        String response= given().queryParam("key","qaclick123").header("Content_Type","application/json")
                .body(Payloads.putAPIPayload(placeId)).
                when().put("maps/api/place/update/json").
                then().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = Reusable.rawToJson(response);
        Assert.assertEquals(js.get("msg"),"Address successfully updated");

    }

    @Test(priority=3)
    public void getPlaceAPI(){

        String response = given().queryParam("key","qaclick123").queryParam("place_id",placeId).
                when().get("maps/api/place/get/json").
                then().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = Reusable.rawToJson(response);
        Assert.assertEquals(js.getString("address"),address);

    }

}
