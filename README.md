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
```bash
move name [Name] moveto [start-x] [start-y] [end-x] [end-y] from [start-time] to [end-timer]
For ex:
>move name window181 moveto 140.0 850.0 140.0 710.0 from 20 to 60
```

```bash
change-color name [Name] colorto [start-R] [start-G] [start-B] [end-R] [end-G] [end-B] from [start-time] to [end-time]
For ex:
>change-color name background colorto 0.1333 0.37 0.976 0.063 0.18 0.976 from 50 to 90
```
* #### And that's it! Checkout some of the samples in the "sample text files" directory if you want to see what a full animation text file looks like.

# Using the application
* #### Simply download the Animator.jar file from the Animator_jar directory.
* #### Download one or more animation text files from the "sample text files" directory, or create your own. 
* #### Start the application from the command-line:
```bash
java -jar /Path-to-the-jar-file/Animator.jar -if [input file] -iv [view type] -speed [ticks per second] 
For ex:
>java -jar ~/Desktop/Animator/Animator_jar/Animator.jar -if big-bang-big-crunch.txt -iv interact_slider -speed 100
```
