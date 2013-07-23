package com.tripman.engine;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.transform.TransformerException;


public interface IBoExpenseManager {
	 
	 List<BoExpense> getAllExpences(BoSearchExpenseType Searchtype );
	 List<BoExpense> getParticipantExpence(BoSearchExpenseType Searchtype,int pId );
	// public int  getNewExpenceId(int aExpenceId);
	 boolean addExpence(BoExpense aExpence);
	 boolean deleteExpence(int aExpenceId);
	 boolean modifyExpence(BoExpense aExpence);
	 List<BoExpense> getDayExpence(BoSearchExpenseType searchType, Calendar dayExpense);
	 void serialize(String aPath) throws TransformerException;
	// void deserialize();
	 BoExpenseManager getexpman();
	 

}
