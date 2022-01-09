package clustererService;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class InitLogger {
    public static Logger initLogger() {
        Logger logger;
        try {
            logger = Logger.getLogger("worker");
            var fh = new FileHandler("/logs/workers.txt");
            var simpleFr = new SimpleFormatter();
            fh.setFormatter(simpleFr);
            logger.addHandler(fh);
        } catch (IOException e) {
            return Logger.getLogger("worker");
        }

        return logger;
    }
}
