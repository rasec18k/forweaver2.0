package com.forweaver.domain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.apache.commons.codec.binary.Base64;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

/**<pre> 자료를 보관하는 클래스
 * id 실제 아이디
 * weaver 올린 사람
 * content 자료 내용
 * name 자료 이름
 * type 자료형
 * date 올린 날짜
 * </pre>
 */
@Document
public class Data implements Serializable {
	
	static final long serialVersionUID = 3423431L;
	@Id
	private String id;
	@DBRef
	private Weaver weaver;
	@Transient
	private byte[] content;
	private String name;
	private String type;
	private Date date;
	private String filePath;
	
	//임시로 기본 파일 패스를 정함
	@Transient
	public static String path = "/home/file/";
	
	public Data(){
		
	}
	
	public Data(String id ,MultipartFile data,Weaver weaver){
		this.id = id;
		this.date = new Date();
		this.weaver = weaver;
		this.name= "";
		try{
			this.content= data.getBytes();
		}catch(IOException e){
			this.content= null;
		}		
		this.name = data.getOriginalFilename();
		this.name = this.name.replace(" ", "_");
		this.name = this.name.replace("#", "_");
		this.name = this.name.replace("?", "_");	
		this.name = this.name.trim();
		this.type = data.getContentType();
		this.filePath = path+weaver.getId()+File.separator+this.id+File.separator+this.name;		
	}	
	
	public String getId() {
		return id.toString();
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getOutPutStream(){
		return new Base64().encodeToString(this.content);
	}

	

	public Weaver getWeaver() {
		return weaver;
	}
    public void setWeaver(Weaver weaver) {
		this.weaver = weaver;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
