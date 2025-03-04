package net.hyper_pigeon.eldritch_mobs.mixin;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin extends HostileEntity {


    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(at = @At("HEAD"), method = "explode")
    public void removeEffects(CallbackInfo ci) {
        if (!this.world.isClient && EldritchMobsMod.isElite((ComponentProvider) this)) {
            // Cannot call remove in for-each
            /*for(StatusEffectInstance statusEffectInstance : this.getStatusEffects()){
                this.removeStatusEffect(statusEffectInstance.getEffectType());
            }*/
            List<StatusEffect> instances = this.getStatusEffects().stream().map(StatusEffectInstance::getEffectType).collect(Collectors.toList());
            instances.forEach(this::removeStatusEffect);
        }

    }


}
