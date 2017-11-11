package managingxml;

import java.util.regex.Pattern;

public class Manager
{
private static Pattern namePattern = Pattern.compile("([^\\s]+(\\.(?i)(xml|xsd|xslt))$)");
/**
 * Main method.
 *
 * @param args The command line arguments. Should consist the name of the source *.xml file,
 *             the name of the *.xsd file, the name of the *.xslt file, the name of the resulting *.xml file
 */
public static void main(String[] args)
{
    if (args.length < 4)
    {
        System.err.println("User input error: you must specify the name of the source *.xml file, "
                           + "the name of the *.xsd file, the name of the *.xslt file, the name of the resulting *.xml file");
        return;
    }

    updateExtensions(args);
    
    final String xmlSourseFileName = args[0];
    final String xsdFileName = args[1];
    final String xsltFileName = args[2];
    final String xmlResultFileName = args[3];
    
    ValidatorXML validator = new ValidatorXML();
    boolean status = validator.validate(xmlSourseFileName, xsdFileName);
    if (!status)
    {
        System.err.println("ERROR: Validating XML Data (" + xmlSourseFileName + ") with XML Schema (" + xsdFileName + ") failed!");
        return;
    }
    
    TransformerXML transformer = new TransformerXML();
    status = transformer.transform(xmlSourseFileName, xsltFileName, xmlResultFileName);
    if (!status)
    {
        System.err.println("ERROR: Transforming XML Data (" + xmlSourseFileName + ") with XSLT (" + xsltFileName + ") failed!");
        return;
    }
    
    status = validator.validate(xmlResultFileName, xsdFileName);
    if (!status)
    {
        System.err.println("ERROR: Validating XML Data (" + xmlResultFileName + ") with XML Schema (" + xsdFileName + ") after transforming with XSLT (" + xsltFileName + ") failed!");
        return;
    }
    System.out.println("Successfully.");
}

/**
 * Checks for the presence of an extension in the file name (in all <code>args</code>), if it does not, adds an extension to the file name.
 *
 * @param args The command line arguments
 */
public static void updateExtensions(String[] args)
{
    for (int i = 0; i < args.length; i++)
    {
        boolean result = namePattern.matcher(args[i]).matches();
        if (!result)
        {
            switch (i)
            {
            case 0:
                args[i] = args[i].concat(".xml");
                break;
            case 1:
                args[i] = args[i].concat(".xsd");
                break;
            case 2:
                args[i] = args[i].concat(".xslt");
                break;
            case 3:
                args[i] = args[i].concat(".xml");
                break;
            }
        }
    }
}
}
