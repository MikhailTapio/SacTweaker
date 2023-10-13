package com.plr.sactweaker.common.item.ref;

import com.plr.sactweaker.common.config.SwordSpecs;
import com.plr.sactweaker.common.item.impl.SacSwordItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.function.Supplier;

import static com.plr.sactweaker.SacTweaker.ITEMS;

public enum ItemReference implements Supplier<Item> {
    COSMIC_DAZZLE,
    GOLDEN_TUSK,
    ROUKANKEN,
    SCULK_CLEAVER,
    THE_OTHER_HALF,
    TIME_FLIES;

    ItemReference() {
        this.reg = ITEMS.register(this.name().toLowerCase(Locale.ROOT), () -> new SacSwordItem(SwordSpecs.valueOf(this.name())));
    }

    public static void init() {
    }

    private final RegistryObject<Item> reg;

    @Override
    public Item get() {
        return reg.get();
    }
}
