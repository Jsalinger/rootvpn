package com.donn.rootvpn;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class ShellCommand {
    private static final String TAG = "ShellCommand.java";
    private Boolean can_su;    

    public SH sh;
    public SH su;
    
    public ShellCommand() {
        sh = new SH("sh");
        su = new SH("su");
    }
    
    public boolean canSU() {
        return canSU(false);
    }
    
    public boolean canSU(boolean force_check) {
        if (can_su == null || force_check) {
            CommandResult r = su.runWaitFor("id");
            StringBuilder out = new StringBuilder();
            
            if (r.stdout != null)
                out.append(r.stdout).append(" ; ");
            if (r.stderr != null)
                out.append(r.stderr);
            
            Log.v(TAG, "canSU() su[" + r.exit_value + "]: " + out);
            can_su = r.success();
        }
        return can_su;
    }

    public SH suOrSH() {
        return canSU() ? su : sh;
    }
    
    public class CommandResult {
        public final String stdout;
        public final String stderr;
        public final Integer exit_value;
        
        CommandResult(Integer exit_value_in, String stdout_in, String stderr_in)
        {
            exit_value = exit_value_in;
            stdout = stdout_in;
            stderr = stderr_in;
        }
        
        CommandResult(Integer exit_value_in) {
            this(exit_value_in, null, null);
        }
        
        public boolean success() {
            return exit_value != null && exit_value == 0;
        }
    }

    public class SH {
        private String SHELL = "sh";

        public SH(String SHELL_in) {
            SHELL = SHELL_in;
        }

        public Process run(String s) {
            Process process = null;
            try {
            	L.log(this, "Running command: " + s);
                process = Runtime.getRuntime().exec(SHELL);
                DataOutputStream toProcess = new DataOutputStream(process.getOutputStream());
                toProcess.writeBytes("exec " + s + "\n");
                toProcess.flush();
            } 
            catch(Exception e) {
                L.err(this, "Exception while trying to run: '" + s + "' " + e.getMessage());
                process = null;
            }
            return process;
        }
        
		private String getStreamLines(InputStream is) {
            String out = null;
            StringBuffer buffer = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            try {
                if (bufferedReader.ready()) { 
                    buffer = new StringBuffer(bufferedReader.readLine());
                    while(bufferedReader.ready())
                        buffer.append("\n").append(bufferedReader.readLine());
                }
                bufferedReader.close();
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
            if (buffer != null)
                out = buffer.toString();
            return out;
        }

        public CommandResult runWaitFor(String s) {
            Process process = run(s);
            Integer exit_value = null;
            String stdout = null;
            String stderr = null;
            if (process != null) {
                try {
                    exit_value = process.waitFor();

                    stdout = getStreamLines(process.getInputStream());
                	if (stdout != null) {
                		L.log(this, "Command std output: " + stdout.replaceAll("\n", " "));
                	}

                    stderr = getStreamLines(process.getErrorStream());
                	if (stderr != null) {
                		L.log(this, "Command err output: " + stderr.replaceAll("\n", " "));
                	}
                	
                } 
                catch(InterruptedException e) {
                	L.err(this, "Exception while running command: " + stderr);
                } 
                catch(NullPointerException e) {
                	L.err(this, "Null pointer while running command: " + e.toString());
                }
            }
            return new CommandResult(exit_value, stdout, stderr);
        }
    }
}
