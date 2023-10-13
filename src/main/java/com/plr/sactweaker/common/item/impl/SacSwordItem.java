package com.plr.sactweaker.common.item.impl;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.plr.sactweaker.SacTweaker;
import com.plr.sactweaker.common.config.SwordSpecs;
import com.plr.sactweaker.common.item.ref.ItemReference;
import com.plr.sactweaker.common.item.tier.SacTier;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SacSwordItem extends SwordItem {
    private Multimap<Attribute, AttributeModifier> defaultModifiers;
    private final SwordSpecs specs;
    private Ingredient ingredientCache;

    public SacSwordItem(SwordSpecs specs) {
        super(SacTier.INSTANCE, 0, .0F, new Properties().fireResistant().rarity(Rarity.EPIC));
        this.specs = specs;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.getModifiers() : super.getDefaultAttributeModifiers(slot);
    }

    public Multimap<Attribute, AttributeModifier> getModifiers() {
        if (defaultModifiers == null) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                    specs.attackDamage().get() - 1.0, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
                    specs.getSpecs().getAttackSpeed().get() - 4.0, AttributeModifier.Operation.ADDITION));
            builder.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(SacTweaker.BLOCK_REACH_MODIFIER_UUID, "Block reach modifier",
                    1.2, AttributeModifier.Operation.ADDITION));
            builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(SacTweaker.ENTITY_REACH_MODIFIER_UUID, "Entity reach modifier",
                    1.2, AttributeModifier.Operation.ADDITION));
            this.defaultModifiers = builder.build();
        }
        return defaultModifiers;
    }

    @Override
    public int getEnchantmentValue() {
        return specs.getEnchantmentValue().get();
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack1, ItemStack stack2) {
        if (ingredientCache == null) ingredientCache = this.specs.getRepairIngredient().get();
        return ingredientCache.test(stack2);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return specs.uses().get();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity user, int slot, boolean selected) {
        super.inventoryTick(stack, level, user, slot, selected);
        if (isNotTheOtherHalf()) return;
        if (user.level().isClientSide || !(user instanceof Player player)) return;
        final CompoundTag tag = stack.getOrCreateTag();
        final int switched = tag.getInt("switched");
        if (switched > 0) tag.putInt("switched", Math.max(0, switched - 1));
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (isNotTheOtherHalf() || !(entity instanceof Player player)) return super.onEntitySwing(stack, entity);
        final CompoundTag tag = stack.getOrCreateTag();
        final int switched = tag.getInt("switched");
        if (switched > 0 && !entity.onGround() && !player.getCooldowns().isOnCooldown(this)) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(5.0 * entity.getLookAngle().x, 0, 5.0 * entity.getLookAngle().z));
            final Level level = entity.level();
            if (!level.isClientSide)
                level.playSound(null, entity, SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.getCooldowns().addCooldown(this, switched);
            tag.putInt("switched", 0);
        }
        return super.onEntitySwing(stack, entity);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

    private boolean isNotTheOtherHalf() {
        return !this.equals(ItemReference.THE_OTHER_HALF.get());
    }
}
