package me.kikugie.moarfps.config;

import me.kikugie.moarfps.MoarFPS;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ClothConfig {
    public static Screen createGui(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(MoarFPS.translated("moarfps.config.title"));
        ConfigCategory render = builder.getOrCreateCategory(MoarFPS.translated("moarfps.config.category"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        addIntOption(entryBuilder,
                render,
                "fakefps",
                1000,
                () -> MoarFPS.getConfig().fakeFps,
                (fakeFps) -> MoarFPS.getConfig().fakeFps = fakeFps);
        addBooleanOption(entryBuilder,
                render,
                "fluctuate",
                true,
                () -> MoarFPS.getConfig().fluctuate,
                (fluctuate) -> MoarFPS.getConfig().fluctuate = fluctuate);
        addIntOption(entryBuilder,
                render,
                "amount",
                50,
                () -> MoarFPS.getConfig().fluctuateAmount,
                (fluctuateAmount) -> MoarFPS.getConfig().fluctuateAmount = fluctuateAmount);
        addBooleanOption(entryBuilder,
                render,
                "enabled",
                true,
                () -> MoarFPS.getConfig().enabled,
                (enabled) -> MoarFPS.getConfig().enabled = enabled);

        builder.setSavingRunnable(MoarFPS.getConfig()::save);
        return builder.build();
    }

    private static void addIntOption(ConfigEntryBuilder builder, ConfigCategory category, String name, int defaultVal, @NotNull Supplier<Integer> getter, @NotNull Consumer<Integer> setter) {
        category.addEntry(builder.startIntField(MoarFPS.translated("moarfps.config." + name), getter.get())
                .setTooltip(MoarFPS.translated("moarfps.config." + name + ".tooltip"))
                .setDefaultValue(defaultVal)
                .setSaveConsumer(setter)
                .build()
        );
    }

    private static void addBooleanOption(ConfigEntryBuilder builder, ConfigCategory category, String name, boolean defaultVal, @NotNull Supplier<Boolean> getter, @NotNull Consumer<Boolean> setter) {
        category.addEntry(builder.startBooleanToggle(MoarFPS.translated("moarfps.config." + name), getter.get())
                .setTooltip(MoarFPS.translated("moarfps.config." + name + ".tooltip"))
                .setYesNoTextSupplier(MoarFPS.YES_NO)
                .setDefaultValue(defaultVal)
                .setSaveConsumer(setter)
                .build()
        );
    }
}
