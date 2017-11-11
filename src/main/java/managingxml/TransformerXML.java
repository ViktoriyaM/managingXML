package managingxml;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.TransformerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransformerXML
{
private static final Logger LOGGER = LogManager.getLogger(TransformerXML.class);
/**
 * Transforming XML to XML using XSLT
 *
 * @param xmlSourseFileName The XML input to transform
 * @param xsltFileName      Source of XSLT document used to create Transformer
 * @param xmlResultFileName The Result of transforming the <code>xmlSendersName</code>
 *
 * @return Result of transforming *.xml file (<code>xmlSendersName</code>) with XSLT
 */
final public boolean transform(String xmlSourseFileName, String xsltFileName, String xmlResultFileName)
{
    LOGGER.info("------->Starting transforming XML Data " + xmlSourseFileName + " with XSLT " + xsltFileName + " to file " + xmlResultFileName);
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    try
    {
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File(xsltFileName)));
        transformer.transform(new StreamSource(new File(xmlSourseFileName)), new StreamResult(new File(xmlResultFileName)));
    }
    catch (TransformerConfigurationException | TransformerFactoryConfigurationError ex)
    {
        LOGGER.error(ex);
        return false;
    }
    catch (TransformerException ex)
    {
        LOGGER.error(ex);
        return false;
    }
    LOGGER.info("<-------Done transforming XML " + xmlSourseFileName);
    return true;
}
}
