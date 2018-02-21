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
package org.apache.lapfdtextpdfbox.pdmodel.graphics.shading;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lapfdtextpdfbox.util.Matrix;

/**
 * AWT PaintContext for function-based (Type 1) shading.
 */
public class Type1ShadingPaint implements Paint
{
    private static final Log LOG = LogFactory.getLog(Type1ShadingPaint.class);

	private final PDShadingType1 shading;
	private final Matrix ctm;
	private final int pageHeight;

    /**
     * Constructor.
     *
     * @param shading the shading resources
     * @param ctm current transformation matrix
     * @param pageHeight the height of the page
     */
    public Type1ShadingPaint(PDShadingType1 shading, Matrix ctm, int pageHeight)
    {
        this.shading = shading;
        this.ctm = ctm;
        this.pageHeight = pageHeight;
    }

    /**
     * {@inheritDoc}
     */
    public int getTransparency()
    {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public PaintContext createContext(ColorModel cm, Rectangle deviceBounds,
            Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)
    {
        try
        {
            return new Type1ShadingContext(shading, cm, xform, ctm, pageHeight, deviceBounds);
        }
        catch (IOException ex)
        {
            LOG.error(ex);
            return null;
        }
    }
}