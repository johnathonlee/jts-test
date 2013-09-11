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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LINE_ITEM")
public class VmOneBeanThree implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private double subtotal;
    private int quantity;
    private String product;
    private VmOneBeanTwo vmOneBeanTwo;

   @Id
   @GeneratedValue
   public int getId()
   {
      return id;
   }
   
   public void setId(int id)
   {
      this.id = id;
   }

   public double getSubtotal()
   {
      return subtotal;
   }

   public void setSubtotal(double subtotal)
   {
      this.subtotal = subtotal;
   }

   public int getQuantity()
   {
      return quantity;
   }

   public void setQuantity(int quantity)
   {
      this.quantity = quantity;
   }

   public String getProduct()
   {
      return product;
   }

   public void setProduct(String product)
   {
      this.product = product;
   }

   @ManyToOne
   @JoinColumn(name = "order_id")
   public VmOneBeanTwo getOrder()
   {
      return vmOneBeanTwo;
   }

   public void setOrder(VmOneBeanTwo vmOneBeanTwo)
   {
      this.vmOneBeanTwo = vmOneBeanTwo;
   }
}