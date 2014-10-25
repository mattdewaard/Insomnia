package model;

import java.util.ArrayList;

public class DataSimpleton {
	private static DataSimpleton data;
	private static ArrayList<String> tempData;

	private DataSimpleton() {
		tempData = new ArrayList<String>();
	}

	public static DataSimpleton get() {
		if (data == null)
			data = new DataSimpleton();
		return data;
	}

	public static void addData(String data) {
		tempData.add(data);
	}

	public static String getData() {
		if(tempData.size() != 0){
			return tempData.remove(tempData.size() - 1);
		}
		return null;
	}

	public static void clear() {
		tempData = new ArrayList<String>();
	}
}