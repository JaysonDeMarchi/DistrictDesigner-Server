/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This function is used to tests if a string is a JSON string or not .
 *
 * @Param test The String to test.
 * @Return boolean This returns test is validate.
 * @author Hengqi Zhu
 */
public class Validator {

  public static boolean isJSONValid(String test) {
    try {
      new JSONObject(test);
    } catch (JSONException ex) {
      try {
        new JSONArray(test);
      } catch (JSONException ex1) {
        return false;
      }
    }
    return true;
  }
}
