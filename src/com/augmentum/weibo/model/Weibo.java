package com.augmentum.weibo.model;

import java.io.Serializable;
/**
 * This model is weibo html
 * @author Bennett.Zhang
 *
 */
public class Weibo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pid;
	private String[] js;
	private String[] css;
	private String html;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String[] getJs() {
		return js;
	}

	public void setJs(String[] js) {
		this.js = js;
	}

	public String[] getCss() {
		return css;
	}

	public void setCss(String[] css) {
		this.css = css;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
