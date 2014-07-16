Automata-Gen-3/Omniscience
==============
This is my full Eclipse workspace folder, plus runnable jar.

YouTube videos of this program can be found at https://www.youtube.com/channel/UCZD4RoffXIDoEARW5aGkEbg/videos

Setup:

- Run program from included jar, or through Eclipse by selecting the "Automata-Gen-3-master/workspace" folder on program start. You could also import the included source to another IDE.

==============

Basic Use:

- Right click to toggle Play/Pause.

- Left click on the grid use the selected Tool.

- Scroll Wheel will cycle through the selected MouseWheel menu item.

==============

Colour Scheme & Graphics:

- Cells are one pixel each. I recommend using 'magnifier' on windows or 'KMag' on Linux when viewing.

- Colour Scheme can be changed from the MouseWheel menu.

- Positive Numbers are typically black or grey-scale.

- Negative numbers are typically Red or Red-to-Yellow.

- When the maximum value of all cells is 1, all cells of value 1 are black.

- When the maximum value is larger than one, value 1 is light blue.




==============
Notable Features, Advanced Use & Techniques (wip):
==============

3D: Every rule is viewable in a single 'plane' or Z-Layer, but these layers can, and might interact

- There is limited 3D support, involving what the user interface names 'layers'. Default universe settings include 16 layers.

- Default settings only compute the one layer that is active. 

- Activating all layers for computation can be done using the value -1 under dialog option "Target Z-Layer" of menu item 'Ruleset'/'Recast Rule Parameters'.

- Some preset rules are aware of neighbours present in 3D space, and will typically behave strangely or resist change when only one layer is active.

- UI: Reseed All & Erase All will affect all layers. Reseed Layer & Erase Layer only affect the currently viewed layer.

==============

Tool Control: Tools can be used to place blocks when the universe is both paused & running

- Tools are used by left-clicking and/or dragging.

- Tools can be configured to have any integer: Placement Area, Value, Distribution Strength: Random(1/x), Value Variation: Random(x).

- Size and Value can be mapped to the MouseWheel, allowing quick tool adjustments.

- Config Example: To set a tool's Random Value Distribution between -50 and +50, set the tool value to -50, and set the value variation to 101.

Special Cases:

- 'Tools'/'Place Random Blocks' with Subfunctions 2 & 3: The distribution is set to spaceing 3 and spacing 2 horizontal lines instead of solid blocks.

- 'Tools'/'Place Random Blocks' with Subfunctions 1: Random values between -16 and 16 will be placed, in random positions.

- 'Tools'/'Apply Custom Tool': The last user-set tool will be restored and activated.

- 'Editor'/'Tool: Copy': Captures data from the grid to the clipboard

- 'Editor'/'Tool: Paste': Pastes data from the clipboard

==============

Seeding: Placing initial values or value-overrides into the grid

- You can reseed both the current active layer, and all 3D layers through the 'Universe'/'Reseed' options

- Seeding can be configured under 'Universe'/'Set Seed'

- 'Constant Seeding' is a special Rule that applies a seed from RNG each turn. It is considered as 'Rule -1', and can be added through the menu 'Ruleset'/'Add Constant Seeding'

- 'Constant Seeding' is configurable like all rules that have additional parameters. It can be modified through the 'Ruleset'/'Recast Rule Parameters' menu item.

==============

Full Menu Option Guide (wip)

==============