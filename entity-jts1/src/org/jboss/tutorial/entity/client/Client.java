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

import org.jboss.tutorial.entity.bean.LineItem;
import org.jboss.tutorial.entity.bean.Order;
import org.jboss.tutorial.entity.bean.ShoppingCart;

/**
 * Comment
 * 
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 61136 $
 */
public class Client {

	InitialContext ctx = null;

	public static void main(String[] args) throws Exception {
		Client c = new Client();
	}

	public Client() {
		long startTime = System.currentTimeMillis();

		try {
			if (ctx == null) {
				ctx = new InitialContext();
			}
			// Iterations 20
			buyStuff(ctx, 20);
		} catch (Throwable ex) {
			ex.printStackTrace();
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		long stopTime = System.currentTimeMillis();
		long runTime = stopTime - startTime;
		System.out.println("iterations: 20 Run time: " + runTime);

	}

	public void buyStuff(InitialContext ctx, int x) {
		try {
			for (int cnt = 0; cnt <= x; cnt++) {
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
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
