package com.github.mamedovartur.confluencegradleplugin.services;

import com.github.mamedovartur.confluencegradleplugin.models.PageToPublish;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.codehaus.groovy.runtime.EncodingGroovyMethods.md5;

public class AsciiDoctorHtmlPreparer {

    public List<PageToPublish> prepareHtml(String path, String baseName) throws IOException {
        Document dom = Jsoup.parse(new File(path), "utf-8");
        dom.outputSettings().prettyPrint(false);
        dom.outputSettings().escapeMode(org.jsoup.nodes.Entities.EscapeMode.xhtml);
        dom.outputSettings().charset("UTF-8");

        List<PageToPublish> publishPages = new LinkedList<>();
        dom.select("div.sect1").forEach(sect1 -> {
            String pageTitle = sect1.select("h2").text();
            Elements pageBody = sect1.select("div.sectionbody");
            pageBody.select("div.sect2").unwrap();
            publishPages.add(new PageToPublish(baseName + pageTitle, preparePage(pageBody)));
        });
        return publishPages;
    }

    private String parseBody(Elements body) {
        body.select("div.paragraph").unwrap();
        body.select("div.ulist").unwrap();
        body.select("div.sect3").unwrap();
        body.select("colgroup").remove();

        Map.of("note", "info",
                "warning", "warning",
                "important", "warning",
                "caution", "note",
                "tip", "tip"
        ).entrySet().forEach(e -> body.select(".admonitionblock." + e.getKey()).forEach(block ->
                parseAdmonitionBlock(block, e.getValue())
        ));

        body.select("div.arc42help").forEach(div -> {
            String divBody = div.select(".content").html();
            div.after(
                    "<ac:structured-macro ac:name=\"expand\">" +
                            "<ac:rich-text-body><ac:structured-macro ac:name=\"info\">" +
                            "<ac:parameter ac:name=\"title\">arc42</ac:parameter>" +
                            "<ac:rich-text-body><p>" +
                            divBody +
                            "</p></ac:rich-text-body></ac:structured-macro>" +
                            "</ac:rich-text-body></ac:structured-macro>"
            );
            div.remove();
        });

        body.select("div.title").wrap("<strong></strong>").unwrap();
        body.select("div.listingblock").wrap("<p></p>").unwrap();

        return body.toString().trim()
                .replaceAll("<pre class=\".+\"><code( class=\".+\")?( data-lang=\".+\")?>", "<ac:structured-macro ac:name=\"code\"><ac:plain-text-body><![CDATA[")
                .replaceAll("</code></pre>", "]]></ac:plain-text-body></ac:structured-macro>")
                .replaceAll("<dl>", "<table>")
                .replaceAll("</dl>", "</table>")
                .replaceAll("<dt[^>]*>", "<tr><th>")
                .replaceAll("</dt>", "</th>")
                .replaceAll("<dd>", "<td>")
                .replaceAll("</dd>", "</td></tr>")
                .replaceAll("<img(.*?)>","<img $1/>");


    }

    private void parseAdmonitionBlock(Element block, String type) {
        Element content = block.select(".content").first();
        Elements titleElement = content.select(".title");
        String titleText = "";
        if (titleElement != null) {
            titleText = "<ac:parameter ac:name=\"title\">" + titleElement.text() + "</ac:parameter>";
            titleElement.remove();
        }
        block.after(
                "<ac:structured-macro ac:name=\"" + type + "\">"
                        + titleText
                        + "<ac:rich-text-body>" + content.toString() + "</ac:rich-text-body></ac:structured-macro>"
        );
        block.remove();
    }


    private String preparePage(Elements page) {
        String localPage = parseBody(page);
        String localHash = hash(localPage);

        return "<p><ac:structured-macro ac:name=\"toc\"/></p>" + localPage
                + "<p><ac:structured-macro ac:name=\"children\"/></p>"
                + "<p style=\"display:none\">hash: #" + localHash + "#</p>";
    }

    private String hash(String localPage) {
        try {
            return md5(localPage);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("hash fail");
        }
    }


}
