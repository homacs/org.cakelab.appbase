package org.cakelab.appbase.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class FileLog extends StreamLogBase implements LogListener {

	private File logfile;
	private PrintStream out;
	private boolean sync;

	public FileLog(File logfile, boolean sync) throws IOException {
		this.logfile = logfile;
		this.sync = sync;
		
		if (!logfile.exists()) {
			logfile.createNewFile();
		}
		
		open();
	}

	public void close() {
		out.close();
	}

	private void open() throws FileNotFoundException {
		out = new PrintStream(new FileOutputStream(logfile));
	}

	@Override
	public void fatal(String msg, Throwable e) {
		fatal(out, msg, e);
		if (sync) out.flush();
	}

	@Override
	public void fatal(String msg) {
		fatal(out, msg);
		if (sync) out.flush();
	}

	@Override
	public void error(String msg, Throwable e) {
		error(out, msg, e);
		if (sync) out.flush();
	}

	@Override
	public void error(String msg) {
		error(out, msg);
		out.flush();
	}

	@Override
	public void warn(String msg, Throwable e) {
		warn(out, msg, e);
		if (sync) out.flush();
	}

	@Override
	public void warn(String msg) {
		warn(out, msg);
		if (sync) out.flush();
	}

	@Override
	public void info(String msg) {
		info(out, msg);
		if (sync) out.flush();
	}

}
