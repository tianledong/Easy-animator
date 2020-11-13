# Easy Animator
## Structure:
### Model:
Our main model **AnimatorModelImpl** implements the **AnimatorModel** interface. The shapes and animations are stored in array lists, which is an easy way to store, find, and sort the elements we need.
The main model has 2 major private fields:

- shapes: The list of all the shapes in the animator to be viewed. Can't have shapes with the same name.
- animations: The list of all animations in the animator to be executed. Can't have animations that contradict to each other,
              for example, a rectangle can move to left and right at the same time.
 
### View:
The view parts of the animator consist of one interface **View**, and four concrete classes that implement the interface: **TextualView**, **SVGView**, **VisualView**, and **EditorView**. Each view stores a copy of the animator model, a speed that indicates the play speed of the animation, and a **ViewType** object. Different view classes might contain more than 
these three fields, but model, speed and view type are necessary in every view class. 

The **TextualView** and **SVGView** are basically used to produce a file that is the textual description of the animation or a SVG file that can be played in the browser. The **VisualView** can be played directly in the terminal or IDE. The **EditorView** generates a GUI interface and allows users play, pause, resume and restart the animation. Also, it can 
output textual and SVG files as well as loading a new file from the system.

### Controller:
We have only one single controller for the animator. It contains one interface and one concrete class. The controller uses a single method and switch between different view types to initialize different concrete views. Moreover, it records the operation logs
so that it's convenient for us to debug and test. The controller stores a copy of an **AnimatorModel** and a **View** to execute different commands. It also takes in a speed and a file name for the views to produce files and play the animation.


  
## Packages:
- **model**: This package includes all classes that represent a shape, animation, and the whole animator model. It contains 2 sub-packages: shape and behavior. The shape package contains all classes that represent a shape object. The
             behavior package contains all classes that represent a single animation. The animator model is represented by the AnimatorModel and implemented by the AnimatorModelImpl.

- **view**: This package includes all classes that represent different types of views. The View interface lists all public methods a concrete view should support. For now, there are three kinds of view: textual, svg, and visual. They
            are implemented in the corresponding classes and each of them has some methods that are not supported in the view type.

- **util**: This is the package that is provided as a starter code. It contains a class that helps parse the resource files, and an interface that represents a model builder, which is implemented in the AnimatorModelImpl class.

- **controller**: This is the package for the controller. For this project, there is only one controller that controls all types of views. It has one interface and one concrete class that implements the interface.
              
## Classes:
**Note:** Below are the brief description of our classes and interfaces. Detailed information can be found in the java docs in the classes.
- **EasyAnimator**: The main class and temporary controller (We will build real controllers in the third vision). Now, it is used to read the command line and pass it to model and viewer.

- **Builder**: A class that implements from **AnimationBuilder** and contains all public methods from **AnimationBuilder**. It is implemented in **AnimatorModelImpl. The function of builder is to make **AnimationReader** works. It uses LinkedHashMap to store 
               the data from the file and use the build() function to make a AnimatorModelImpl object.

- **View**: The interface that contains all public methods of the **View** objects. The valid view types for now are textual view, svg view, and visual view. The textual view basically writes the description of the animations to
            a file, and the svg view does the similar thing, but the output file is a svg file that can be played in the browser. The visual view plays the animation directly in Java. The editor view has GUI to interact with users; it can play animation, export SVG files and export Text file like other three do.
            Also, it can open file and change the speed from the GUI.

- **AnimationPanel**: A class that extends the **JPanel** class and represents a panel in the **VisualView** class, which extends the **JFrame** class. It's a panel that stores all shapes that are to be animated.

- **SVGView**: A class that represents the svg view type. Its main feature is producing a svg file that can be played directly in the internet browser.

- **TextualView**: A class that represents the textual view type. It is basically used to output a txt file that describes the animation. 

- **VisualView**: A class that represents the visual view type. It allows the user to directly play the animation in Java but doesn't allow the user to output a file that can be played in the browser or describes the animation.
                  It extends the **JFrame** and uses **JPanel** as a component. Its bounds should be setup by using the setCanvas method.  

- **ViewFactory**: A factory class that initializes appropriate view classes based on the string passed in. The string "text" initializes a **TextualView**, "svg" initializes a "SVGView", and "visual" initializes a **VisualView**.

- **EditorView**: A class that represents the editor view type. It extends JFrame and implements interface View and ActionListener. It's main feature is to show a GUI to interact with users. First, users can play, pause, resume, restart and loop animations by clink buttons.
                  Second, users can control the speed of animations by increase and decrease speed buttons, but the speed can't be less than 1. Current speed will be shown on the GUI. Third, uses can produce svg and text file by export svg and export text buttons. Last, uses can use Open file button to open files and play other animation.

- **ViewType**: This is an enum class that lists all types of view: textual view, svg view, editor view and visual view.

- **Controller**: The interface that contains all public methods of the Controller objects. A Controller object should be able to start the animation by **start()** and get users action by **getLog()** 

- **ControllerImpl**: This class implements all public methods in the Controller and ActionListener interface. It uses a StringBuilder to store all behaviors that users make. It can use **start()** to distinguish the view type and control the actions of views by call relative view functions. If the view type is Editor view, the controller is also a lisenter.
                      When it receive the ActionEvent command, it will call the **setFeature(action)** in the editor view and record the command in the log. 

- **Behavior**: An interface that lists all public methods a **Behavior** object has. An animation behavior should be able to return its start time, end time, shape, current behavior, begin status , end status, type and description.

- **AbstractBehavior**: The abstract class of a Behavior object, represents an abstracted Behavior. It implements the most of the methods in **Behavior** interface, but the **getCurrentBehavior()**, **getStartStatus()**, **getEndStatus()**, 
                        **change()**, and **getType()** methods are implemented in the concrete classes.

- **BehaviorType**: An enum class that lists all types of **Behavior** objects that are supported in the animator.

- **ChangeColor**: A class that represents the behavior of change color to change **Shape** color. **getCurrentBehavior()**, **getStartStatus()**, **getEndStatus()**, **change()**, and **getType()** methods are implemented here.

- **Move**: A class that represents the behavior of move to change **Shape** position. **getCurrentBehavior()**, **getStartStatus()**, **getEndStatus()**, **change()**, and **getType()** methods are implemented here.

- **Scale**: A class that represents the behavior of scale to change **Shape** dimensions. **getCurrentBehavior()**, **getStartStatus()**, **getEndStatus()**, **change()**, and **getType()** methods are implemented here.

- **Shape**: An interface that lists all public methods a **Shape** object has, like get the name of the shape, get the color of the shape, etc.

- **AbstractShape**: The abstract class of a Shape object, represents an abstracted shape. It implements the most of the methods in **Shape** interface, but the **copyShape()**, **getDimension1Tag()**, **getDimension2Tag()**, and **getPositionTag()** methods are implemented in the concrete classes.

- **ShapeType**: An enum class that lists all types of **Shape** objects that are supported in the animator.

- **Point**: A class that represents a 2D point. It's basically used as the reference point of a **Shape** object.

- **Rectangle**: A class that represents a rectangle, extends the **AbstractShape** class. The **copyShape()**, **getDimension1Tag()**, **getDimension2Tag()**, 
                 and **getPositionTag()** methods are implemented here.

- **Oval**: A class that represents an oval, extends the **AbstractShape** class. The **copyShape()**, **getDimension1Tag()**, **getDimension2Tag()**, 
            and **getPositionTag()** methods are implemented here.

- **StringFormat**: A class that is used to provide strings that represent the fields of the **Shape** objects. For instance, it can convert the reference point
                    of a **Shape** object into the form **"(1.0, 2.0)"**. It can be used to reduce the amount of duplicating codes that are used to print 
                    the state of objects.

- **AnimatorModel**: An interface that lists all public methods our animator model uses.

- **AnimatorModelImpl**: This class implements all public methods in the **AnimatorModel** interface. It uses 2 array lists to store the shapes and animations.
                         It doesn't allow the user to store shapes that have the same name or animations that contradict to each other.

- **AnimationBuilder**: An interface of the builder that is used to build an animator model. It's provided as starter code and is implemented by the **Builder** class.

- **AnimationReader**: A helper to read animation data and construct an animation from it. It's provided as starter code.


==========================================
## Changes from assignment 7 to assignment 8

Basically, we do not have big structure change in this time. We did some slightly change for the better fitting for the views. The biggest change is that we add some methods that is used in the svg view to the StringFormat class
and the concrete shape classes as well as the concrete behavior classes. Those methods returns strings that is directly output to the svg file and the detail can be found in the documents in the classes.
For adapting the timer for visual, the original **change()** function got changed as it cannot work for timer. Now, the **change(double currentTime)** can accept a double as parameter and change to the final destination by 
the time process. And for better animation effect. We adjust the timer to 0.1s per time.

We modify the overlapping condition. Besides, we implement a builder to read the text files in the **AnimatiorModellmpl**. As we do not have controllers yet, the main is our temporary controller. 
We plan to move some function to the controller for the final vision. What's more, we add **String svgString(int speed, boolean isLoop)** to our behavior class that can support SVGview to use.

==========================================
## Changes from assignment 8 to assignment 10

For better control the outcome of **change(double currentTime)**, we added a **getMaxTick()** in the model interface. When the timer => the max tick it will stop. That is a helpful function in visual view and editor view especially for the loop function. We added other condition to **change(double currentTime)** to make sure 
every change can be performed during the time no matter how fast the speed is. We built the controller interface and implemented it as **ControllerImpl**. We removed some function out from the main class **EasyAnimator** and moved them to the **ControllerImpl**. Also, we added the **copyModel()** in the model to protect the data to 
let the original data cannot be changed by other non-model class.

Another major change is that we no longer use array lists in the view classes to store the shapes and animations. Instead, we directly use an **AnimatorModel** in the views to store the shapes and animations. The model in the view is the copy of the original model and is created in the **EasyAnimator** class. Also, in the **EasyAnimator** method, we removed
the last "switch" statements and directly initialize a controller, then pass the model and the view to the controller. It cuts down lots of space in the main method. Besides, we add a boolean "isLoop" as parameter to the **writeState()** method in the view classes. It allows users to output SVG files that loop back automatically, which
can be executed in the **EditorView**. 

There are some other minor changes, like we rewrote some Java docs in the classes and adjusted some tests to fit the changes made on the methods and classes.



