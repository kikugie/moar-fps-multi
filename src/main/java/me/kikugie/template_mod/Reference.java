package me.kikugie.template_mod;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;

public class Reference {
    public static final String MOD_ID = "template_mod";
    public static String MOD_VERSION = "unknown";
    public static String MOD_NAME = "unknown";

    public static void init() {
        ModMetadata metadata = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata();
        MOD_NAME = metadata.getName();
        MOD_VERSION = metadata.getVersion().getFriendlyString();
    }
}
