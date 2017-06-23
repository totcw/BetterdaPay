package com.betterda.betterdapay.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;


/**json封装
* @author Administrator
		*
		*/
public class GsonTools {
	private static Gson gson = null;

	static
	{
		if(null == gson)
		{
			gson = new GsonBuilder().create();
		}
	}

	/**
	 * 将对象转化成 json
	 * @param list
	 * @param cls
	 * @return
	 */
	public  static String getJsonString(Object object){

		return gson.toJson(object);
	}

	/**
	 * 将封装了String的list转化成 json
	 * @param list
	 * @param cls
	 * @return
	 */
	public  static String getJsonListString(List<String> list){

		return gson.toJson(list);
	}

	/**
	 * 将封装了对象的list转化成 json
	 * @param list
	 * @param cls
	 * @return
	 */
	public static <T> String getJsonListObject(List<T> list){

		return gson.toJson(list, new TypeToken<List<T>>(){}.getType());
	}




}
