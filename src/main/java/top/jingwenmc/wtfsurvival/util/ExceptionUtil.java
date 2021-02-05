package top.jingwenmc.wtfsurvival.util;

import top.jingwenmc.wtfsurvival.WTFSurvival;

import java.io.*;
import java.util.Date;
import java.util.logging.Level;

public class ExceptionUtil {
    public static void print(Throwable e) {
        File eFile = new File(WTFSurvival.getInstance().getDataFolder(), "errors.txt");
        try (FileWriter out = new FileWriter(eFile,true);PrintWriter writer = new PrintWriter(out)) {
            if (!eFile.exists()) if(!eFile.createNewFile()) {
                throw new IllegalStateException("Cannot Create File");
            }
            if (!eFile.canWrite()) throw new IOException("errors.txt is not writable");
            writer.println(("[Error] " + new Date(System.currentTimeMillis())));
            writer.println("Error Details:");
            handle(e,writer);
            writer.println();
            WTFSurvival.getInstance().getLogger().log(Level.SEVERE, "一个错误已经发生,已将该错误信息保存到插件目录下的 errors.txt");
            WTFSurvival.getInstance().getLogger().log(Level.SEVERE, "An error has occurred and the error message has been saved to errors.txt in the plugin directory");
        } catch (IOException | IllegalStateException exception) {
            System.err.println("UNEXPECTED ERROR:Cannot Create/Write errors.txt While Recording An Error");
            System.err.println("IF ITS ALL RIGHT, PLEASE REPORT TO DEV.");
            handle(exception);
        }
    }
    public static void handle(Throwable exception) {
        handle(exception,System.err);
    }

    public static void handle(Throwable exception, PrintStream stream) {
        if(exception == null)return;
        stream.println("Caused By:");
        stream.println("Type:"+exception.getClass().getName());
        stream.println("Message:"+exception.getMessage());
        stream.println("Stacktrace:");
        for(StackTraceElement element : exception.getStackTrace()) {
            stream.println("  Class:"+element.getClassName()+", Method:"+element.getMethodName()
                    +", At Line:"+element.getLineNumber());
        }
        stream.println("Have a deeper cause: "+(exception.getCause() != null ? "YES" : "NO"));
        handle(exception.getCause());
    }
    public static void handle(Throwable exception, PrintWriter writer) {
        if(exception == null)return;
        writer.println("Caused By:");
        writer.println("Type:"+exception.getClass().getName());
        writer.println("Message:"+exception.getMessage());
        writer.println("Stacktrace:");
        for(StackTraceElement element : exception.getStackTrace()) {
            writer.println("  Class:"+element.getClassName()+", Method:"+element.getMethodName()
                    +", At Line:"+element.getLineNumber());
        }
        writer.println("Have a deeper cause: "+(exception.getCause() != null ? "YES" : "NO"));
        handle(exception.getCause());
    }
}
