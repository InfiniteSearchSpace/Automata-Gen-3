Automata-Gen-3
==============
'Main' can be used to call different automata functions from 'automataLib'

Basic Use:

On program start, click and drag the window. This will make sure it captures mouse position accurately.

You'll see some menu numbers at the bottom similar to: x.x.x.x.xx

0.0.0.4.11111111 means (in order):

Active Surface ID: 0
Active Z-axis (depth) ID for this Surface: 0
Function Category: 0
Current Subfunction: 4

And the 11111111 means:

Surface0: Graphics Stopped
Surface0: Calculations Stopped
Surface1: Graphics Stopped
Surface1: Calculations Stopped
Surface2: Graphics Stopped
Surface2: Calculations Stopped
Surface3: Graphics Stopped
Surface3: Calculations Stopped


00111111 would mean only Surface 0 of 0-3 is updating graphics and automata calculations.

Controls: Everything is controlled by the mouse.

Left Click: Perform Current Subfunction from selected Category
Right Click: Pause/Unpause the currently selected Surface
Middle Mouse Click: Cycle through current Subunctions from selected Function Category

Special Left-Click Cases:

In all Function Categories:
  When Current Subfunction == 0, cycle through Z-axis for Current Surface
  When Current Subfunction == 4, cycle through Function Category

Other than that, Category 4, function 1 will cycle through active surfaces.

As a general rule,

Category 0 is placing solid blocks
Category 1 is placing random blocks
Category 2 is setting blocks to 0 / "deleting"
Category 3 is placing blocks with value 30 (this was for early testing, some functions cannot act on value 30 cells) 
Category 4 is utility: 
  1 = cycle active surface, 
  2 = clear all, 
  3 = toggle automata calculations only

Good luck, ha.