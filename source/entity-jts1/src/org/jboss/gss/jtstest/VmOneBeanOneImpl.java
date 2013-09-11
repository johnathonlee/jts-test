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

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Properties;

@Stateful
@Remote(VmOneBeanOne.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VmOneBeanOneImpl implements VmOneBeanOne, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager manager;
	private VmOneBeanTwo vmOneBeanTwo;
	static InitialContext context;

	@Override
	public void doWork(String product, int quantity, double price) {

		try {
			getContext();
		} catch (javax.naming.NamingException ex){
			System.out.println("SE catch");
			ex.printStackTrace();
			return;
		}

		if (vmOneBeanTwo == null)
			vmOneBeanTwo = new VmOneBeanTwo();
			vmOneBeanTwo.addPurchase(product, quantity, price);

		//if (true) {
			goToVmTwo();
		//}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void goToVmTwo() {

		try {
			getContext();
			Object obj = context.lookup("VmTwoBeanOneImpl/remote");
			System.out.println("-->> lookup VmTwoBeanOneImpl/remote successfully");
			VmTwoBeanOne vmTwoBeanOne = (VmTwoBeanOne) obj;
			vmTwoBeanOne.sayVmTwoBeanOne();
			vmTwoBeanOne.removeVmTwoBeanOne();

		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	private void getContext() throws NamingException {
		if (context == null) {
			Properties prop=new Properties();
			prop.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
			prop.put(Context.PROVIDER_URL,"127.0.0.1:1100");
			context = new InitialContext(prop);
		}
	}

	@Override
	public VmOneBeanTwo getVmOneBeanTwo() {
		return vmOneBeanTwo;
	}

	@Override
	@Remove
	public void removeWork() {
		manager.persist(vmOneBeanTwo);
	}
}
