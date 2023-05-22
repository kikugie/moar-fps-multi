package me.kikugie.moarfps.config;

import me.kikugie.moarfps.MoarFPS;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ClothConfig {
    public static Screen createGui(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.of("FPS Config"));
        ConfigCategory render = builder.getOrCreateCategory(Text.of("Render"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        addIntOption(entryBuilder,
                render,
                "Desired FPS",
                1000,
                () -> MoarFPS.getConfig().fakeFps,
                (fakeFps) -> MoarFPS.getConfig().fakeFps = fakeFps);
        addBooleanOption(entryBuilder,
                render,
                "Make it appear less fake?",
                true,
                () -> MoarFPS.getConfig().fluctuate,
                (fluctuate) -> MoarFPS.getConfig().fluctuate = fluctuate);
        addIntOption(entryBuilder,
                render,
                "How much less fake?",
                50,
                () -> MoarFPS.getConfig().fluctuateAmount,
                (fluctuateAmount) -> MoarFPS.getConfig().fluctuateAmount = fluctuateAmount);
        addBooleanOption(entryBuilder,
                render,
                "Get better FPS?",
                true,
                () -> MoarFPS.getConfig().enabled,
                (enabled) -> MoarFPS.getConfig().enabled = enabled);

        builder.setSavingRunnable(MoarFPS.getConfig()::save);
        return builder.build();
    }

    private static void addIntOption(ConfigEntryBuilder builder, ConfigCategory category, String name, int defaultVal, @NotNull Supplier<Integer> getter, @NotNull Consumer<Integer> setter) {
        category.addEntry(builder.startIntField(Text.of(name), getter.get())
                .setDefaultValue(defaultVal)
                .setSaveConsumer(setter)
                .build()
        );
    }

    private static void addBooleanOption(ConfigEntryBuilder builder, ConfigCategory category, String name, boolean defaultVal, @NotNull Supplier<Boolean> getter, @NotNull Consumer<Boolean> setter) {
        category.addEntry(builder.startBooleanToggle(Text.of(name), getter.get())
                .setDefaultValue(defaultVal)
                .setSaveConsumer(setter)
                .build()
        );
    }
}
