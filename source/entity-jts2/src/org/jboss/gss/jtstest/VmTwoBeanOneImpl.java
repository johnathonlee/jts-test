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

import javax.ejb.Stateful;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
@Remote(VmTwoBeanOne.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class VmTwoBeanOneImpl implements VmTwoBeanOne, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager manager;
	DataItemBeanImpl di = null;
	static InitialContext context = null;

	@Remove
	public void removeVmTwoBeanOne() {
		System.out.println("VmTwoBeanOne ============== removeVmTwoBeanOne");
		manager.persist(di);
	}

	public void sayVmTwoBeanOne() {
		System.out.println("VmTwoBeanOne =================sayVmTwoBeanOne()");
		di = new DataItemBeanImpl();
		di.setString("VmTwoBeanOne");		
	}
}
