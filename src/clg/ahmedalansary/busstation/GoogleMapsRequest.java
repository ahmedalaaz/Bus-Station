package clg.ahmedalansary.busstation;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoogleMapsRequest {
	 private static final String API_KEY="AIzaSyDJPdLHrmwHevrM_WSApVF3Z4Acl-HZ_B0";
	    OkHttpClient client = new OkHttpClient();


	public Object[] calculate(String source ,String destination) throws IOException {
	String url="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+source+"&destinations="+destination+"&key="+ API_KEY;
	            Request request = new Request.Builder()
	                .url(url)
	                .build();

	            Response response = client.newCall(request).execute();
	            
	            JSONParser parser = new JSONParser();
	            try {
	             JSONObject obj = (JSONObject) parser.parse(response.body().string());
	             JSONObject jsonobj=obj;
	             System.out.println(response.body().string());
	             JSONArray dist=(JSONArray)jsonobj.get("rows");
	             JSONObject obj2 = (JSONObject)dist.get(0);
	             JSONArray disting=(JSONArray)obj2.get("elements");
	             JSONObject obj3 = (JSONObject)disting.get(0);
	             JSONObject distance=(JSONObject)obj3.get("distance");
	             JSONObject duration=(JSONObject)obj3.get("duration");
	             System.out.println(distance.get("text"));
	             System.out.println(duration.get("text"));
	             Object[] ret = new Object[2];
	             ret[0] = distance.get("text");
	             ret[1] = duration.get("text");
	             return ret;
	             
	        }
	    catch(Exception e) {
	        e.printStackTrace();
	    }
	            return null;
	          }
}
