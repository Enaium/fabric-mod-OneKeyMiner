# OneKeyMiner

A minecraft mod for Fabric that allows you to mine blocks with one mining action.

## Usage

### Commands

- `/onekeyminer screen` - Displays the mod's screen.(Only works in singleplayer and on LAN servers and only for the
  player who open the LAN server)
- `/onekeyminer limit` - Displays the current limit.(Default: 64)
- `/onekeyminer limit <limit>` - Max amount of blocks that can be mined with one action.(Don't set it too high)
- `/onekeyminer <AXE|HOE|PICKAXE|SHOVEL|SHEARS|ANY> list` - Lists all blocks that are currently in the tool's list.
- `/onekeyminer <AXE|HOE|PICKAXE|SHOVEL|SHEARS|ANY> <ADD|REMOVE> <block>` - Adds or removes a block from the tool's
  list.
- `/onkeyminer interact` - Displays the current interact is enabled or not.(Default: false)
- `/onkeyminer interact <true|false>` - Enables or disables the `interact`.Some of the blocks can interact with other
  blocks, such as `Grass Block`, it can use `Hoe` to turn into `Farmland` or can use `Shovel` to turn into `Path`.