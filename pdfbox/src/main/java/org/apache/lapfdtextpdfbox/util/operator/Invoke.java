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
import java.util.Map;

import org.apache.lapfdtextpdfbox.cos.COSBase;
import org.apache.lapfdtextpdfbox.cos.COSName;
import org.apache.lapfdtextpdfbox.cos.COSStream;
import org.apache.lapfdtextpdfbox.pdmodel.PDResources;
import org.apache.lapfdtextpdfbox.pdmodel.graphics.xobject.PDXObject;
import org.apache.lapfdtextpdfbox.pdmodel.graphics.xobject.PDXObjectForm;
import org.apache.lapfdtextpdfbox.util.Matrix;
import org.apache.lapfdtextpdfbox.util.PDFMarkedContentExtractor;
import org.apache.lapfdtextpdfbox.util.PDFOperator;

/**
 * Invoke named XObject.
 *
 * @author <a href="mailto:ben@benlitchfield.com">Ben Litchfield</a>
 * @author Mario Ivankovits
 *
 * @version $Revision: 1.9 $
 */
public class Invoke extends OperatorProcessor
{
    /**
     * process : Do - Invoke a named xobject.
     * 
     * @param operator The operator that is being executed.
     * @param arguments List
     *
     * @throws IOException If there is an error processing this operator.
     */
	@Override
	public void process(PDFOperator operator, List<COSBase> arguments) throws IOException {
        if (arguments.size() < 1)
        {
            return;
        }
        COSBase base0 = arguments.get(0);
        if (!(base0 instanceof COSName))
        {
            return;
        }
        COSName name = (COSName) base0;

        Map<String,PDXObject> xobjects = context.getXObjects();
		PDXObject xobject = xobjects.get(name.getName());
		if (context instanceof PDFMarkedContentExtractor)
        {
            ((PDFMarkedContentExtractor) context).xobject(xobject);
        }

        if(xobject instanceof PDXObjectForm)
        {
            PDXObjectForm form = (PDXObjectForm)xobject;
            COSStream formContentstream = form.getCOSStream();
            // if there is an optional form matrix, we have to map the form space to the user space
            Matrix matrix = form.getMatrix();
            if (matrix != null) 
            {
                Matrix xobjectCTM = matrix.multiply( context.getGraphicsState().getCurrentTransformationMatrix());
                context.getGraphicsState().setCurrentTransformationMatrix(xobjectCTM);
            }
            // find some optional resources, instead of using the current resources
            PDResources pdResources = form.getResources();
            context.processSubStream( context.getCurrentPage(), pdResources, formContentstream );
        }
    }
}