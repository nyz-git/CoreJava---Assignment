package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();
	public QueryParameter parseQuery(String queryString) {
		
		//this method will parse the queryString and will return the object of QueryParameter
		//class
		queryParameter.setQueryString(queryString.toLowerCase());
		String baseQuery = queryString/*.split("where|order by|group by")[0]*/;
		queryParameter.setBaseQuery(baseQuery);
		String fileName = queryString.split("from")[1].split("\\s+")[1];
		queryParameter.setFile(fileName);
		queryParameter.setOrderByFields(getOrderByFields(queryString));
		queryParameter.setGroupByFields(getGroupByFields(queryString));
		queryParameter.setFields(getFields(baseQuery));
		queryParameter.setAggregateFunctions(getAggregateFunctions(queryString));
		queryParameter.setLogicalOperators(getLogicalOperators(queryString));
		queryParameter.setRestrictions(getRestrictions(queryString));
		return queryParameter;
	}
	
	private List<String> getFields(String baseQuery) {
		if (baseQuery.contains("group by")) {
			queryParameter.setQUERY_TYPE("GROUP_BY_QUERY");
		}
		else
		{
		queryParameter.setQUERY_TYPE("SIMPLE_QUERY");
		}
		String[] fields = baseQuery.split("select")[1].trim().split("from")[0].split("[\\s,]+");
		List<String> fieldList = new ArrayList<>();
		for (String field : fields) {
			fieldList.add(field.trim());
		}
		return fieldList;
	}

	private List<Restriction> getRestrictions(String queryString) {
		String whereClause = null;
		String[] propertyNameAndValue;
		String propertyName;
		String propertyValue;
		String conditionalOperator;
		Restriction restriction;
		List<Restriction> restrictions = null;

		if (queryString.contains("where")) {
			restrictions = new ArrayList<>();
			whereClause = queryString.split("where")[1].split("group by")[0].split("order by")[0];

			String[] logicalExpressions = whereClause.split("\\s+or\\s+|\\s+and\\s+|\\s+not\\s+");
			for (String expression : logicalExpressions) {
				restriction=new Restriction();
				expression = expression.trim();
				propertyNameAndValue = expression.split("<=|>=|!=|<|>|=");
				propertyName = propertyNameAndValue[0].trim();
				restriction.setPropertyName(propertyName);

				propertyValue = propertyNameAndValue[1].trim();
				restriction.setPropertyValue(propertyValue);

				conditionalOperator = expression.split(propertyName)[1].trim().split(propertyValue)[0];

				restriction.setCondition(conditionalOperator);

				restrictions.add(restriction);
			}

		}
		return restrictions;
	}

	private List<String> getLogicalOperators(String queryString) {
		List<String> logicalOperators = null;
		String whereClause = null;

		if (queryString.contains("where")) {
			whereClause = queryString.split("where\\s+")[1].split("group by")[0].split("order by")[0];
			String[] logicalExpressions = whereClause.split("\\s+or\\s+|\\s+and\\s+|\\s+not\\s+");
			logicalOperators = new ArrayList<>();
			int x = 0;
			for (String expression : logicalExpressions) {
				if (x++ < logicalExpressions.length - 1) {
					logicalOperators.add(whereClause.split(expression.trim())[1].split("\\s+")[1].trim());
				}
			}
		}
		return logicalOperators;

	}

	private List<AggregateFunction> getAggregateFunctions(String queryString) {
		List<AggregateFunction> aggregateFunctionList = null;
		AggregateFunction aggregateFunction;
		if (queryString.contains("count") || queryString.contains("sum") || queryString.contains("min")
				|| queryString.contains("max") || queryString.contains("avg")) {
			queryParameter.setQUERY_TYPE("AGGREGATE_QUERY");
			String[] aggregateFunctions = queryString.split("from")[0].split("select")[1].trim().split(",");
			// get the size
			String aggregate;
			String function;
			String aggregateField;
			aggregateFunctionList = new ArrayList<>();
			for (int i = 0; i < aggregateFunctions.length; i++) {

				aggregate = aggregateFunctions[i].trim();
				if (aggregate.contains("(")) {

					function = aggregate.split("\\(")[0].trim();

					aggregateField = aggregate.split("\\(")[1].trim().split("\\)")[0];
					aggregateFunction = new AggregateFunction();
					aggregateFunction.setFunction(function);
					aggregateFunction.setField(aggregateField);
					aggregateFunctionList.add(aggregateFunction);
				}
			}

		}
		return aggregateFunctionList;
	}

	private List<String> getOrderByFields(String queryString) {
		List<String> orderByFieldList = null;
		if (queryString.contains("order by")) {
			String[] orderByFields = queryString.split("order by\\s+")[1].split("[\\s,]+");
			orderByFieldList = new ArrayList<>();
			for (String field : orderByFields) {
				orderByFieldList.add(field);
			}
		}
		return orderByFieldList;

	}

	private List<String> getGroupByFields(String queryString) {
		
		List<String> groupByFieldList = null;
		if (queryString.contains("group by")) {
			String orderBy = queryString.split("order by")[0];
			String[] groupByFields = orderBy.split("group by")[1].trim().split("\\s");
			
			groupByFieldList = new ArrayList<>();
			for (String field : groupByFields) {
				groupByFieldList.add(field);
			}
		}
		return groupByFieldList;
	}
	
}
