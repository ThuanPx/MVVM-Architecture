import org.w3c.dom.Element
import org.w3c.dom.Node

import javax.xml.parsers.DocumentBuilderFactory

//How to use :  gradle generateDimen

abstract class DimenFactory : DefaultTask() {
    @Input
    var fromDimen = 360

    @Input
    var dimens = listOf(320, 360, 384, 411, 480, 518, 540, 600, 800, 1024, 1280)
    var maxSize = 810

    @Input
    var dimenFileName = "values/dimens.xml"

    private val resFolder = project.projectDir.path + "/src/main/res/"

    @TaskAction
    fun create() {
        createDimenFromDimenFile()
    }

    private fun createDimenFromDimenFile() {
        println("Start convert dimen from value file to other screen size")
        val path = resFolder + dimenFileName
        val pairs = HashMap<String, String>()
        try {
            val fXmlFile = File(path)
            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val doc = dBuilder.parse(fXmlFile)
            doc.documentElement.normalize()

            val nList = doc.getElementsByTagName("dimen")
            for (temp in 0 until nList.length) {
                val nNode = nList.item(temp)
                if (nNode.nodeType == Node.ELEMENT_NODE) {
                    val eElement: Element = nNode as Element
                    pairs[eElement.getAttribute("name")] = eElement.textContent
                }
            }

            val dps = HashMap<String, Double>()
            val sps = HashMap<String, Double>()
            val others = HashMap<String, String>()

            for (entry: Map.Entry<String, String> in pairs) {
                var mVal = entry.value
                if (mVal.contains("dp") || mVal.contains("sp")) {
                    mVal = mVal.replace("dp", "").replace("sp", "")
                    val valDouble = java.lang.Double.parseDouble(mVal)
                    if (entry.value.contains("dp")) {
                        dps[entry.key] = valDouble
                    } else {
                        sps[entry.key] = valDouble
                    }
                } else {
                    others[entry.key] = entry.value
                }
            }

            //Sort
            val dpsSort = java.util.TreeMap(dps)
            val spsSort = java.util.TreeMap(sps)
            println(fXmlFile.absolutePath)
            dimens.forEach { dimen ->
                val screenSize = "values-sw" + dimen + "dp"
                val folder = resFolder + screenSize
                val fileName = "$folder/dimens.xml"
                println(fileName)
                if (fXmlFile.absolutePath.equals(fileName)) {
                    return@forEach
                }
                File(folder).mkdir()
                File(fileName).createNewFile()
                val printWriter = java.io.PrintWriter(fileName)
                printWriter.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                printWriter.println("<resources>")
                for (entry: Map.Entry<String, Double> in dpsSort) {
                    val ratio = entry.value / fromDimen
                    val sdp = ratio * dimen
                    printWriter.printf("\t<dimen name=\"%s\">%.2fdp</dimen>\r\n", entry.key, sdp)
                }
                for (entry: Map.Entry<String, Double> in spsSort) {
                    val ratio = entry.value / fromDimen
                    val sdp = ratio * dimen
                    printWriter.printf("\t<dimen name=\"%s\">%.2fsp</dimen>\r\n", entry.key, sdp)
                }
                for (entry: Map.Entry<String, String> in others) {
                    printWriter.printf("\t<dimen name=\"%s\">%s</dimen>\r\n", entry.key, entry.value)
                }
                printWriter.println("</resources>")
                printWriter.close()
            }
        } catch (e: Exception) {
            println(e.message)
        }

    }
}

tasks.register<DimenFactory>("generateDimen") {
    dimens = listOf(320, 360, 384, 411, 480, 518, 540, 600, 800, 1024, 1280)
    fromDimen = 360
    maxSize = 1280
    dimenFileName = "values/dimens.xml"
}

//afterEvaluate {
//    tasks["clean"].dependsOn("generateDimen")
//}