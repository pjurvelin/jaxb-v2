/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.codemodel.tests;

import java.util.ArrayList;

import org.junit.Test;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JForEach;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.codemodel.writer.SingleStreamCodeWriter;

/**
 * 
 * Simple program to test the generation of the enhanced for loop in jdk 1.5
 * 
 * @author Bhakti Mehta Bhakti.Mehta@sun.com
 * 
 */

public class ForEachTest {

	@Test
	public void main() throws Exception {

		JCodeModel cm = new JCodeModel();
		JDefinedClass cls = cm._class("Test");

		JMethod m = cls.method(JMod.PUBLIC, cm.VOID, "foo");
		m.body().decl(cm.INT, "getCount");

		// This is not exactly right because we need to
		// support generics
		JClass arrayListclass = cm.ref(ArrayList.class);
		JVar $list = m.body().decl(arrayListclass, "alist",
				JExpr._new(arrayListclass));

		JClass $integerclass = cm.ref(Integer.class);
		JForEach foreach = m.body().forEach($integerclass, "count", $list);
		JVar $count1 = foreach.var();
		foreach.body().assign(JExpr.ref("getCount"), JExpr.lit(10));

		// printing out the variable
		JFieldRef out1 = cm.ref(System.class).staticRef("out");
		// JInvocation invocation =
		foreach.body().invoke(out1, "println").arg($count1);

		cm.build(new SingleStreamCodeWriter(System.out));
	}
}
