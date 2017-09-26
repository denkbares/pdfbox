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
package org.apache.lapfdtextpdfbox.pdmodel.interactive.form;

import java.io.IOException;

import org.apache.lapfdtextpdfbox.cos.COSDictionary;
import org.apache.lapfdtextpdfbox.cos.COSName;
import org.apache.lapfdtextpdfbox.cos.COSString;

/**
 * A class for handling the PDF field as a PDPushButton.
 *
 * @author sug
 * @version $Revision: 1.3 $
 */
public class PDPushButton extends PDField
{

    /**
     * @see PDField#PDField(PDAcroForm, COSDictionary)
     *
     * @param theAcroForm The acroForm for this field.
     * @param field The field for this push button.
     */
    public PDPushButton( PDAcroForm theAcroForm, COSDictionary field)
    {
        super(theAcroForm, field);
    }

    /**
     * @see PDField#setValue(java.lang.String)
     *
     * @param value The new value for the field.
     *
     * @throws IOException If there is an error creating the appearance stream.
     */
	@Override
	public void setValue(String value) throws IOException {
        COSString fieldValue = new COSString(value);
        getDictionary().setItem( COSName.getPDFName( "V" ), fieldValue );
        getDictionary().setItem( COSName.getPDFName( "DV" ), fieldValue );
    }

    /**
     * getValue gets the fields value to as a string.
     *
     * @return The string value of this field.
     *
     * @throws IOException If there is an error getting the value.
     */
	@Override
	public String getValue() throws IOException {
        return getDictionary().getString( "V" );
    }
}
