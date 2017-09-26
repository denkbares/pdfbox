/*****************************************************************************
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 ****************************************************************************/

package org.apache.pdfbox.preflight.annotation;

import org.apache.lapfdtextpdfbox.cos.COSDictionary;
import org.apache.lapfdtextpdfbox.cos.COSName;
import org.apache.lapfdtextpdfbox.pdmodel.interactive.annotation.PDAnnotationText;
import org.apache.pdfbox.preflight.PreflightContext;
import org.apache.pdfbox.preflight.ValidationResult.ValidationError;

import static org.apache.pdfbox.preflight.PreflightConstants.ERROR_ANNOT_MISSING_FIELDS;
import static org.apache.pdfbox.preflight.PreflightConstants.ERROR_ANNOT_NOT_RECOMMENDED_FLAG;

/**
 * Validation class for Text Annotation
 */
public class TextAnnotationValidator extends AnnotationValidator
{
    /**
     * PDFBox object which wraps the annotation dictionary
     */
    protected PDAnnotationText pdText = null;

    public TextAnnotationValidator(PreflightContext ctx, COSDictionary annotDictionary)
    {
        super(ctx, annotDictionary);
        this.pdText = new PDAnnotationText(annotDictionary);
        this.pdAnnot = this.pdText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.awl.edoc.pdfa.validation.annotation.AnnotationValidator#checkFlags( java.util.List)
     */
	@Override
	protected boolean checkFlags() {
        // call common flags settings
        boolean result = super.checkFlags();

        /*
         * For Text Annotation, this two flags should be set to avoid potential ambiguity between the annotation
         * dictionary and the reader behavior.
         */
        result = result && this.pdAnnot.isNoRotate();
        result = result && this.pdAnnot.isNoZoom();
        if (!result)
        {
            ctx.addValidationError(new ValidationError(ERROR_ANNOT_NOT_RECOMMENDED_FLAG));
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @seenet.awl.edoc.pdfa.validation.annotation.AnnotationValidator# checkMandatoryFields(java.util.List)
     */
	@Override
	protected boolean checkMandatoryFields() {
        boolean subtype = this.annotDictionary.containsKey(COSName.SUBTYPE);
        boolean rect = this.annotDictionary.containsKey(COSName.RECT);
        boolean f = this.annotDictionary.containsKey(COSName.F);
        boolean contents = this.annotDictionary.containsKey(COSName.CONTENTS);
        /*
         * Since PDF 1.5, two optional entries are possible. These new entries seem to e compatible with the PDF/A
         * specification (used to set a State to the annotation - ex : rejected, reviewed...)
         */
        boolean result = (subtype && rect && f && contents);
        if (!result)
        {
            ctx.addValidationError(new ValidationError(ERROR_ANNOT_MISSING_FIELDS));
        }
        return result;
    }
}
