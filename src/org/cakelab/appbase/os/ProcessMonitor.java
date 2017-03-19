package org.cakelab.appbase.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.cakelab.appbase.log.Log;

public class ProcessMonitor {

	/** Silent implementation of an output reader, 
	 * which does not echo on console. 
	 * 
	 * Method {@link #toString()} returns the entire 
	 * output received on the given stream.
	 * 
	 * This class can be overridden to receive single 
	 * lines of output via {@link #append(String)}.
	 * 
	 * @author homac
	 *
	 */
	public static class OutputReader implements Runnable {

		private InputStream stream;
		private Throwable exception;
		private StringBuilder out;

		
		public void read(InputStream stream) {
			this.stream = stream;
			this.out = new StringBuilder();
			new Thread(this, this.getClass().getCanonicalName()).start();
		}
		
		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(stream));
				String line;
				while (null != (line = in.readLine())) {
					append(line);
				}
			} catch (Throwable ex) {
				exception(ex);
			}
		}
		
		protected void exception(Throwable ex) {
			exception = ex;
		}

		/**
		 * called each time a new line was read 
		 * from the stream.
		 * @param line content of the read line without '\n' 
		 * or similar terminating characters.
		 */
		protected void append(String line) {
			out.append(line);
		}

		protected Throwable getException() {
			return exception;
		}
		
		public String toString() {
			return out.toString();
		}
	}

	public static class StdOutReader extends OutputReader {

		@Override
		protected void exception(Throwable ex) {
			super.exception(ex);
			Log.error("internal exception while reading stdout");
		}

		@Override
		protected void append(String line) {
			super.append(line);
			Log.info(line);
		}
	}

	public static class StdErrReader extends OutputReader {

		@Override
		protected void exception(Throwable ex) {
			super.exception(ex);
			Log.error("internal exception while reading stderr");
		}

		@Override
		protected void append(String line) {
			super.append(line);
			Log.error(line);
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

	/**
	 * attaches output readers to a process which log to stderr and strout.
	 * @param p
	 */
	public void attach(Process p) {
		if (stdout == null) stdout = new StdOutReader();
		if (stderr == null) stderr = new StdErrReader();
		attach(p, stdout, stderr);
	}

	public void attach(Process p, OutputReader stdout, OutputReader stderr) {
		this.stdout = stdout;
		this.stderr = stderr;
		stdout.read(p.getInputStream());
		stderr.read(p.getErrorStream());
	}
	
	/**
	 * Executes the process and waits for the result. 
	 * Afterwards the exit code is compared to the 
	 * expectedResult and turned into an exception if 
	 * it was different.
	 * 
 	 * @param expectedResult
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws Error thrown if exit code is not equal to expectedResult
	 */
	public void execute(int expectedResult) throws IOException, InterruptedException, Error {
		Process p = pb.start();
		attach(p);
		result = p.waitFor();
		success = (result == expectedResult);
		if (!success) {
			throw new Error("process' " + pb.command().get(0) + " exit code: " + result + "\n" + stderr.toString());
		}
	}

	public void execute() throws IOException, Error {
		Process p = pb.start();
		attach(p);
	}

	public void setStandardOutputReader(OutputReader reader) {
		this.stdout = reader;
	}
	
	public void setStandardErrorReader(OutputReader reader) {
		this.stderr = reader;
	}
	
	public String getStdout() {
		return stdout.toString();
	}
	
	public String getStderr() {
		return stderr.toString();
	}
	
	
}
