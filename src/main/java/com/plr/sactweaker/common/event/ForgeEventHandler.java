package com.plr.sactweaker.common.event;

import com.plr.sactweaker.common.item.ref.ItemReference;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingSwapItemsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgeEventHandler {
    @SubscribeEvent
    public static void onSwapHands(LivingSwapItemsEvent.Hands event) {
        final ItemStack stack = event.getItemSwappedToMainHand();
        if (!(stack.is(ItemReference.THE_OTHER_HALF.get()))) return;
        stack.getOrCreateTag().putInt("switched", 80);
    }
}
