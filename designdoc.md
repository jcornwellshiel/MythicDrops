# Design Document for MythicDrops

## Purpose
The purpose of MythicDrops is to create magic items for Minecraft servers. This will be accomplished through
learning from and attempting to fix the mistakes made in DiabloDrops, as well as using experience gained through
the programming and creation of that project. MythicDrops will be more customizable, more reliable, and more
powerful than DiabloDrops.

## Goals of Development
* General
  * Customizable
  * Flexible
  * Powerful
  * Easily Usable
  * Extendable

## Stages of Development
MythicDrops is going to follow the standard stages of initial development: Pre-Alpha, Alpha, Beta, and Release. There will
be development builds available in the future for any and all wishing to test the plugin.

Below is a general idea of what is planned to be done in each stage.

- [x] Pre-Alpha: The base coding for the system, as outlined in this design document. Configuration and randomization are the focus.
- [ ] Alpha: A fully customizable system of drops, including support for mod items. Abilities will be designed and created during this phase.
- [ ] Beta: This phase will be dedicated to testing the plugin thoroughly.
- [ ] Release: This phase involves the release of the project to the general public. Any products released will be fully usable and tested.

## Important Concepts for Items
### Tiers
Tiers are ways for the user to design items without having to hand-make each item. Tiers have special values that affect each of the items from that tier.
* Name: The name of the tier
* Display Name: The display name of the tier
* Color: The color of all items in the tier
* Identifier: A color code at the end of the item name to identify the tier
* Enchantment Amount: The maximum number of enchantments that can be placed on this tier on creation
* Enchantment Level: The maximum level for any enchantment placed on this tier on creation
* Automatic Enchantments: Enchantments that are always applied to items from this tier, no matter what
* Items in Tier: A list of item ids that can be from this tier
* Chance To Be Given: The chance that this tier will be the one that is given to a mob.
* Chance To Drop: The chance that this tier will be dropped when equipped.

## Abilities
### Contact Abilities
#### Armor
* Self-Immolation (sets wearer on fire)
* Immolation (sits hitter on fire)
* Self-Flight (launches wearer into the air)
* Flight (launches hitter into the air)
* Self-Blink (teleports wearer to nearby location)
* Blink (teleports hitter to a nearby location)
#### Tools
* Self-Immolation (sets wielder on fire)
* Immolation (sets target on fire)
* Self-Flight (launches wielder into the air)
* Flight (launches target into the air)
* Self-Blink (teleports wielder to nearby location)
* Blink (teleports target to a nearby location)
* Self-Poison (poisons the wielder)
* Poison (poisons the target)
* Self-Invisibility (makes the wielder invisible)
* Invisibility (makes the target invisible)
