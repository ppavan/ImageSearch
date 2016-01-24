
package com.example.imagesearch;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class gallaryUtil {
	static public class galleryJSONParser {
		static ArrayList<ParsedDetail> parseGallery(String in, String search)
				throws JSONException {
			ArrayList<ParsedDetail> parsedDetailList = new ArrayList<ParsedDetail>();
			JSONObject object = new JSONObject(in);
			JSONArray PhotoJSONArray = object.getJSONArray("photos");
			for (int i = 0; i < PhotoJSONArray.length(); i++) {
				ParsedDetail parsedDetail = new ParsedDetail();
				JSONObject photoDetails = PhotoJSONArray.getJSONObject(i);
				parsedDetail.setPhotoName(photoDetails.getString("name"));

				parsedDetail.setPhotoUrl(photoDetails.getString("image_url"));

				String fName, lName;
				JSONObject user = photoDetails.getJSONObject("user");
				fName = user.getString("firstname");
				lName = user.getString("lastname");
				parsedDetail.setOwnerName(fName + " " + lName);

				parsedDetail.setSearchterm(search);

				parsedDetail.setOwnerUrl(user.getString("userpic_url"));
				Log.d("demo", parsedDetail.toString());
				parsedDetailList.add(parsedDetail);
			}
			return parsedDetailList;
		}
	}
}
