/*
 * Copyright 2015-2016 the original author or authors.
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
package io.koara.xml;

import io.koara.ast.BlockElement;
import io.koara.ast.BlockQuote;
import io.koara.ast.Code;
import io.koara.ast.CodeBlock;
import io.koara.ast.Document;
import io.koara.ast.Em;
import io.koara.ast.Heading;
import io.koara.ast.Image;
import io.koara.ast.LineBreak;
import io.koara.ast.Link;
import io.koara.ast.ListBlock;
import io.koara.ast.ListItem;
import io.koara.ast.Paragraph;
import io.koara.ast.Strong;
import io.koara.ast.Text;
import io.koara.Renderer;

public class XmlRenderer implements Renderer {

	private StringBuffer out;
	private int level;
    private boolean hardWrap;
	private String declarationTag;
	
	@Override
	public void visit(Document node) {
		out = new StringBuffer();
		if(declarationTag != null) {
		    out.append(declarationTag + "\n");
		}
		if(node.getChildren() != null && node.getChildren().length > 0) {
			out.append("<document>\n");
			node.childrenAccept(this);
			out.append("</document>");
		} else {
			out.append("<document />");
		}
	}

	@Override
	public void visit(Heading node) {
		level++;
		indent();
		out.append("<heading level=\"" + node.getValue() + "\"");
		if(node.getChildren() != null && node.getChildren().length > 0) {
			out.append(">\n");
			level++;
			node.childrenAccept(this);
			level--;
			indent();
			out.append("</heading>\n");
		} else {
			out.append(" />\n");
		}
		level--;
	}

	@Override
	public void visit(BlockQuote node) {
		level++;
		indent();
		out.append("<blockquote");
		if(node.getChildren() != null && node.getChildren().length > 0) {
			out.append(">\n");
			level++;
			node.childrenAccept(this);
			level--;
			indent();
			out.append("</blockquote>\n");
			level--;
		} else {
			out.append(" />\n");
		}
	}

	@Override
	public void visit(ListBlock node) {
		level++;
		indent();
		out.append("<list ordered=\"" + node.isOrdered() + "\">\n");
		node.childrenAccept(this);
		indent();
		out.append("</list>\n");
		level--;
	}

	@Override
	public void visit(ListItem node) {
		level++;
		indent();
		out.append("<listitem");
		if(node.getNumber() != null) {
			out.append(" number=\"" + node.getNumber() + "\"");
		}
		if(node.getChildren() != null && node.getChildren().length > 0) {
			out.append(">\n");
			node.childrenAccept(this);
			indent();
			out.append("</listitem>\n");
		} else {
			out.append(" />\n");
		}
		level--;
	}

	@Override
	public void visit(CodeBlock node) {
		level++;
		indent();
		out.append("<codeblock");
		if(node.getLanguage() != null) {
			out.append(" language=\"" + escape(node.getLanguage()) + "\"");
		}
		if(node.getValue() != null && node.getValue().toString().length() > 0) {
			out.append(">");
			level++;
			out.append(escape(node.getValue().toString()));
			level--;
			out.append("</codeblock>\n");
		} else {
			out.append(" />\n");
		}
		level--;
	}

	@Override
	public void visit(Paragraph node) {
		level++;
		indent();
		out.append("<paragraph>\n");
		level++;
		node.childrenAccept(this);
		level--;
		indent();
		out.append("</paragraph>\n");
		level--;
	}

	@Override
	public void visit(BlockElement node) {
	}

	@Override
	public void visit(Image node) {
		indent();
		out.append("<image url=\"" + escapeUrl(node.getValue().toString()) + "\">\n");
		level++;
		node.childrenAccept(this);
		level--;
		indent();
		out.append("</image>\n");
	}

	@Override
	public void visit(Link node) {
		indent();
		out.append("<link url=\"" + escapeUrl(node.getValue().toString()) + "\">\n");
		level++;
		node.childrenAccept(this);
		level--;
		indent();
		out.append("</link>\n");
	}

	@Override
	public void visit(Text node) {
		indent();
		out.append("<text>");
		out.append(escape(node.getValue().toString()));
		out.append("</text>\n");
	}

	@Override
	public void visit(Strong node) {
		indent();
		out.append("<strong>\n");
		level++;
		node.childrenAccept(this);
		level--;
		indent();
		out.append("</strong>\n");
	}

	@Override
	public void visit(Em node) {
		indent();
		out.append("<em>\n");
		level++;
		node.childrenAccept(this);
		level--;
		indent();
		out.append("</em>\n");
	}

	@Override
	public void visit(Code node) {
		indent();
		out.append("<code>\n");
		level++;
		node.childrenAccept(this);
		level--;
		indent();
		out.append("</code>\n");
	}

	@Override
	public void visit(LineBreak node) {
		boolean hard = hardWrap || node.isExplicit();
		indent();
		out.append("<linebreak explicit=\"" + hard + "\"/>\n");
	}

	public String escapeUrl(String text) {
		return text.replaceAll(" ", "%20")
				.replaceAll("\"", "%22")
				.replaceAll("`", "%60")
				.replaceAll("<", "%3C")
				.replaceAll(">", "%3E")
				.replaceAll("\\[", "%5B")
				.replaceAll("\\]", "%5D")
				.replaceAll("\\\\", "%5C");
	}
	
	public void indent() {
		int repeat = level * 2;
		for (int i = repeat - 1; i >= 0; i--) {
		 out.append(" ");
		} 
	}
	
	public String escape(String text) {
		return text.replaceAll("&", "&amp;")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll("\"", "&quot;");
	}
	
	public String getOutput() {
		return out.toString();
	}

	public void setHardWrap(boolean hardWrap) {
		this.hardWrap = hardWrap;
	}

	public void setDeclarationTag(String declarationTag) {
		this.declarationTag = declarationTag;
	}
	
}