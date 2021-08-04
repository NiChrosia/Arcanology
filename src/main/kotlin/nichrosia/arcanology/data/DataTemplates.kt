package nichrosia.arcanology.data

import net.minecraft.block.Block
import net.minecraft.block.OreBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object DataTemplates {
    fun normalBlockLootTable(blockItem: BlockItem): String {
        return "{\n" +
                "  \"type\": \"minecraft:block\",\n" +
                "  \"pools\": [\n" +
                "    {\n" +
                "      \"rolls\": 1,\n" +
                "      \"bonus_rolls\": 0,\n" +
                "      \"entries\": [\n" +
                "        {\n" +
                "          \"type\": \"minecraft:item\",\n" +
                "          \"name\": \"${Registry.ITEM.getId(blockItem)}\",\n" +
                "\n" +
                "          \"functions\": [\n" +
                "            {\n" +
                "              \"function\": \"minecraft:set_count\",\n" +
                "              \"count\": 1\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"conditions\": [\n" +
                "        {\n" +
                "          \"condition\": \"minecraft:survives_explosion\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

    fun oreBlockToRawOre(ore: OreBlock, rawOre: Item): String {
        return "{\n" +
                "  \"type\": \"minecraft:block\",\n" +
                "  \"pools\": [\n" +
                "    {\n" +
                "      \"rolls\": 1,\n" +
                "      \"bonus_rolls\": 0,\n" +
                "      \"entries\": [\n" +
                "        {\n" +
                "          \"type\": \"minecraft:alternatives\",\n" +
                "          \"children\": [\n" +
                "            {\n" +
                "              \"type\": \"minecraft:item\",\n" +
                "              \"conditions\": [\n" +
                "                {\n" +
                "                  \"condition\": \"minecraft:match_tool\",\n" +
                "                  \"predicate\": {\n" +
                "                    \"enchantments\": [\n" +
                "                      {\n" +
                "                        \"enchantment\": \"minecraft:silk_touch\",\n" +
                "                        \"levels\": {\n" +
                "                          \"min\": 1\n" +
                "                        }\n" +
                "                      }\n" +
                "                    ]\n" +
                "                  }\n" +
                "                }\n" +
                "              ],\n" +
                "              \"name\": \"${Registry.BLOCK.getId(ore)}\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"minecraft:item\",\n" +
                "              \"functions\": [\n" +
                "                {\n" +
                "                  \"function\": \"minecraft:apply_bonus\",\n" +
                "                  \"enchantment\": \"minecraft:fortune\",\n" +
                "                  \"formula\": \"minecraft:ore_drops\"\n" +
                "                },\n" +
                "                {\n" +
                "                  \"function\": \"minecraft:explosion_decay\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"name\": \"${Registry.ITEM.getId(rawOre)}\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

    fun normalBlockstate(block: Block): String {
        return "{\n" +
                "  \"variants\": {\n" +
                "    \"\": {\n" +
                "      \"model\": \"arcanology:block/${Registry.BLOCK.getId(block).path}\"\n" +
                "    }\n" +
                "  }\n" +
                "}"
    }

    fun normalBlockModel(block: Block): String {
        return "{\n" +
                "  \"parent\": \"block/cube_all\",\n" +
                "  \"textures\": {\n" +
                "    \"all\": \"arcanology:block/${Registry.BLOCK.getId(block).path}\"\n" +
                "  }\n" +
                "}"
    }

    fun blockItemModel(item: BlockItem): String {
        return "{\n" +
                "  \"parent\": \"arcanology:block/${Registry.BLOCK.getId(item.block).path}\"\n" +
                "}"
    }

    fun normalItemModel(item: Item): String {
        return "{\n" +
                "  \"parent\": \"item/generated\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"arcanology:item/${Registry.ITEM.getId(item).path}\"\n" +
                "  }\n" +
                "}"
    }
}