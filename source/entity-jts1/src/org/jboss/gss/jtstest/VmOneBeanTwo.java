/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the VmTwoBeanOne Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the VmTwoBeanOne
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.gss.jtstest;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PURCHASE_ORDER")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VmOneBeanTwo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Collection<VmOneBeanThree> vmOneBeanThrees;
	private double total;

	public void addPurchase(String product, int quantity, double price) {
		if (vmOneBeanThrees == null){
			vmOneBeanThrees = new ArrayList<VmOneBeanThree>();
		}
		VmOneBeanThree beanThree = new VmOneBeanThree();
		beanThree.setOrder(this);
		beanThree.setProduct(product);
		beanThree.setQuantity(quantity);
		beanThree.setSubtotal(quantity * price);
		vmOneBeanThrees.add(beanThree);
		total += quantity * price;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
	public Collection<VmOneBeanThree> getLineItems() {
		return vmOneBeanThrees;
	}

	public double getTotal() {
		return total;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLineItems(Collection<VmOneBeanThree> vmOneBeanThrees) {
		this.vmOneBeanThrees = vmOneBeanThrees;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
