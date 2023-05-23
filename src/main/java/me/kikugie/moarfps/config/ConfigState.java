package me.kikugie.moarfps.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.kikugie.moarfps.MoarFPS;
import me.kikugie.moarfps.Reference;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

public class ConfigState {
    public static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(String.format("%s.json", Reference.MOD_ID)).toFile();
    public static final Codec<ConfigState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("fakeFps").forGetter(state -> state.fakeFps),
            Codec.INT.fieldOf("fluctuateAmount").forGetter(state -> state.fluctuateAmount),
            Codec.BOOL.fieldOf("enabled").forGetter(state -> state.enabled),
            Codec.BOOL.fieldOf("fluctuate").forGetter(state -> state.fluctuate)
    ).apply(instance, ConfigState::new));
    public int fakeFps;
    public int fluctuateAmount;
    public boolean enabled;
    public boolean fluctuate;
    private ConfigState(int fakeFps, int fluctuateAmount, boolean enabled, boolean fluctuate) {
        this.fakeFps = fakeFps;
        this.fluctuateAmount = fluctuateAmount;
        this.enabled = enabled;
        this.fluctuate = fluctuate;
    }

    public static ConfigState load() {
        if (CONFIG_FILE.exists()) {
            try {
                String jsonString = FileUtils.readFileToString(CONFIG_FILE, StandardCharsets.UTF_8);
                //#if MC > 11900
                JsonElement json = JsonParser.parseString(jsonString);
                //#else
                //$$ JsonElement json = new JsonParser().parse(jsonString);
                //#endif
                return CODEC.parse(JsonOps.INSTANCE, json)
                        .resultOrPartial(s -> MoarFPS.LOGGER.error("Error reading config data!\n{}", s))
                        .orElseThrow(() -> new NoSuchElementException("No value present"));
            } catch (IOException e) {
                MoarFPS.LOGGER.error("Error reading config file!\n", e);
            } catch (NoSuchElementException ignored) {
            }
        }

        ConfigState state = new ConfigState(1000, 50, true, true);
        try {
            CONFIG_FILE.createNewFile();
            state.save();
        } catch (IOException e) {
            MoarFPS.LOGGER.error("Couldn't create config file!\n", e);
        }
        return state;
    }

    public void save() {
        try {
            DataResult<JsonElement> result = CODEC.encodeStart(JsonOps.INSTANCE, this);
            String jsonString = result.resultOrPartial(s -> MoarFPS.LOGGER.error("Error saving config data! How odd...\n{}", s)).orElseThrow(() -> new NoSuchElementException("No value present")).toString();
            FileUtils.write(CONFIG_FILE, jsonString, StandardCharsets.UTF_8);
        } catch (IOException e) {
            MoarFPS.LOGGER.error("Error writing config file!\n", e);
        } catch (NoSuchElementException ignored) {
        }
    }

    public void reset() {
        fakeFps = 1000;
        fluctuateAmount = 50;
        enabled = true;
        fluctuate = true;
    }
}
