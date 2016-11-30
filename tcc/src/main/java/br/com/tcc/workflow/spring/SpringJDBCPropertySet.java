/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package br.com.tcc.workflow.spring;

import com.opensymphony.module.propertyset.AbstractPropertySet;
import com.opensymphony.module.propertyset.PropertyException;

import java.util.Collection;

/**
 * This is an implementation of a property set manager for JDBC. It relies on
 * one table, called "os_propertyset" that has four columns: "type" (integer),
 * "keyValue" (string), "globalKey" (string), and "value" (string). This is not
 * likely to be enough for people who store BLOBS as properties. Of course,
 * those people need to get a life.
 * <p>
 * 
 * For Postgres(?):<br>
 * CREATE TABLE OS_PROPERTYENTRY (GLOBAL_KEY varchar(255), ITEM_KEY
 * varchar(255), ITEM_TYPE smallint, STRING_VALUE varchar(255), DATE_VALUE
 * timestamp, DATA_VALUE oid, FLOAT_VALUE float8, NUMBER_VALUE numeric, primary
 * key (GLOBAL_KEY, ITEM_KEY));
 * <p>
 * 
 * For Oracle (Thanks to Michael G. Slack!):<br>
 * CREATE TABLE OS_PROPERTYENTRY (GLOBAL_KEY varchar(255), ITEM_KEY
 * varchar(255), ITEM_TYPE smallint, STRING_VALUE varchar(255), DATE_VALUE date,
 * DATA_VALUE long raw, FLOAT_VALUE float, NUMBER_VALUE numeric, primary key
 * (GLOBAL_KEY, ITEM_KEY));
 * <p>
 * 
 * Other databases may require small tweaks to the table creation scripts!
 * 
 * <p>
 * 
 * <b>Required Args</b>
 * <ul>
 * <li><b>globalKey</b> - the globalKey to use with this PropertySet</li>
 * </ul>
 * <p>
 * 
 * <b>Required Configuration</b>
 * <ul>
 * <li><b>datasource</b> - JNDI path for the DataSource</li>
 * <li><b>table.name</b> - the table name</li>
 * <li><b>col.globalKey</b> - column name for the globalKey</li>
 * <li><b>col.itemKey</b> - column name for the itemKey</li>
 * <li><b>col.itemType</b> - column name for the itemType</li>
 * <li><b>col.string</b> - column name for the string value</li>
 * <li><b>col.date</b> - column name for the date value</li>
 * <li><b>col.data</b> - column name for the data value</li>
 * <li><b>col.float</b> - column name for the float value</li>
 * <li><b>col.number</b> - column name for the number value</li>
 * </ul>
 * 
 * @version $Revision: 162 $
 * @author <a href="mailto:epesh@hotmail.com">Joseph B. Ottinger</a>
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 */
public class SpringJDBCPropertySet extends AbstractPropertySet {

	@Override public Collection getKeys(String s, int i) throws PropertyException {
		return null;
	}

	@Override public int getType(String s) throws PropertyException {
		return 0;
	}

	@Override public boolean exists(String s) throws PropertyException {
		return false;
	}

	@Override public void remove(String s) throws PropertyException {

	}

	@Override protected void setImpl(int i, String s, Object o) throws PropertyException {

	}

	@Override protected Object get(int i, String s) throws PropertyException {
		return null;
	}
}
