package com.stackroute.datamunger;

import java.util.Scanner;

import com.stackroute.datamunger.query.parser.QueryParser;

public class DataMunger {

	public static void main(String[] args) {

		
		//read the query from the user
		Scanner sc=new Scanner(System.in);
		String queryString=sc.next();
		
		//create an object of QueryParser class
		QueryParser queryParser=new QueryParser();
		
		//call parseQuery() method of queryParser
		queryParser.parseQuery(queryString);
		

	}

	

}
