package com.htmlpdf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.log.Level;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.NoCustomContextException;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.Tag;
import com.itextpdf.tool.xml.WorkerContext;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.exceptions.LocaleMessages;
import com.itextpdf.tool.xml.exceptions.RuntimeWorkerException;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.HTML;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.scb.sherlock.admin.dum.Html;
public class ConvertHtmlToPdf {

	public static void main(String[] args) {
	    convertHtmlToPdf();

	}

	private static void convertHtmlToPdf() {
	    try {
	        final Document document = new Document();
	        final PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/orange/Que/pdf/file.pdf"));
	        document.open();
	        final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
	        tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
	        tagProcessorFactory.addProcessor(new ImageTagProcessor(), HTML.Tag.IMG);

	        final CssFilesImpl cssFiles = new CssFilesImpl();
	        cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
	        final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
	        final HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(new XMLWorkerFontProvider()));
	        hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
	        final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
	        final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
	        final XMLWorker worker = new XMLWorker(pipeline, true);
	        final Charset charset = Charset.forName("UTF-8");
	        final XMLParser xmlParser = new XMLParser(true, worker, charset);
	        //final InputStream is = new FileInputStream("/home/orange/Que/pdf/raw2.html");
	        final InputStream is = new FileInputStream(Html.setTemplateThaiEconomicsTrends());
	        xmlParser.parse(is, charset);

	        is.close();
	        document.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        // TODO
	    }
	}
}
