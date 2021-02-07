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
            WTFSurvival.getInstance().getLogger().log(Level.SEVERE, "请检查你的配置文件或语言文件是否出现错误,否则请将该错误反馈给开发者.");
            WTFSurvival.getInstance().getLogger().log(Level.SEVERE, "Please check your configuration file or language file for errors, otherwise please report the error to the developer. ");
        } catch (IOException | IllegalStateException exception) {
            System.err.println("意外的错误：记录错误时无法创建/写入 errors.txt");
            System.err.println("UNEXPECTED ERROR:Cannot Create/Write errors.txt while recording an error");
            System.err.println("如果您的环境一切正常，请报告给开发人员。");
            System.err.println("If everything is NORMAL in your environment, please report to the developer.");
            handle(exception);
        } catch (NullPointerException exception) {
            System.err.println("意外的错误：NPE");
            System.err.println("UNEXPECTED ERROR:NPE");
            System.err.println("如果您的环境一切正常，请报告给开发人员。");
            System.err.println("If everything is NORMAL in your environment, please report to the developer.");
            handle(exception);
        }
    }
    public static void handle(Throwable exception) {
        handle(exception,System.err);
    }

    public static void handle(Throwable exception, PrintStream stream) {
        handle(exception, stream, false);
    }

    private static void handle(Throwable exception, PrintStream stream, boolean deep) {
        if(exception == null)return;
        if(deep)stream.println("Caused By:");
        stream.println("Type:"+exception.getClass().getName());
        if(exception.getMessage()!=null)stream.println("Message:"+exception.getMessage());
        else stream.println("No Message.");
        stream.println("Stacktrace:");
        for(StackTraceElement element : exception.getStackTrace()) {
            stream.println("  Class:["+element.getClassName()+"]\t| Method:["+element.getMethodName()
                    +"]\t| At Line:["+element.getLineNumber()+"]");
        }
        stream.println("Have a deeper cause: "+(exception.getCause() != null ? "YES" : "NO"));
        handle(exception.getCause(),stream,true);
    }

    public static void handle(Throwable exception, PrintWriter writer){
        handle(exception, writer, false);
    }

    private static void handle(Throwable exception, PrintWriter writer, boolean deep) {
        if(exception == null)return;
        if(deep)writer.println("Caused By:");
        writer.println("Type:"+exception.getClass().getName());
        if(exception.getMessage()!=null)writer.println("Message:"+exception.getMessage());
        else writer.println("No Message.");
        writer.println("Stacktrace:");
        for(StackTraceElement element : exception.getStackTrace()) {
            writer.println("  Class:["+element.getClassName()+"]\t| Method:["+element.getMethodName()
                    +"]\t| At Line:"+element.getLineNumber()+"]");
        }
        writer.println("Have a deeper cause: "+(exception.getCause() != null ? "YES" : "NO"));
        handle(exception.getCause(),writer,true);
    }
}
