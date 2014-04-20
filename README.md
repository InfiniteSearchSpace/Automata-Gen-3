Automata-Gen-3
==============
This is my full Eclipse workspace folder, plus runnable jar.

Setup:

- Run program from included jar, or through Eclipse by selecting the "Automata-Gen-3-master/workspace" folder on program start. You could also import the included source to another IDE.

- Once running, click & drag window frame a little (for mouse location)

Program Use:

- Left click to execute the selected Tool's subfunction.

- Scroll up or down to cycle through the target selected from the "MouseWheel" menu. 

- Right click to toggle Play/Pause.

==============
Advanced Use/Details:
==============

Controls: Everything is controlled by the mouse.

- Left Click: Sets the value of cells according to selected Tool. This is typically 0 or 1, though some rules use other negative and positive values.

- Right Click: Pause/Unpause

- Scroll Wheel: Cycle through the selected MouseWheel menu item. The default is "Tool Subfunction", where scrolling changes the type of block to place.

==============

Colour Scheme & Graphics:

- Cells are one pixel each. I reccomend using 'magnifier' on windows or 'KMag' on linux when viewing.

- When the maximum value of all cells is 1, all cells of value 1 are black.

- When the maximum value is larger than one, value 1 is light blue, low numbers are whiter, higher numbers are blacker according to a greyscale gradient

- All Negative numbers are the same red

==============

You'll see some numbers at the bottom similar to: x.x.x.x.xx {x,x}

They represent the Menu.

Terms:
	
	Surface				- the individual windows that show the visual automata updates
	Universe 			- the object that contains the placements and values, and has actions performed on it
	Z-axis/Depth ID 		- the x*y layer, at index z
	Tool 				- determines the type of left-click action: place blocks, erase, utility
	Subfunction			- determines which action from a category to perform (default is 4 different variations of each tool)
	Ruleset				- determines which cellular automata rules to use

Example Menu: 0.2.3.4.111100 {1,15}

0.2.3.1 means (in order):

	Active Universe ID: 0
	Active Z-axis/depth ID for this Universe: 2
	Tool Category: 3
	Current Tool Subfunction: 1

.111100 means:

	Surface0: Graphics Stopped
	Surface0: Calculations Stopped
	Surface1: Graphics Stopped
	Surface1: Calculations Stopped
	Surface2: Graphics Running
	Surface2: Calculations Running

{1,15} means for each iteration:
	
	Do Instruction 1, Then Do Instruction 15

==============

- Category x.x.4.1.xx combines three random automata with random parameters, and a seed. It's chaotic 95% of the time, but you might stumble into an interesting combination! Try it out!

Interesting Combos:
- 36,44,0
- 31,39,23
- 4,5
- 26,22,23
- 23,18,0
- 27,11,30
- 47,35,16
