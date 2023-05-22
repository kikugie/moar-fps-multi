package me.kikugie.moarfps.config;

import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.OptionGroup;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.gui.controllers.TickBoxController;
import dev.isxander.yacl.gui.controllers.string.number.IntegerFieldController;
import me.kikugie.moarfps.MoarFPS;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class YaclConfig {
    public static Screen createGui(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.of("FPS Config"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("Render"))
                        .group(OptionGroup.createBuilder()
                                .option(optionForInt("Desired FPS",
                                        1000,
                                        () -> MoarFPS.getConfig().fakeFps,
                                        (fakeFps) -> MoarFPS.getConfig().fakeFps = fakeFps))
                                .option(optionForBool("Make it appear less fake?",
                                        true,
                                        () -> MoarFPS.getConfig().fluctuate,
                                        (fluctuate) -> MoarFPS.getConfig().fluctuate = fluctuate))
                                .option(optionForInt("How much less fake?",
                                        50,
                                        () -> MoarFPS.getConfig().fluctuateAmount,
                                        (fluctuateAmount) -> MoarFPS.getConfig().fluctuateAmount = fluctuateAmount))
                                .option(optionForBool("Get better FPS?",
                                        true,
                                        () -> MoarFPS.getConfig().enabled,
                                        (enabled) -> MoarFPS.getConfig().enabled = enabled))
                                .build())
                        .build())
                .save(MoarFPS.getConfig()::save)
                .build()
                .generateScreen(parent);
    }

    private static Option<Integer> optionForInt(String name, int defaultVal, @NotNull Supplier<Integer> getter, @NotNull Consumer<Integer> setter) {
        return Option.createBuilder(Integer.class)
                .name(Text.of(name))
                .binding(defaultVal,
                        getter,
                        setter)
                .controller(IntegerFieldController::new)
                .build();
    }

    private static Option<Boolean> optionForBool(String name, boolean defaultVal, @NotNull Supplier<Boolean> getter, @NotNull Consumer<Boolean> setter) {
        return Option.createBuilder(Boolean.class)
                .name(Text.of(name))
                .binding(defaultVal,
                        getter,
                        setter)
                .controller(TickBoxController::new)
                .build();
    }
}
