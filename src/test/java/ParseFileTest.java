import com.github.minecraft_ta.reciper.registry.RecipeRegistry;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ParseFileTest {

    @Test
    public void testFileParsing() {
        // Print current working directory
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        File lookupFile = new File("src/test/resources/recipe-export.bin");
        RecipeRegistry.loadRecipesFromFile(lookupFile);

        RecipeRegistry.RECIPES.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });


    }


}
