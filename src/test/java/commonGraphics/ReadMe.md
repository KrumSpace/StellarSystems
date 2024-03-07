# Common test graphics

The most commonly used graphics.


## ColorUtils

Contains various utilities regarding colors, such as:
* `public static final @NotNull Color TRANSPARENT_BLACK`
  * Returns a transparent black color.
* `public static @NotNull Color getGray(int brightness, int alpha)`
  * Gets a gray color from common brightness and applies specified alpha value.
  * Brightness and alpha must be within 0-255 (inclusive).


## StringUtils

Contains various utilities regarding strings, such as:
* `public static final int DEFAULT_TEXT_HEIGHT`
  * Returns a default text height for drawing strings.
* ```
  public static void drawNumberedString(@NotNull Graphics g, @Nullable String line,
                                        int @NotNull [] textLocation, int lineNumber)
  ```
  * Intended for drawing multiple lines of text.
  * Draws a string at `textLocation + DEFAULT_TEXT_HEIGHT * lineNumber`
  * Doesn't set the color.
  * Null line doesn't draw anything.


## AbstractWindow

An abstract window with default parameters.

Constructor:
```
public AbstractWindow(@Nullable Dimension size, @Nullable Point location, @Nullable String title)
```

Has `public abstract void addPanels();`, which needs to be overridden and doesn't get called automatically.


## WindowUpdater

Extends `ThreadAbstraction.AbstractUpdater` and updates an `AbstractWindow`.

Update frequency defined using frames-per-second, default set to 60.


## UpdatingWindow

Extends `AbstractWindow` and has a `WindowUpdater`.

Has 2 constructors:
* With default frame rate:
  ```
  public UpdatingWindow(@Nullable Dimension size, @Nullable Point location, @Nullable String title)
  ```
* With custom frame rate:
  ```
  public UpdatingWindow(@Nullable Dimension size, @Nullable Point location, @Nullable String title, long frameRate)
  ```

Starts updating, only after `public void startUpdating()` gets called.

Get currently set preferred FPS: `public long getPreferredFrameRate()`

Set desired FPS: `public void setFrameRate(long frameRate)`


## AbstractKeyListener

An abstract key listener, extends `java.awt.event.KeyListener`

_Check code for more info..._


## Panels

Most basic panels.


### MinimalPanel

An abstract minimal panel with paintable borders and diagonals, extends `JPanel`

Has `mainPaint` and `finalPaint` methods.

_Check code for more info..._


### FixedSizePanel

Extends `MinimalPanel`

_Check code for more info..._


### FixedVerticalPanel

Extends `FixedSizePanel`

_Check code for more info..._


### FixedHorizontalPanel

Extends `FixedSizePanel`

_Check code for more info..._


### sidePanels

Commonly used side panels

_Check code for more info..._
