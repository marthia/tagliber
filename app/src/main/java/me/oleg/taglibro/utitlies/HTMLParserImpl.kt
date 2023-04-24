package me.oleg.taglibro.utitlies

import org.w3c.dom.Attr
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class HTMLParserImpl(
    private val file: File,
    private val node: String
) : HTMLParser {

    private val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
    private val builder: DocumentBuilder = factory.newDocumentBuilder()


    /**
     * @param tag the attribute tag name to search in the HTML node list
     * @return the content of node based on the provided attribute tag
     */
    override fun execute(tag: String): String {
        val document: Document = builder.parse(file)
        val nodeList = document.getElementsByTagName(node)

        var attrIndex = -1

        if (nodeList == null) return ""

        for (i in 0 until nodeList.length) {

            val attrAtNode = (nodeList.item(i).attributes?.item(0) as Attr).value

            if (attrAtNode == tag) {
                attrIndex = i
                break
            }

        }
        // check if the attribute has been found
        return if (attrIndex != -1)
            nodeList.item(attrIndex)?.textContent ?: ""
        else ""
    }
}