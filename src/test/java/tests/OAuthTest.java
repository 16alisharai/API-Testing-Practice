package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import reusableMethods.Reusable;

import java.util.*;

import static io.restassured.RestAssured.*;

public class OAuthTest {
//    ******************************************************************
//    Authorization Server EndPoint:
//    https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token
//    HTTP Method : POST
//    Form parameters :
//    client_id:
//            692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com
//    client_secret:  erZOWM9g3UtwNRj340YYaK_W
//    grant_type:   client_credentials
//    scope:  trust
//******************************************************************
//    GetCourseDetails EndPoint (Secured by OAuth) :
//    https://rahulshettyacademy.com/oauthapi/getCourseDetails
//    HTTP Method : GET
//    Query Parameter : access_token

    @Test
    public static void oauthValidationTest(){

        RestAssured.baseURI="https://rahulshettyacademy.com/";
        String res = given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().post("oauthapi/oauth2/resourceOwner/token")
                .then().log().all().extract().response().asString();

        JsonPath js = Reusable.rawToJson(res);
        String access_token = js.get("access_token");

        String res2 = given().queryParam("access_token",access_token)
                .when().get("oauthapi/getCourseDetails")
                .then().log().all().extract().response().asString();
        JsonPath js2 = Reusable.rawToJson(res2);

        HashMap<String,Object> hm = js2.get("courses");
        Set hs = hm.entrySet();
        Iterator it = hs.iterator();

        while(it.hasNext()){
            Map.Entry mp = (Map.Entry)it.next();
            System.out.println(mp.getKey());
            System.out.print(mp.getValue()+"\n");
        }

        for(int i=0 ; i< js2.getInt("courses.webAutomation.size()") ; i++){
            System.out.print(js2.getString("courses.webAutomation["+i+"].courseTitle")+"    ");
            System.out.print(js2.get("courses.webAutomation["+i+"].price")+"\n");
        }

    }
}
