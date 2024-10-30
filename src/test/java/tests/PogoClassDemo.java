package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import pojo.Api;
import pojo.ResponsePojo;
import reusableMethods.Reusable;

import java.util.*;

import static io.restassured.RestAssured.given;

public class PogoClassDemo {

    @Test
    public static void pojoPracticeTest(){

        RestAssured.baseURI="https://rahulshettyacademy.com/";
        String res = given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().post("oauthapi/oauth2/resourceOwner/token")
                .then().log().all().extract().response().asString();

        JsonPath js = Reusable.rawToJson(res);
        String access_token = js.get("access_token");

        ResponsePojo obj = given().queryParam("access_token",access_token)
                .when().get("oauthapi/getCourseDetails")
                .then().log().all().extract().response().as(ResponsePojo.class);

        System.out.println(obj.getCourses().getWebAutomation().get(0).getPrice());
        System.out.println(obj.getLinkedIn());

        List<Api> apiCourses = obj.getCourses().getApi();
        for(int i=0; i< apiCourses.size(); i++){
            if(obj.getCourses().getApi().get(i).getCourseTitle().equals("Rest Assured Automation using Java")){
                System.out.println(":::"+obj.getCourses().getApi().get(i).getPrice());
                break;
            }
        }

        //get course names of Web automation
        for(int i=0; i<obj.getCourses().getWebAutomation().size(); i++){
            System.out.println(obj.getCourses().getWebAutomation().get(i).getCourseTitle());
        }

    }
}
