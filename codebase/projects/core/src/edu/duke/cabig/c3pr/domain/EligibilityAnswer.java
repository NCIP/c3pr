/*
 * Created Thu Apr 20 17:45:32 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

/**
 * A class that represents a row in the 'PATIENT_EC_DATA' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class EligibilityAnswer implements Serializable
{
    private Integer id;
    private Integer questionId;
    private String answer;
    
    
	public EligibilityAnswer()
	{
	}

	
	public EligibilityAnswer(Integer questionId, String answer)
	{
		this.questionId = questionId;
		this.answer = answer;
	}


	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer(String answer)
	{
		this.answer = answer;
	}

	public Integer getQuestionId()
	{
		return questionId;
	}

	public void setQuestionId(Integer formId)
	{
		this.questionId = formId;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
    
}
