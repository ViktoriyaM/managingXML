package managingxml;

import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

import java.io.File;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ValidatorXML
{
private static final Logger LOGGER = LogManager.getLogger(ValidatorXML.class);
/**
 * Checks the *.xml file for according with the schema in the *.xsd file,
 * obtained as command-line parameters
 *
 * @param xmlSourseFileName XML to be validated
 * @param xsdFileName       File that represents a schema
 *
 * @return Result of validating *.xml file (<code>xmlSendersName</code>) with XML Schema
 */
final public boolean validate(String xmlSourseFileName, String xsdFileName)
{
    LOGGER.info("------->Starting validating XML Data " + xmlSourseFileName + " with XML Schema " + xsdFileName);
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try
    {
        Schema schema = schemaFactory.newSchema((new File(xsdFileName)));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new File(xmlSourseFileName)));
    }
    catch (IllegalArgumentException | IOException | SAXException | NullPointerException ex)
    {
        LOGGER.error(ex);
        return false;
    }
    LOGGER.info("<-------Done validating XML " + xmlSourseFileName);
    return true;
}
}
