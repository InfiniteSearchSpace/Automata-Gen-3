Automata-Gen-3
==============
This is my full Eclipse workspace folder.

Basic Use:


- Run program

- Click & drag window frame a little (for mouse location data)

- Right click to start and stop the automata

- Scroll up or down to change the subfunction (unlabeled numbers currently)

- Click scrollwheel to change the function category

- Left click to execute a function
	
- Left click option 0 to scroll z-axis/depth where applicable

- Menu items 4.3 & 4.4 change the automata ruleset while preserving the current placements


==============
Advanced Use/Details:
==============

Colour Scheme:

- When max value is 1, all value 1 is black
- When max value is larger than one, value 1 is light blue, low numbers are white, with gradient to the highest numbers in black.
- Negative numbers are red

==============

You'll see some numbers at the bottom similar to: x.x.x.x.xx {x,x}

They represent the Menu.

Terms:
	
	Surface			- the individual windows that show visual updates
	Universe 		- the object that contains the placements and values
	Z-axis ID 		- the x*y layer at index z
	Function Catagory/Type 	- determines the type of action: place blocks, delete, utility...
	Subfunction		- determines which action from a catagory to perform
	Instruction		- determines which cellular automata rules to use

Example Menu: 0.0.0.4.111100 {1,15}

0.0.0.4 means (in order):

	Active Universe ID: 0
	Active Z-axis (depth) ID for this Universe: 0
	Function Category: 0
	Current Subfunction: 4

.111100 means:

	Surface0: Graphics Stopped
	Surface0: Calculations Stopped
	Surface1: Graphics Stopped
	Surface1: Calculations Stopped
	Surface2: Graphics Running
	Surface2: Calculations Running

{1,15} means:
	
	Do Instruction 1
	Do Instruction 15

==============

Menu functions:

- Category 0 - placing solid blocks
- Category 1 - placing random blocks
- Category 2 - setting blocks to 0 / "deleting"
- Category 3 - placing blocks with value 100

Category 4 is utility: 

	1 = Cycle UI selection to the next surface
	2 = Reset all to 0 / Clear all
	3 = Changes z-layer to a randomised automata
	4 = Changes z-layer to the next automata in sequence

==============

Controls: Everything is controlled by the mouse.

- Left Click: Perform Current Subfunction from selected Category

- Right Click: Pause/Unpause the currently selected Surface

- Scroll Wheel: Cycle through current Subunctions from selected Function Category

- Scroll Wheel Click: Cycle through function category

Special Left-Click Case:

In all function categories, when Current Subfunction == 0, cycle through Z-axis for the currently selected Universe



		
