package me.david.spacezero.logger;

import lombok.Getter;
import me.david.spacezero.util.StringUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class ZeroLogger {

    @Getter private Logger logger;
    @Getter private Level level;

    public ZeroLogger(final Level level) {
        this.level = level;
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        this.logger = root;
        FileHandler txt;
        try {
            String filename = new SimpleDateFormat("dd MM yyyy").format(new Date()) + "-1";
            File logDir = new File("Logs/");
            if(!logDir.exists()) {
                logDir.mkdirs();
            }
            while(new File(logDir, filename + ".log").exists()) {
                int next = Integer.valueOf(filename.split("-")[1]);
                next++;
                filename = filename.substring(0, filename.split("-")[0].length()) + "-" + next;
            }
            txt = new FileHandler(new File(logDir, filename + ".log").getAbsolutePath());
            root.setLevel(level);
            txt.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String ret = "";
                    ret += "[" + record.getLevel().getName() + "] ";
                    ret += new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(record.getMillis());
                    ret += ": " + record.getMessage();
                    ret += "\r\n";
                    return ret;
                }
            });
            root.addHandler(txt);
        } catch (Exception e) {
            exception(e, "Failed to create Logger");
        }
    }

    public ZeroLogger info(String info) {
        logger.info(info);
        System.out.println(info);
        return this;
    }

    public ZeroLogger warn(String warning){
        logger.warning(warning);
        System.out.println("WARN: " + warning);
        return this;
    }

    public ZeroLogger printBanner(){
        info("  _____ ____   ____    __    ___  _____    ___  ____   ___  ");
        info(" / ___/|    \\ /    |  /  ]  /  _]|     |  /  _]|    \\ /   \\ ");
        info("(   \\_ |  o  )  o  | /  /  /  [_ |__/  | /  [_ |  D  )     |");
        info(" \\__  ||   _/|     |/  /  |    _]|   __||    _]|    /|  O  |");
        info(" /  \\ ||  |  |  _  /   \\_ |   [_ |  /  ||   [_ |    \\|     |");
        info(" \\    ||  |  |  |  \\     ||     ||     ||     ||  .  \\     |");
        info("  \\___||__|  |__|__|\\____||_____||_____||_____||__|\\_|\\___/");

        return this;
    }

    public ZeroLogger infoConsole(String info, boolean in) {
        logger.info("Console " + (in ? "<-" : "->") + " " + info);
        System.out.println(info);
        return this;
    }


    //TODO debug mode?
    public ZeroLogger debug(String debug){
        info(debug);
        return this;
    }

    //TODO debug mode?
    public ZeroLogger debugWarn(String debug){
        warn(debug);
        return this;
    }

    public ZeroLogger exception(Throwable throwable, String message){
        debugWarn("Exception Was Thrown: " + message);
        for(String line : StringUtil.fromException(throwable).split(System.lineSeparator()))
            debugWarn(line);
        return this;
    }

    public ZeroLogger infoLog(String message) {
        logger.info(message);
        return this;
    }

}
