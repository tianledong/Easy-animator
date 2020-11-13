package cs5004.animator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Arrays;

import javax.swing.JOptionPane;

import cs5004.animator.controller.Controller;
import cs5004.animator.controller.ControllerImpl;
import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.AnimatorModelImpl;
import cs5004.animator.util.AnimationReader;
import cs5004.animator.view.View;


import static cs5004.animator.view.ViewFactory.createView;

/**
 * The main class and temporary controller (We will build real controllers in the third vision).
 * Now, it is used to read the command line and pass it to model and viewer.
 */
public final class EasyAnimator {

  /**
   * The main method that reads the args commandline and initializes different views.
   *
   * @param args commandline from users.
   */
  public static void main(String[] args) {
    AnimatorModel model;
    Readable in = new StringReader("");
    int speed = 1;
    int newSpeed;
    String viewType = "";
    String fileWriter = "System.out";
    int i;

    // check if -in and -view are provided
    if ((Arrays.stream(args).noneMatch(s -> s.equals("-in"))
            || (Arrays.stream(args).noneMatch(s -> s.equals("-view"))))) {
      JOptionPane.showMessageDialog(null,
              "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
    }
    for (i = 0; i < args.length; i++) {
      String command = args[i];
      switch (command) {
        case "-in":
          try {
            in = new FileReader(args[i + 1]);
            i++;
          } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "File does not find", "Error", JOptionPane.ERROR_MESSAGE);
          } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null,
                    "Out of index", "Error", JOptionPane.ERROR_MESSAGE);
          }
          break;
        case "-view":
          try {
            viewType = args[i + 1];
            i++;
          } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null,
                    "Out of index", "Error", JOptionPane.ERROR_MESSAGE);
          }
          break;
        case "-out":
          try {
            // jump to the next arg if the file name is not provided
            // and use System.out as the default output
            if (!args[i + 1].equals("-speed") && !args[i + 1].equals("-view")
                    && !args[i + 1].equals("-in")) {
              fileWriter = args[i + 1];
              i++;
            }
          } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null,
                    "Out of index", "Error", JOptionPane.ERROR_MESSAGE);
          }
          break;
        case "-speed":
          try {
            newSpeed = Integer.parseInt(args[i + 1]);
            if (newSpeed > 0) {
              speed = newSpeed;
            } else {
              JOptionPane.showMessageDialog(null,
                      "Negative speed", "Error", JOptionPane.ERROR_MESSAGE);
            }
            i++;
          } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null,
                    "Out of index", "Error", JOptionPane.ERROR_MESSAGE);
          } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid number input", "Error", JOptionPane.ERROR_MESSAGE);
          }
          break;
        default:
          JOptionPane.showMessageDialog(null,
                  "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
    model = AnimationReader.parseFile(in, new AnimatorModelImpl.Builder()).copyModel();
    View view = createView(viewType);
    Controller controller = new ControllerImpl(model, view, speed, fileWriter);
    controller.start();
  }
}
