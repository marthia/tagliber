package me.oleg.tagliber.utitlies

import org.w3c.dom.Attr
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class HTMLParser
internal constructor(file: File) {

    private var factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
    private val builder: DocumentBuilder = factory.newDocumentBuilder()
    private val document: Document = builder.parse(file)
    private val nodeList = document.getElementsByTagName("div")

    /**
     * @param attr the attribute tag name to search in the HTML node list
     * @return the content of node based on the provided attribute tag
     */
    fun getItemByClass(attr: String): String {
        var attrIndex = -1

        if (nodeList != null) {
            for (i in 0 until nodeList.length) {

                val attrAtNode = (nodeList.item(i).attributes?.item(0) as Attr).value

                if (attrAtNode == attr) {
                    attrIndex = i
                    break
                }
            }
            // check if the attribute has been found
            if (attrIndex != -1)
                return nodeList.item(attrIndex)?.textContent ?: ""
        }
        return ""
    }
}