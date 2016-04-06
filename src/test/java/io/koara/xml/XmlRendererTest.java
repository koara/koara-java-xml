package io.koara.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import io.koara.Parser;
import io.koara.ast.Document;
import io.koara.xml.XmlRenderer;

public class XmlRendererTest {

	private XmlRenderer renderer;
	private Document document;
	
	@Before
	public void setUp() {
		Parser parser = new Parser();
		document = parser.parse("Test");
		renderer = new XmlRenderer();
	}
	
	@Test
	public void testBasic() {
		StringBuilder expected = new StringBuilder();
		expected.append("<document>\n");
		expected.append("  <paragraph>\n");
		expected.append("    <text>Test</text>\n");
		expected.append("  </paragraph>\n");
		expected.append("</document>");
		
		document.accept(renderer);
		assertEquals(expected.toString(), renderer.getOutput());
	}
	
	@Test
	public void testAddDeclarationTag() {
		StringBuilder expected = new StringBuilder();
		expected.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		expected.append("<document>\n");
		expected.append("  <paragraph>\n");
		expected.append("    <text>Test</text>\n");
		expected.append("  </paragraph>\n");
		expected.append("</document>");
		
		renderer.setDeclarationTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		document.accept(renderer);
		assertEquals(expected.toString(), renderer.getOutput());
	}
	
	
}
