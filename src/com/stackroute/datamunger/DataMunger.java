package com.stackroute.datamunger;

import java.util.ArrayList;
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
		String splitAtWhere[] = queryString.split("group by")[0].split("order by")[0].split("where")[1].split("\\s");

		if (splitAtWhere.length > 1) {

			ArrayList<String> whereCondition = new ArrayList<>();

			String whereString = splitAtWhere[1].trim();
			String[] whereCond = whereString.split("\\s+");

			for (String s : whereCond) {
				if (s.equals("or")) {
					whereCondition.add("or");
				} else if (s.equals("and")) {
					whereCondition.add("and");
				}
			}
			String whereClauseElement[] = whereString.split(" and | or ", 2);
			while (whereClauseElement.length != 1) {
				whereClauseElement = whereClauseElement[1].split(" and | or ", 2);
			}
		return whereClauseElement;

	}
		return null;
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
