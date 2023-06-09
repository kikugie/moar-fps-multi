package me.kikugie.moarfps.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3"))
            return Yacl3Config::createGui;
        if (FabricLoader.getInstance().isModLoaded("yet-another-config-lib"))
            return YaclConfig::createGui;
        if (FabricLoader.getInstance().isModLoaded("cloth-config") || FabricLoader.getInstance().isModLoaded("cloth-config2"))
            return ClothConfig::createGui;
        return null;
    }
}