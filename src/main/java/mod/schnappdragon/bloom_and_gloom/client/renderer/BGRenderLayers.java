package mod.schnappdragon.bloom_and_gloom.client.renderer;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import mod.schnappdragon.bloom_and_gloom.core.util.CompatHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BGRenderLayers {

    public static void registerRenderLayers() {
        RenderTypeLookup.setRenderLayer(BGBlocks.RAFFLESIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BGBlocks.KABLOOM_BUSH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BGBlocks.SLIME_FERN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BGBlocks.WALL_SLIME_FERN.get(), RenderType.getCutout());

        CompatHelper.registerCompat("quark", () -> RenderTypeLookup.setRenderLayer(BGBlocks.POTTED_RAFFLESIA.get(), RenderType.getCutout()));
        CompatHelper.registerCompat("quark", () -> RenderTypeLookup.setRenderLayer(BGBlocks.POTTED_KABLOOM_BUSH.get(), RenderType.getCutout()));
        RenderTypeLookup.setRenderLayer(BGBlocks.POTTED_SLIME_FERN.get(), RenderType.getCutout());
    }
}