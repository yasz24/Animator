# Animator
Welcome to a world of animations! Ever wanted to see your thoughts or imaginations come alive? 
If yeah, then the Animator is your best buddy. All you have to do is 
describe what you want your animation to look like in text and the Animator will
do the rest! 
-------------
The Animator is an application built in the realms of Java 8. It uses the 
Swing toolkit for GUI rendering.
## Generating the TextFile
* #### Define the shapes in your animation:   
```bash
rectangle name [Name] min-x [bottom-left x-coordinate] min-y [bottom-left y-coordinate] width [width] height [height] color [R] [G] [B] from [appear-time] to [dissappear-time]
For ex:
>rectangle name window181 min-x 320.0 min-y -430.0 width 20.0 height 20.0 color 1 1 1 from 1 to 200
```
**or**
```bash
oval name [Name] center-x [center x-coordinate] center-y [center y-coordinate] x-radius [width] y-radius [height] color [R] [G] [B] from [appear-time] to [dissappear-time]
For ex:
>oval name star0 center-x 226  center-y 69 x-radius 3 y-radius 3 color 1 1 1 from 108 to 200
```
* #### Define moves and color transformations on your shapes:

