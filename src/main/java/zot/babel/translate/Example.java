package zot.babel.translate;

import zot.babel.Babel;
import com.deepl.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example {
    public static final Logger LOGGER = LoggerFactory.getLogger(Babel.MOD_ID);
    Translator translator;

    public Example() throws Exception {
        String authKey = System.getProperty("DEEPL_API_KEY"); // .env file must be in src/main/resources directory
        translator = new Translator(authKey);
        TextResult result = translator.translateText("Hello, world!", null, "fr");
        LOGGER.info(result.getText()); // "Bonjour, le monde !"
    }
}