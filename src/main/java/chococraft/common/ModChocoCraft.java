// <copyright file="ModChocoCraft.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2012-09-10</date>
// <summary>Base mod class for the ChocoCraft mod</summary>

package chococraft.common;

import chococraft.common.config.*;
import chococraft.common.config.gson.ConfigGson;
import chococraft.common.events.ChocoboPlayerTracker;
import chococraft.common.gui.ChocoboGuiHandler;
import chococraft.common.network.PacketRegistry;
import chococraft.common.proxy.CommonProxyChocoCraft;
import chococraft.common.tick.ServerSpawnTickHandler;
import chococraft.common.utils.UpdateChecker;
import chococraft.common.worldgen.WorldGenGysahls;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

@Mod(modid= Constants.TCC_MODID, name=Constants.TCC_NAME, version=Constants.TCC_VERSION)
public class ModChocoCraft
{
	//TODO
	//Redo config file to use gson
	//config drop rate for pedia - see above..
	//cleanup stupid main mod class bloated with crap
	public static Configuration mainConfiguration;

	public static boolean debugMode = false;

	// setup
	public static File configFolder;
	@Deprecated
	public static boolean chocoboWingFlutter;
	@Deprecated
	public static int genderMaleChance;//TODO add to config
	@Deprecated
	public static boolean showChocoboNames;
	@Deprecated
	public static boolean hungerEnabled;
	@Deprecated
	public static boolean riderBuffsEnabled;
	@Deprecated
	public static boolean wildCanDespawn;
	
	// chocobo size setup
	public static float chocoboHeight;
	public static float chocoboWidth;
	
	// gysahl mutation setup
	@Deprecated
	public static int gysahlGreenMutationRate;
	@Deprecated
	public static int gysahlLoveMutationRate;
	
	// gysahl world generation setup
	@Deprecated
	public static int gysahlWorldGenRate;//TODO add to config
	@Deprecated
	public static int gysahlSeedGrassDropWeight;//TODO add to config
	
	// feather drop setup
	@Deprecated
	public static int featherDropChance;
	@Deprecated
	public static int featherDelayRandom;
	@Deprecated
	public static int featherDelayStatic;
	
	// pen heal setup
	@Deprecated
	public static int penHealProbability;
	@Deprecated
	public static int penHealCauldronRange;
	
	// spawn setup
	@Deprecated
	public static int spawnTimeDelay;
	@Deprecated
	public static int spawnGroupMin;
	@Deprecated
	public static int spawnGroupMax;
	@Deprecated
	public static int spawnTotalMax;
	@Deprecated
	public static int spawnProbability;
	@Deprecated
	public static int spawnLimitChunkRadius;
	@Deprecated
	public static int spawnDistanceNextWild;
	
	// procreate setup
	@Deprecated
	public static int breedingDelayMale;
	@Deprecated
	public static int breedingDelayFemale;
	@Deprecated
	public static int growupDelayStatic;
	@Deprecated
	public static int growupDelayRandom;
	
	// hunger setup
	@Deprecated
	public static int hungerDelayChocobo;
	@Deprecated
	public static int hungerDelayChicobo;
	
	// movement setup
	@Deprecated
	public static boolean saddledCanWander;
	
	// chocopedia setup
	@Deprecated
	public static boolean chocopediaInDungeons;
	
	// debug
	public static long spawnDbTimeDelay;
	public static String spawnDbStatus;

	@Deprecated
	public static double renderNameHeight;
	@Deprecated
	public static int livingSoundProb;
	
	public static boolean isRemoteClient = false;

	@Deprecated
	public static BiomeGenBase[] spawnBiomes =
	{
		BiomeGenBase.extremeHills,
		BiomeGenBase.extremeHillsEdge,
		BiomeGenBase.forest,
		BiomeGenBase.forestHills,
		BiomeGenBase.jungle,
		BiomeGenBase.jungleHills,
		BiomeGenBase.plains,
		BiomeGenBase.swampland,
		BiomeGenBase.taiga,
		BiomeGenBase.taigaHills,
		BiomeGenBase.iceMountains,
		BiomeGenBase.icePlains,
		BiomeGenBase.hell
	};

	@Instance(Constants.TCC_MODID)
	public static ModChocoCraft instance;

	@SidedProxy(clientSide = "chococraft.client.ClientProxyChocoCraft", serverSide = "chococraft.common.proxy.CommonProxyChocoCraft")
	public static CommonProxyChocoCraft proxy;


	public static ConfigGson chococraftConfig = null;

	@EventHandler
	public void preLoadChocoCraft(FMLPreInitializationEvent preInitEvent)
	{

		PacketRegistry.registerPackets();

		chocoboHeight = 1.9F;
		chocoboWidth = 1.3F;

		configFolder = preInitEvent.getModConfigurationDirectory();

		chocoboWingFlutter   = Constants.DEFAULT_CHOCOBO_WING_FLUTTER;
		hungerEnabled        = Constants.DEFAULT_HUNGER_ENABLED;
		riderBuffsEnabled    = Constants.DEFAULT_RIDER_BUFFS_ENABLED;
		showChocoboNames     = Constants.DEFAULT_SHOW_CHOCOBO_NAMES;
		wildCanDespawn       = Constants.DEFAULT_WILD_CAN_DESPAWN;
		
		genderMaleChance     = Constants.DEFAULT_GENDER_MALE_CHANCE;
		
		gysahlGreenMutationRate = Constants.DEFAULT_GYSAHL_GREEN_MUTATION_RATE;
		gysahlLoveMutationRate  = Constants.DEFAULT_GYSAHL_LOVE_MUTATION_RATE;

		gysahlWorldGenRate   = Constants.DEFAULT_GYSAHL_WORLD_GEN_RATE;
		gysahlSeedGrassDropWeight = Constants.DEFAULT_GYSAHL_SEED_GRASS_DROP_WEIGHT;

		featherDropChance    = Constants.DEFAULT_FEATHER_DROP_CHANCE;
		featherDelayRandom   = Constants.DEFAULT_FEATHER_DELAY_RANDOM;
		featherDelayStatic   = Constants.DEFAULT_FEATHER_DELAY_STATIC;
		
		penHealProbability   = Constants.DEFAULT_PEN_HEAL_PROBABILITY;
		penHealCauldronRange = Constants.DEFAULT_PEN_HEAL_CAULDRON_RANGE;

		spawnTimeDelay = Constants.DEFAULT_SPAWN_TIME_DELAY;
		spawnGroupMin = Constants.DEFAULT_SPAWN_GROUP_MIN;
		spawnGroupMax = Constants.DEFAULT_SPAWN_GROUP_MAX;
		spawnTotalMax = Constants.DEFAULT_SPAWN_TOTAL_MAX;
		spawnProbability = Constants.DEFAULT_SPAWN_PROBABILITY;
		spawnLimitChunkRadius = Constants.DEFAULT_SPAWN_LIMIT_CHUNK_RADIUS;
		spawnDistanceNextWild = Constants.DEFAULT_SPAWN_DIST_NEXT_WILD;
		
		spawnDbTimeDelay = 0;
		spawnDbStatus = "";
		
		breedingDelayMale = Constants.DEFAULT_BREEDING_DELAY_MALE;
		breedingDelayFemale = Constants.DEFAULT_BREEDING_DELAY_FEMALE;
		growupDelayRandom = Constants.DEFAULT_GROWUP_DELAY_RANDOM;
		growupDelayStatic = Constants.DEFAULT_GROWUP_DELAY_STATIC;
		
		hungerDelayChicobo = Constants.DEFAULT_HUNGER_DELAY_CHICOBO;
		hungerDelayChocobo = Constants.DEFAULT_HUNGER_DELAY_CHOCOBO;
		
		renderNameHeight = Constants.DEFAULT_RENDER_NAME_HEIGHT;
		livingSoundProb = Constants.DEFAULT_LIVING_SOUND_PROB;
		
		saddledCanWander = Constants.DEFAULT_SADDLED_CAN_WANDER;
		
		chocopediaInDungeons = Constants.DEFAULT_CHOCOPEDIA_IN_DUNGEONS;
				
    	ChocoboConfig.readConfigFilePreInit();
    	
    	proxy.registerEventListener();
    	
    	ChocoboConfig.readConfigFileInit();

		ChocoCraftBlocks.registerBlocks();
		ChocoCraftItems.registerItems();

		ChocoCraftModIntegration.integrateMods();

		chococraftConfig = new ConfigGson();
	}
	
	@EventHandler
	public void loadChocoCraft(FMLInitializationEvent loadEvent)
	{
		//this.createCreativeTab();
		ChocoCraftWorld.registerDungeonLoot();
		ChocoCraftWorld.addGrassDrops();

		ChocoCraftRecipes.registerRecipes();
		ChocoCraftRecipes.registerSmeltingRecipes();

		ChocoCraftEntities.registerChocobos();

		proxy.registerRenderInformation();
		proxy.registerRenderThings();

		GameRegistry.registerWorldGenerator(new WorldGenGysahls(), 5);//TODO check how weights work

		FMLCommonHandler.instance().bus().register(new ChocoboPlayerTracker());

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new ChocoboGuiHandler());

		FMLCommonHandler.instance().bus().register(new ServerSpawnTickHandler());

		new Thread(new UpdateChecker()).start();
	}
	

}
