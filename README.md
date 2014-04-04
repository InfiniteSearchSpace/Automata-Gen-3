Automata-Gen-3
==============
This is my full Eclipse workspace folder.

Basic Use:

- Run program

- Click & drag window frame a little (for mouse location data)

- Right click to start and stop the automata

- Scroll up to change the function (unlabeled numbers currently)

- Left click to execute a function
	or
- Left click option 0 to scroll z-axis/depth where applicable
- Left click option 4 to change the function catagory

==============

Advanced Use/Details:

On program start, click and drag the window. This will make sure it captures mouse position accurately.

You'll see some numbers at the bottom similar to: x.x.x.x.xx

They represent the Menu.

Example Menu: 0.0.0.4.11111111

0.0.0.4 means (in order):

	Active Universe ID: 0
	Active Z-axis (depth) ID for this Universe: 0
	Function Category: 0
	Current Subfunction: 4

And the .11111111 means:

	Surface0: Graphics Stopped
	Surface0: Calculations Stopped
	Surface1: Graphics Stopped
	Surface1: Calculations Stopped
	Surface2: Graphics Stopped
	Surface2: Calculations Stopped
	Surface3: Graphics Stopped
	Surface3: Calculations Stopped


For a Menu with 111100 only Universe 2 (the third surface) of 0-2 is updating graphics and automata calculations.

==============

Controls: Everything is controlled by the mouse.

Left Click: Perform Current Subfunction from selected Category
Right Click: Pause/Unpause the currently selected Surface
Scroll Wheel: Cycle through current Subunctions from selected Function Category

Special Left-Click Cases:

In all Function Categories:
	When Current Subfunction == 0, cycle through Z-axis for Current Universe
	When Current Subfunction == 4, cycle through Function Category

Category 4, subfunction 1 will cycle through active surfaces.

- Category 0 - placing solid blocks
- Category 1 - placing random blocks
- Category 2 - setting blocks to 0 / "deleting"
- Category 3 - placing blocks with value 30 (this was for early testing, some functions cannot act on value 30 cells) 

- Category 4 is utility: 
	1 = cycle active universe, 
	2 = clear all, 
	3 = toggle automata calculations only
