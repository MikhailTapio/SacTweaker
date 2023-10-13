package com.plr.sactweaker;

import com.plr.sactweaker.common.config.CommonConfig;
import com.plr.sactweaker.common.item.ref.ItemReference;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

@Mod(SacTweaker.MODID)
public class SacTweaker {
    public static final String MODID = "sactweaker";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SacTweaker.MODID);
    public static final UUID BLOCK_REACH_MODIFIER_UUID = UUID.fromString("03E82BB8-9D24-568B-BA29-FABC992165F1");
    public static final UUID ENTITY_REACH_MODIFIER_UUID = UUID.fromString("DAF854FA-DA5A-7556-495C-0FFE4DA69C95");

    public SacTweaker() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.CFG);
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        ItemReference.init();
    }
}
