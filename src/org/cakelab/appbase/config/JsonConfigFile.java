package org.cakelab.appbase.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.cakelab.json.codec.JSONCodec;
import org.cakelab.json.codec.JSONCodecException;

public class JsonConfigFile {

	protected transient File file;
	protected transient boolean modified;

	public JsonConfigFile(File file) throws JSONCodecException, IOException {
		this.file = file;
		this.modified = true;
		if (this.file.exists()) {
			load();
		}
	}

	public JsonConfigFile() {
		this.file = null;
		this.modified = true;
	}

	public boolean exists() {
		return (file != null) && file.exists();
	}
	
	public void load() throws JSONCodecException, IOException {
		if (file == null) throw new IOException("no file attached");
		JSONCodec codec = new JSONCodec(true, true);
		InputStream in = new FileInputStream(this.file);
		codec.decodeObject(in, this);
		in.close();
		modified = false;
	}
	
	public void save() throws JSONCodecException, IOException {
		if (file == null) throw new IOException("no file attached");
		JSONCodec codec = new JSONCodec(true, true);
		FileOutputStream out = new FileOutputStream(this.file);
		codec.encodeObject(this, out);
		out.close();
		modified = false;
	}

	public void setFile(File newFile) {
		if (file != null && !file.equals(newFile) 
				|| newFile == null /* && file != null */) 
		{
			modified = true;
		}
		file = newFile;
	}
	
	public boolean isModified() {
		return modified;
	}
	
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	protected boolean modifying(Object oldValue, Object newValue) {
		if (oldValue != null && !oldValue.equals(newValue) || newValue != null) {
			modified = true;
			return true;
		} else {
			return false;
		}
	}

	protected boolean modifying(long oldValue, long newValue) {
		if (oldValue != newValue) {
			modified = true;
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean modifying(double oldValue, double newValue) {
		if (oldValue != newValue) {
			modified = true;
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean modifying(boolean oldValue, boolean newValue) {
		if (oldValue != newValue) {
			modified = true;
			return true;
		} else {
			return false;
		}
	}
}
