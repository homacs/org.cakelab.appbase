package org.cakelab.appbase.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.cakelab.json.codec.JSONCodec;
import org.cakelab.json.codec.JSONCodecConfiguration;
import org.cakelab.json.codec.JSONCodecException;

public class JsonConfigFile {

	protected transient File file;
	protected transient boolean modified;

	protected transient JSONCodecConfiguration jsonCodecConfig;
	
	private static final JSONCodecConfiguration DEFAULT_JSON_CODEC_CONFIG;
	static {
		DEFAULT_JSON_CODEC_CONFIG = new JSONCodecConfiguration();
		DEFAULT_JSON_CODEC_CONFIG.ignoreNull = true;
		DEFAULT_JSON_CODEC_CONFIG.ignoreMissingFields = true;
	}
	
	
	public JsonConfigFile(JSONCodecConfiguration codecConfig, File file) throws JSONCodecException, IOException {
		this.file = file;
		this.modified = true;
		this.jsonCodecConfig = codecConfig;
		if (this.file != null && this.file.exists()) {
			load();
		}
	}

	public JsonConfigFile(JSONCodecConfiguration codecConfig) {
		this.file = null;
		this.modified = true;
		this.jsonCodecConfig = codecConfig;
	}

	public JsonConfigFile(File file) throws JSONCodecException, IOException {
		this(DEFAULT_JSON_CODEC_CONFIG, file);
	}

	public JsonConfigFile() {
		this.file = null;
		this.modified = true;
		this.jsonCodecConfig = DEFAULT_JSON_CODEC_CONFIG;
	}

	public boolean exists() {
		return (file != null) && file.exists();
	}
	
	public void load() throws JSONCodecException, IOException {
		if (file == null) throw new IOException("no file attached");
		JSONCodec codec = new JSONCodec(jsonCodecConfig);
		InputStream in = new FileInputStream(this.file);
		codec.decodeObject(in, this);
		in.close();
		modified = false;
	}
	
	public void save() throws JSONCodecException, IOException {
		if (file == null) throw new IOException("no file attached");
		JSONCodec codec = new JSONCodec(jsonCodecConfig);
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
