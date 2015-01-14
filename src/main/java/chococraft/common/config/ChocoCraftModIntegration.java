package chococraft.common.config;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.item.ItemStack;

public class ChocoCraftModIntegration {

	public static void integrateMods() {
		registerThaumcraftCrops();
	}

	private static void registerThaumcraftCrops() {
		//Register fully grown gyshal from seeds
		FMLInterModComms.sendMessage("Thaumcraft", "harvestStandardCrop", new ItemStack(ChocoCraftBlocks.gysahlStemBlock, 1, 4));

		//Register fully grown gyshal from item
		FMLInterModComms.sendMessage("Thaumcraft", "harvestStandardCrop", new ItemStack(ChocoCraftBlocks.gysahlGreenBlock, 1));
	}

}
