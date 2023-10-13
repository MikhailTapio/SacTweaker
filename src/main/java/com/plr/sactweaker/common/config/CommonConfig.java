package com.plr.sactweaker.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class CommonConfig {
    public static final ForgeConfigSpec CFG;
    public static final Map<SwordSpecs, SwordSpecsSetting> specsMap = new HashMap<>();

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        for (SwordSpecs sword : SwordSpecs.values())
            specsMap.put(sword, new SwordSpecsSetting(builder, sword.name(), sword.getDefaultSpecs()));
        CFG = builder.build();
    }
}
