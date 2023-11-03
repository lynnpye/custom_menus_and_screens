package com.pyehouse.custom_menus_and_screens.common;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.pyehouse.custom_menus_and_screens.common.screendef.ScreenDef;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.pyehouse.custom_menus_and_screens.ModMain.LOGGER;
import static com.pyehouse.custom_menus_and_screens.ModMain.MODID;

public class Config {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;
    public static final Map<String, ScreenDef> screens = new HashMap<>();

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }

    public static void initConfig() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        Config.loadConfig(Config.COMMON_SPEC, FMLPaths.CONFIGDIR.get().resolve(String.format("%s-common.toml", MODID)).toString());

        Config.loadScreens();
    }

    public static class CommonConfig {
        public CommonConfig(ForgeConfigSpec.Builder builder) {

        }
    }

    public static ScreenDef getScreenDef(String screenDefId) {
        if (screenDefId == null) {
            return null;
        }
        return Config.screens.get(screenDefId);
    }

    private static void loadConfig(ForgeConfigSpec configSpec, String path) {
        final CommentedFileConfig file = CommentedFileConfig
                .builder(new File(path))
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        file.load();
        configSpec.setConfig(file);
    }

    private static void loadScreens() {
        File screenFolder = new File(FMLPaths.CONFIGDIR.get().toFile(), MODID);
        if (!screenFolder.exists()) {
            if (!screenFolder.mkdir()) {
                LOGGER.error(String.format("Unable to create screen folder path [%s].", screenFolder.getAbsolutePath()));
                return;
            }
        }
        File[] screenFiles = screenFolder.listFiles((file, s) -> s.endsWith(".json"));
        if (screenFiles == null || screenFiles.length == 0) {
            // no screen definitions found
            return;
        }
        for (File screenFile : screenFiles) {
            String filename = screenFile.getName().substring(0, screenFile.getName().indexOf(".json"));

            if (filename.isEmpty()) {
                // invalid name
                continue;
            }
            try {
                ScreenDef screenDef = ScreenDef.fromJson(Files.readString(Paths.get(screenFile.toURI())));
                if (screenDef.id == null || screenDef.id.isEmpty()) {
                    screenDef.id = filename;
                }
                Config.screens.put(screenDef.id, screenDef);
            } catch (IOException e) {
                LOGGER.error(String.format("Exception caught while reading screenDef from file [%s]: %s", screenFile.getAbsolutePath(), e));
            }
        }
    }
}
