package com.stackroute.datamunger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataMunger {

	public static void main(String[] args) {
		// read the query from the user into queryString variable
		DataMunger dataMunger = new DataMunger();

		Scanner sc = new Scanner(System.in);

		String query = sc.next();
		// call the parseQuery method and pass the queryString variable as a
		// parameter
		dataMunger.parseQuery(query);

	}

	public void parseQuery(String queryString) {
		// call the methods
		getSplitStrings(queryString);
		getFile(queryString);
		getBaseQuery(queryString);
		getConditionsPartQuery(queryString);
		getConditions(queryString);
		getLogicalOperators(queryString);
		getFields(queryString);
		getOrderByFields(queryString);
		getGroupByFields(queryString);
		getAggregateFunctions(queryString);
	}

	// parse the queryString into words and display
	public String[] getSplitStrings(String queryString) {
		String[] splittedString = queryString.split("\\w");
		return splittedString;
	}

	// get and display the filename
	public String getFile(String queryString) {
		String filename = queryString.split("from")[1].split("\\s")[1];
		return filename;
	}

	// getting the baseQuery and display
	public String getBaseQuery(String queryString) {
		String baseQuery = queryString.split("where")[0].split("group by")[0].split("order by")[0];
		return baseQuery;

	}

	// get and display the where conditions part(if where condition exists)
	public String getConditionsPartQuery(String queryString) {
		if(queryString.contains("where"))
		{
		String conditionsPartQuery = queryString.split("order by")[0].split("group by")[0].split("where")[1];
		return conditionsPartQuery;
		}
		return null;
	}

	/*
	 * parse the where conditions and display the propertyName, propertyValue
	 * and conditionalOperator for each conditions
	 */
	public String[] getConditions(String queryString) {
		String conditionsPartQuery = getConditionsPartQuery(queryString); // for getting the where condition part to check its existence
		
		if(conditionsPartQuery != null)//checking null for where condition
		{
		String getConditions[] = queryString.split("group by")[0].split("order by")[0].split("where")[1].trim().split("\\s+and\\s|\\s+or\\s");
		return getConditions;
		}
		return null;
	}

	// get the logical operators(applicable only if multiple conditions exist)
	public String[] getLogicalOperators(String queryString) {
		
		String whereClause=null;
		// checking null for where condition
		if(queryString.contains("where"))
		{
			
			whereClause=queryString.split("order by")[0].split("group by")[0].split("where")[1];
			
			//spilting wrt to or and not
			String [] logicalExpressions=whereClause.split("\\s+or\\s+|\\s+and\\s+|\\s+not\\s+");			
			
			List<String> logicalOperators=new ArrayList<>();
			// traverse over the array which is being split on the basis of and | or			
			int x=0;
			for(String expression:logicalExpressions)
			{
				if(x++ < logicalExpressions.length-1)
				{
					// fetch the element and put it in the list
					logicalOperators.add(whereClause.split(expression.trim())[1].split("\\s+")[1].trim());					
				}
			}			
			String [] operators=new String[logicalOperators.size()];
			operators=logicalOperators.toArray(operators);			
			return operators;
		}
		else
		{
			return null;
		}

	}
	
	/* get the fields from the select clause */
	public String[] getFields(String queryString) {
		String[] fields = queryString.split("select")[1].trim().split("from")[0].trim().split("[\\s,]");
		return fields;

	}

	// get order by fields if order by clause exists
	public String[] getOrderByFields(String queryString) {
		String[] orderByFields = queryString.split("order by")[1].trim().split("\\s");
		return orderByFields;
	}

	// get group by fields if group by clause exists
	public String[] getGroupByFields(String queryString) {
		if(queryString.contains("group by"))
		{
		String  groupByField[] = queryString.split("group by")[1].trim().split("\\s");
		return groupByField;
		}
		return null;
	}

	// parse and display aggregate functions(if applicable)
	public String[] getAggregateFunctions(String queryString) {

		return null;
	}

}
