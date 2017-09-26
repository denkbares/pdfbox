/*
 * Copyright 2014 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pdfbox.examples.pdmodel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lapfdtextpdfbox.cos.COSArray;
import org.apache.lapfdtextpdfbox.cos.COSDictionary;
import org.apache.lapfdtextpdfbox.cos.COSFloat;
import org.apache.lapfdtextpdfbox.cos.COSInteger;
import org.apache.lapfdtextpdfbox.cos.COSName;
import org.apache.lapfdtextpdfbox.exceptions.COSVisitorException;
import org.apache.lapfdtextpdfbox.pdmodel.PDDocument;
import org.apache.lapfdtextpdfbox.pdmodel.PDPage;
import org.apache.lapfdtextpdfbox.pdmodel.PDResources;
import org.apache.lapfdtextpdfbox.pdmodel.common.function.PDFunctionType2;
import org.apache.lapfdtextpdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.lapfdtextpdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.lapfdtextpdfbox.pdmodel.graphics.shading.PDShadingResources;
import org.apache.lapfdtextpdfbox.pdmodel.graphics.shading.PDShadingType2;

/**
 * This example creates a PDF with type 2 (axial) shading with a type 2
 * (exponential) function.
 *
 * @author Tilman Hausherr
 */
public class CreateGradientShadingPDF
{

    /**
     * This will create the PDF and write the contents to a file.
     *
     * @param file The name of the file to write to.
     *
     * @throws IOException If there is an error writing the data.
     */
    public void create(String file) throws IOException, COSVisitorException
    {
        PDDocument document = null;
        try
        {
            document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // function attributes
            COSDictionary fdict = new COSDictionary();
            fdict.setInt(COSName.FUNCTION_TYPE, 2);
            COSArray domain = new COSArray();
            domain.add(COSInteger.get(0));
            domain.add(COSInteger.get(1));
            COSArray c0 = new COSArray();
            c0.add(COSFloat.get("1"));
            c0.add(COSFloat.get("0"));
            c0.add(COSFloat.get("0"));
            COSArray c1 = new COSArray();
            c1.add(COSFloat.get("0.5"));
            c1.add(COSFloat.get("1"));
            c1.add(COSFloat.get("0.5"));
            fdict.setItem(COSName.DOMAIN, domain);
            fdict.setItem(COSName.C0, c0);
            fdict.setItem(COSName.C1, c1);
            fdict.setInt(COSName.N, 1);
            PDFunctionType2 func = new PDFunctionType2(fdict);

            PDShadingType2 shading = new PDShadingType2(new COSDictionary());

            // shading attributes
            shading.setColorSpace(PDDeviceRGB.INSTANCE);
            shading.setShadingType(PDShadingType2.SHADING_TYPE2);
            COSArray coords = new COSArray();
            coords.add(COSInteger.get(100));
            coords.add(COSInteger.get(400));
            coords.add(COSInteger.get(400));
            coords.add(COSInteger.get(600));
            shading.setCoords(coords);
            shading.setFunction(func);

            // create and add to shading resources
            page.setResources(new PDResources());
            Map<String, PDShadingResources> shadings = new HashMap<String, PDShadingResources>();
			shadings.put("sh1", shading);
			page.getResources().setShadings(shadings);

            // invoke shading from content stream
            PDPageContentStream contentStream = new PDPageContentStream(document, page, true, false);
            contentStream.appendRawCommands("/sh1 sh\n");
            contentStream.close();
            
            document.save(file);
            document.close();
        }
        finally
        {
            if (document != null)
            {
                document.close();
            }
        }
    }

    /**
     * This will create a blank document.
     *
     * @param args The command line arguments.
     *
     * @throws IOException If there is an error writing the document data.
     */
    public static void main(String[] args) throws IOException, COSVisitorException
    {
        if (args.length != 1)
        {
            usage();
        }
        else
        {
            CreateGradientShadingPDF creator = new CreateGradientShadingPDF();
            creator.create(args[0]);
        }
    }

    /**
     * This will print the usage of this class.
     */
    private static void usage()
    {
        System.err.println("usage: java org.apache.pdfbox.examples.pdmodel.CreateGradientShadingPDF <outputfile.pdf>");
    }
}
