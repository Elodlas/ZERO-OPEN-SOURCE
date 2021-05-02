package dev.elodlas.customs.arrows;

import org.bukkit.potion.PotionEffectType;

public class PotionImpactEffect extends ImpactEffect{

    public PotionEffectType type;
    public int amplifier;

    public PotionImpactEffect(PotionEffectType type, int duration, int amplifier){
        super(duration);
        this.type = type;
        this.amplifier = amplifier;
    }
}
