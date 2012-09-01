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
package org.jboss.tutorial.entity.bean;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Remote(NextFree.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class NextFreeBean implements Free, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager manager;
	NextDataItem di = null;

	public void sayFree() {
		System.out.println("NextFree");
		di = new NextDataItem();
		di.setString("next free item");
	}

	@Remove
	public void leaveFree() {
		System.out.println("removeNextFree");
		manager.persist(di);
	}
}
