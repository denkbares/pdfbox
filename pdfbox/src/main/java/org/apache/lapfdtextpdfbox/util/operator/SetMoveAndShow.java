/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lapfdtextpdfbox.util.operator;

import java.io.IOException;
import java.util.List;

import org.apache.lapfdtextpdfbox.cos.COSBase;
import org.apache.lapfdtextpdfbox.util.PDFOperator;

/**
 * @author Huault : huault@free.fr
 * @version $Revision: 1.6 $
 */

public class SetMoveAndShow extends OperatorProcessor
{
    /**
     * " Set word and character spacing, move to next line, and show text.
     * @param operator The operator that is being executed.
     * @param arguments List.
     * @throws IOException If there is an error processing the operator.
     */
	@Override
	public void process(PDFOperator operator, List<COSBase> arguments) throws IOException {
        //Set word and character spacing, move to next line, and show text
        //
        if (arguments.size() < 3)
        {
            return;
        }
        context.processOperator("Tw", arguments.subList(0,1));
        context.processOperator("Tc", arguments.subList(1,2));
        context.processOperator("'", arguments.subList(2,3));
    }
}