package org.cakelab.appbase.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.cakelab.appbase.log.Log;

public class ProcessMonitor {

	public class OutputReader extends Thread {

		private InputStream stream;
		private Throwable exception;
		private boolean stderr;
		private StringBuilder out;

		public OutputReader(InputStream stream, boolean stderr) {
			this.stream = stream;
			this.stderr = stderr;
		}

		@Override
		public void run() {
			out = new StringBuilder();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(stream));
				String line;
				while (null != (line = in.readLine())) {
					out.append(line);
					if (stderr) Log.error(line);
					else Log.info(line);
				}
			} catch (Throwable ex) {
				exception = ex;
				Log.error("output reader", ex);
			}
		}
		Throwable getException() {
			return exception;
		}
		
		public String toString() {
			return out.toString();
		}
	}

	private OutputReader stdout;
	private OutputReader stderr;
	private ProcessBuilder pb;
	private int result;
	private boolean success;

	public ProcessMonitor(ProcessBuilder pb) {
		this.pb = pb;
	}

	public void attach(Process p) {
		stdout = new OutputReader(p.getInputStream(), false);
		stderr = new OutputReader(p.getErrorStream(), true);
		
		stdout.start();
		stderr.start();
	}

	public void execute(int expectedResult) throws IOException, InterruptedException {
		Process p = pb.start();
		attach(p);
		result = p.waitFor();
		success = (result == expectedResult);
		if (!success) {
			throw new IOException("process' " + pb.command().get(0) + " exit code: " + result + "\n" + stderr.toString());
		}
	}

	public void execute() throws IOException {
		Process p = pb.start();
		attach(p);
	}

}
