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
package org.jboss.gss.jtstest.client;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.*;

import java.util.Properties;

import org.jboss.gss.jtstest.VmOneBeanThree;
import org.jboss.gss.jtstest.VmOneBeanTwo;
import org.jboss.gss.jtstest.VmOneBeanOne;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 61136 $
 */
public class Client implements Runnable{
	
	public static void main(String[] args) throws Exception {
 
		int i = 1;
		

		while (i < 6){
			Thread thread = new Thread(new Client());  
			thread.start();
			System.out.println("Started: " + i);
			i++;
		}
		 
	}
	InitialContext ctx;

	int iter = 5;

	private UserTransaction ut = null;

	public Client() throws Exception {
	}
	
	public Client(int iter) {
		super();
		this.iter = iter;
	}
	
	public void beginWork(InitialContext ctx, int x) {

		System.out.println("beginWork");
		for (int cnt = 0; cnt <= x; cnt++) {
				
				try {
						VmOneBeanOne beanOne = (VmOneBeanOne) ctx
								.lookup("VmOneBeanOneImpl/remote");
		
						System.out.println("VmOneBeanOneImpl.doWork1");
						beanOne.doWork("work1", 2, 500.00);
						System.out.println("VmOneBeanOneImpl.doWork2");
						beanOne.doWork("work2", 1, 2000.00);
		
						System.out.println("work results:");
						VmOneBeanTwo vmOneBeanTwo = beanOne.getVmOneBeanTwo();
						System.out.println("Total: $" + vmOneBeanTwo.getTotal());
						for (VmOneBeanThree item : vmOneBeanTwo.getLineItems()) {
							System.out.println(item.getQuantity() + "     "
									+ item.getProduct() + "     " + item.getSubtotal());
						}
		
						System.out.println("Checkout purchase: " + cnt);
						beanOne.removeWork();
				} catch (Throwable ex) {
						System.out.println("beginWork:");
						ex.printStackTrace();
				}
		}
	}
	
	private void commitUtx() {
		try {
			ut.commit();
		} catch (SystemException ex){
			System.out.println("SE catch");
			ex.printStackTrace();
		} catch (HeuristicMixedException ex) {
			System.out.println("SE catch");
			ex.printStackTrace();
		} catch (HeuristicRollbackException ex) {
			System.out.println("SE catch");
			ex.printStackTrace();
		} catch (IllegalStateException ex) {
			System.out.println("SE catch");
			ex.printStackTrace();
		} catch (SecurityException ex) {
			System.out.println("SE catch");
			ex.printStackTrace();
		} catch (RollbackException ex) {
			System.out.println("SE catch");
			ex.printStackTrace();
		} catch (RuntimeException ex) {
			System.out.println("SE catch");
			ex.printStackTrace();
		}
	}

	private void getContext() {
    	try {
    		if (ctx == null) {
    			Properties prop=new Properties();
    			prop.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
    			prop.put(Context.PROVIDER_URL,"127.0.0.1:1100");

    			ctx = new InitialContext(prop);

    			System.out.println("InitialContext created for Client");
    		}
    	} catch (NamingException e) {
    		e.printStackTrace();
    	}    	
	}

	public int getIter() {
		return iter;
	}
	
    private UserTransaction getUtx() throws Exception {
    	System.out.println("------------------> getUtx()");
    	Properties properties = new Properties();
    	properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.NamingContextFactory");
    	properties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming.client:org.jnp.interfaces");
    	properties.setProperty("java.naming.provider.url", "127.0.0.1:1100");
    	Context context;
    	javax.transaction.UserTransaction utx = null;
		
    	context = new InitialContext(properties);
		utx = (UserTransaction) context.lookup("UserTransaction");
		System.out.println("------------------> UTX is initialized!");
		
		
		return utx;
    }
    
    
    @Override
	public void run() {
		long startTime = System.currentTimeMillis();
		

		getContext();

		try {
			if (startUtx()){

				System.out.println("------------------> startUtx Passed");
				beginWork(ctx, iter);

				commitUtx();  

				long stopTime = System.currentTimeMillis();
				long runTime = stopTime - startTime;
				System.out.println("iterations: " + iter + " Run time: " + runTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public void setIter(int iter) {
		this.iter = iter;
	}

	private boolean startUtx() throws Exception{
		System.out.println("------------------> startUtx()");
		
		boolean started = false;
		
		if ((ut = getUtx()) == null){
			return started;
		};

		System.out.println("------------------> ut.begin");
		ut.begin();	
		started = true;

		return started;
	}
}
