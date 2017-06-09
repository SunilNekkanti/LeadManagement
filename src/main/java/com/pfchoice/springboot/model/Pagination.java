package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;



/**
 *
 * @author sarath
 */
@Resource
public class Pagination<T>  implements Serializable {

	private static final long serialVersionUID = 1L;

	
     private int draw;
     
     private int recordsFiltered;
     
     private int recordsTotal;
     
     private List<T> data ;
	

	/**
	 * 
	 */
	public Pagination() {
		super();
	}

	/**
	 * @param id
	 */
	public Pagination(final int draw, final int recordsFiltered,final int recordsTotal,final List<T> data) {
		this.draw = draw;
		this.recordsFiltered = recordsFiltered;
		this.recordsTotal = recordsTotal;
		this.data = data;
	}

	/**
	 * @return the draw
	 */
	public int getDraw() {
		return draw;
	}

	/**
	 * @param draw the draw to set
	 */
	public void setDraw(int draw) {
		this.draw = draw;
	}

	/**
	 * @return the recordsFiltered
	 */
	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	/**
	 * @param recordsFiltered the recordsFiltered to set
	 */
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	/**
	 * @return the recordsTotal
	 */
	public int getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * @param recordsTotal the recordsTotal to set
	 */
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}


}
