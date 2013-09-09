/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.tutorial.entity.client;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.*;

import java.util.Properties;

import org.jboss.tutorial.entity.bean.LineItem;
import org.jboss.tutorial.entity.bean.Order;
import org.jboss.tutorial.entity.bean.ShoppingCart;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 61136 $
 */
public class Client implements Runnable{
	
	int iter = 25;
	public Client(int iter) {
		super();
		this.iter = iter;
	}

	public int getIter() {
		return iter;
	}

	public void setIter(int iter) {
		this.iter = iter;
	}

	InitialContext ctx;
	private UserTransaction ut = null;
	
	public static void main(String[] args) throws Exception {
 
		int i = 0;
		
		while (i < 5){
			Client c = new Client();
			c.run();
			i++;
		}
		 
	}
	
	public Client() throws Exception {


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

	public void buyStuff(InitialContext ctx, int x) {

		System.out.println("buyStuff");
		for (int cnt = 0; cnt <= x; cnt++) {
				
				try {
						ShoppingCart cart = (ShoppingCart) ctx
								.lookup("ShoppingCartBean/remote");
		
						System.out.println("Buying 2 memory sticks");
						cart.buy("Memory stick", 2, 500.00);
						System.out.println("Buying a laptop");
						cart.buy("Laptop", 1, 2000.00);
		
						System.out.println("Print cart:");
						Order order = cart.getOrder();
						System.out.println("Total: $" + order.getTotal());
						for (LineItem item : order.getLineItems()) {
							System.out.println(item.getQuantity() + "     "
									+ item.getProduct() + "     " + item.getSubtotal());
						}
		
						System.out.println("Checkout purchase: " + cnt);
						cart.checkout();
				} catch (Throwable ex) {
						System.out.println("buyStuff: first catch");
						ex.printStackTrace();
				}
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

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		

		getContext();

		try {
			if (startUtx()){

				System.out.println("------------------> startUtx Passed");
				buyStuff(ctx, iter);

				commitUtx();  

				long stopTime = System.currentTimeMillis();
				long runTime = stopTime - startTime;
				System.out.println("iterations: " + iter + " Run time: " + runTime);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
