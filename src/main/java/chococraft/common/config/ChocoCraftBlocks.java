package chococraft.common.config;

import chococraft.common.blocks.BlockGysahlGreen;
import chococraft.common.blocks.BlockGysahlStem;
import chococraft.common.blocks.BlockStraw;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

/**
 * Created by clienthax on 23/10/2014.
 */
public class ChocoCraftBlocks {

	public static Block gysahlStemBlock;
	public static Block gysahlGreenBlock;
	public static Block strawBlock;

	public static void registerBlocks() {

		gysahlStemBlock = new BlockGysahlStem();
		GameRegistry.registerBlock(gysahlStemBlock, "gysahlStemBlock");

		gysahlGreenBlock = new BlockGysahlGreen();
		GameRegistry.registerBlock(gysahlGreenBlock, "gysahlGreenBlock");

		strawBlock = new BlockStraw();
		GameRegistry.registerBlock(strawBlock, "strawBlock");
	}
}
