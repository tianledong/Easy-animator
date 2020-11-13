package cs5004.animator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;


import cs5004.animator.model.shape.Shape;

/**
 * This is a package private class that represents a JPanel component in the visual view. It's used
 * to store shapes and fill all shapes to be played.
 */
class AnimationPanel extends JPanel {
  private List<Shape> shapes;

  /**
   * A constructor that initializes the panel. It uses the super constructor in the JPanel class
   * and reset the size and background color.
   *
   * @param shapes the list of shapes to be played
   */
  AnimationPanel(List<Shape> shapes) {
    super();
    this.shapes = shapes;
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(1500, 1500));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics = (Graphics2D) g;
    for (Shape shape : this.shapes) {
      int x = (int) shape.getPositionData().getX();
      int y = (int) shape.getPositionData().getY();
      int dimension1 = (int) shape.getDimension1();
      int dimension2 = (int) shape.getDimension2();
      Color color = shape.getColorData();
      if (shape.getType().equals("oval")) {
        graphics.setColor(color);
        graphics.fillOval(x, y, dimension1 * 2, dimension2 * 2);
        graphics.drawOval(x, y, dimension1 * 2, dimension2 * 2);
      } else if (shape.getType().equals("rectangle")) {
        graphics.setColor(color);
        graphics.fillRect(x, y, dimension1, dimension2);
        graphics.drawRect(x, y, dimension1, dimension2);
      }
    }
  }

  void setShapes(List<Shape> shapes) {
    this.shapes = shapes;
  }

  void resetShapes(List<Shape> shapes) {
    this.shapes = shapes;
  }
}
