Automata-Gen-3/Omniscience
==============
This is my full Eclipse workspace folder, plus runnable jar.

Youtube videos of this program can be found at https://www.youtube.com/channel/UCZD4RoffXIDoEARW5aGkEbg/videos

Setup:

- Run program from included jar, or through Eclipse by selecting the "Automata-Gen-3-master/workspace" folder on program start. You could also import the included source to another IDE.

- Once running, click & drag window frame a little (for mouse location)

==============

Basic Use:

- Left click to set the value of cells according to selected Tool. This is typically 0 or 1, though some rules use other negative and positive values.

- Right click to toggle Play/Pause.

- Scroll Wheel will cycle through the selected MouseWheel menu item. The default is "Tool Subfunction", where scrolling changes the type of block to place.

- Then menu item 'Universe'/'Reset to Default' will reset the user interface to 'factory settings'.

==============

Colour Scheme & Graphics:

- Cells are one pixel each. I reccomend using 'magnifier' on windows or 'KMag' on linux when viewing.

- When the maximum value of all cells is 1, all cells of value 1 are black.

- When the maximum value is larger than one, value 1 is light blue, low numbers are whiter, higher numbers are blacker in a greyscale gradient.

- Negative numbers range from red to yellow.



==============
Notable Features, Advanced Use & Techniques (wip):
==============

3D: Every rule is viewable in a single 'plane' or Z-Layer, but these layers can, and might interact

- There is limited 3D support, involving what the user interface names 'layers'. Default universe settings include 16 layers.

- Default settings allow only compute the one layer that is active. 

- Activating all layers for computation can be done using the value -1 under dialog option "Target Z-Layer" of 'Ruleset'/'Recast Rule Parameters'.

- Some preset rules are aware of neighbours present in 3D space, and will typically behave strangly or resist change when only one layer is active.

- UI: Reseed All & Erase All will affect all layers. Reseed Layer & Erase Layer only affect the currently viewed layer.

==============

Tool Control: Tools can be used to place blocks when the universe is both paused & running

- Tools are used by single left-clicking, or dragging.

- Tools can be customised to have any integer: size, value, placement distribution strength Random(1/x), Value variation Random(x).

- Size and Value can be mapped to the mousewheel, allowing quick tool adjustments.

- To set a random value distribution between -50 and +50, for example, set the tool value to -50, and set the value variation to 101.

- 'Tools'/'Place Random Blocks' with Subfunctions 2 & 3: The distribution is set to spaceing 3 and spacing 2 horizontal lines instead of solid blocks.

- 'Tools'/'Place Random Blocks' with Subfunctions 1: Random values between -16 and 16 will be placed, in random positions.

- 'Tools'/'Apply Custom Tool': The last user-set tool will be restored and activated.

==============

Seeding: Injecting initial values or value overrides into the grid

- You can reseed both the current active layer, and all 3D layers through the 'Universe'/'Reseed x' menu

- Seeding can be configured in all the same ways at the Tools can under 'Universe'/'Set Seed'

- Constant Seeding applys a seed from RNG as rule each turn. It is considered as 'Rule -1', and can be added through the menu 'Ruleset'/'Add Constant Seeding'

- Constant Seeding is configurable like all rules that have additional parameters. It can be modified through the 'Ruleset'/'Recast Rule Parameters' menu item.

==============

Rulesets & Rule Manipulation: Rules' parameters, if applicable, can be altered and the global ruleset can be modified and built apon

==============

Mousewheel Function Binding: Certain functions can be bound to the mousewheel for quick navigation

==============

Menu List: Describe what each line of the list means




