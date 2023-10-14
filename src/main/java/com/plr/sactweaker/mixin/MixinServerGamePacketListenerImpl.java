package com.plr.sactweaker.mixin;

import com.plr.sactweaker.common.item.ref.ItemReference;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinServerGamePacketListenerImpl {
    @Shadow
    public ServerPlayer player;

    @Inject(method = "handlePlayerAction", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;resetLastActionTime()V"))
    private void inject$handlePlayerAction(ServerboundPlayerActionPacket packet, CallbackInfo ci) {
        if (!packet.getAction().equals(SWAP_ITEM_WITH_OFFHAND)) return;
        final ItemStack stack = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!(stack.is(ItemReference.THE_OTHER_HALF.get()))) return;
        stack.getOrCreateTag().putInt("switched", 80);
    }
}
