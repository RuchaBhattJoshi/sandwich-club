package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonUtils {

    private static final String JSON_NAME = "name";
    private static final String JSON_MAIN_NAME = "mainName";
    private static final String JSON_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String JSON_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String strJson) throws JSONException {

        Sandwich sandwichObj = new Sandwich();

            JSONObject jsonRootObject = new JSONObject(strJson);
            Log.d(TAG, "received JSONObject: " + jsonRootObject.toString(1));

            String jsonName = jsonRootObject.optString(JSON_NAME);
            sandwichObj.setMainName(jsonName);

            JSONObject jObjectName = new JSONObject(jsonName);

            String jsonMainName = jObjectName.optString(JSON_MAIN_NAME);
            sandwichObj.setMainName(jsonMainName);

            JSONArray jArrayKnownAs = jObjectName.getJSONArray(JSON_ALSO_KNOWN_AS);
            sandwichObj.setAlsoKnownAs(toStringList(jArrayKnownAs));

            String jsonOrigin = jsonRootObject.optString(JSON_PLACE_OF_ORIGIN);
            sandwichObj.setPlaceOfOrigin(jsonOrigin);

            String jsonDescription = jsonRootObject.optString(JSON_DESCRIPTION);
            sandwichObj.setDescription(jsonDescription);

            String jsonImgUrl = jsonRootObject.optString(JSON_IMAGE);
            sandwichObj.setImage(jsonImgUrl);

            JSONArray jArrayIngredients = jsonRootObject.getJSONArray(JSON_INGREDIENTS);
            sandwichObj.setIngredients(toStringList(jArrayIngredients));

            return sandwichObj;

    }

    private static List<String> toStringList(JSONArray jArray) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < jArray.length(); i++) {
            try {
                stringList.add(jArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stringList;
    }

}
