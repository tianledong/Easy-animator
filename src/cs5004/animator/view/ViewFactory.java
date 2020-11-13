package cs5004.animator.view;

/**
 * This is a factory class that initializes different view type depends on the string passed in.
 * The "text" viewType initializes a textual view, the "svg" viewType initializes a svg view,
 * the "visual" viewType initializes a visual view, and the playback initializes an editor view.
 */
public class ViewFactory {
  /**
   * Create different concrete view types depends on the string passed in.
   *
   * @param viewType the view type to be created
   * @return the concrete view class that is initialized
   * @throws IllegalArgumentException if the view type is empty or isn't one of the valid view types
   */
  public static View createView(String viewType) throws IllegalArgumentException {
    switch (viewType) {
      case "text":
        return new TextualView();
      case "svg":
        return new SVGView();
      case "visual":
        return new VisualView();
      case "playback":
        return new EditorView();
      default:
        throw new IllegalArgumentException("Unknown view type");
    }
  }
}
