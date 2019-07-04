package com.zaigo.coordinatelayouttest.common;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
	static SharedPreferences preferences;

	public static void setUser_FirstName(String user_FirstName, Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_FirstName", user_FirstName).apply();
	}

	public static String getUser_FirstName(Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_FirstName", "");
	}




	public static void setUser_Name(String user_Name, Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_Name", user_Name).apply();
	}

	public static String getUser_Name(Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_Name", "");
	}



	public static void setUser_Email(String user_Email, Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_Email", user_Email).apply();
	}

	public static String getUser_Email(Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_Email", "");
	}


	public static void setUser_Address(String user_Address, Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_Address", user_Address).apply();
	}

	public static String getUser_Address(Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_Address", "");
	}

	public static void setUser_MobileNumber(String user_MobileNumber, Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_MobileNumber", user_MobileNumber).apply();
	}

	public static String getUser_MobileNumber(Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_MobileNumber", "");
	}



	public static void setUser_Role(String user_Role, Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_Role", user_Role).apply();
	}

	public static String getUser_Role(Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_Role", "");
	}


	public static void setUser_Image(String user_Image, Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_Image", user_Image).apply();
	}

	public static String getUser_Image(Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_Image", "");
	}


	public static void setUser_Id(String user_Id, Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_Id", user_Id).apply();
	}

	public static String getUser_Id(Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_Id", "");
	}


	public static void setRole_Id(String role_Id, Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("role_Id", role_Id).apply();
	}

	public static String getRole_Id(Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("role_Id", "");
	}

	public static void setCompany_id(String company_Id, Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("company_Id", company_Id).apply();
	}

	public static String getCompany_Id(Context context) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("company_Id", "");
	}


}
