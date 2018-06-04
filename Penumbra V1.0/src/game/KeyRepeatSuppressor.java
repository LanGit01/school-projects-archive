package game;
import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
 
public class KeyRepeatSuppressor {
  private class Listener implements AWTEventListener, KeyListener,
      FocusListener {
    private boolean consumeKeyTyped;
    private boolean consumeKeyReleased;
 
    private final Robot robot;
    private int fakeKeyCode;
    private final ArrayList<Integer> keyCodes;
 
    public Listener(Robot robot) throws AWTException {
      consumeKeyTyped = false;
      consumeKeyReleased = false;
 
      keyCodes = new ArrayList<Integer>();
      keyCodes.add(KeyEvent.VK_0);
      keyCodes.add(KeyEvent.VK_9);
      keyCodes.add(KeyEvent.VK_8);
      keyCodes.add(KeyEvent.VK_7);
      keyCodes.add(KeyEvent.VK_6);
      keyCodes.add(KeyEvent.VK_5);
      keyCodes.add(KeyEvent.VK_4);
      keyCodes.add(KeyEvent.VK_3);
      keyCodes.add(KeyEvent.VK_2);
      keyCodes.add(KeyEvent.VK_1);
      keyCodes.add(KeyEvent.VK_A);
      keyCodes.add(KeyEvent.VK_B);
      keyCodes.add(KeyEvent.VK_C);
      keyCodes.add(KeyEvent.VK_D);
      keyCodes.add(KeyEvent.VK_E);
      keyCodes.add(KeyEvent.VK_F);
      keyCodes.add(KeyEvent.VK_G);
      keyCodes.add(KeyEvent.VK_H);
      keyCodes.add(KeyEvent.VK_I);
      keyCodes.add(KeyEvent.VK_J);
      keyCodes.add(KeyEvent.VK_K);
      keyCodes.add(KeyEvent.VK_L);
      keyCodes.add(KeyEvent.VK_M);
      keyCodes.add(KeyEvent.VK_N);
      keyCodes.add(KeyEvent.VK_O);
      keyCodes.add(KeyEvent.VK_P);
      keyCodes.add(KeyEvent.VK_Q);
      keyCodes.add(KeyEvent.VK_R);
      keyCodes.add(KeyEvent.VK_S);
      keyCodes.add(KeyEvent.VK_T);
      keyCodes.add(KeyEvent.VK_U);
      keyCodes.add(KeyEvent.VK_V);
      keyCodes.add(KeyEvent.VK_W);
      keyCodes.add(KeyEvent.VK_X);
      keyCodes.add(KeyEvent.VK_Y);
      keyCodes.add(KeyEvent.VK_Z);
 
      this.robot = robot;
    }
 
    @Override
    public void eventDispatched(AWTEvent event) {
      final KeyEvent keyEvent = (KeyEvent) event;
 
      if (!keyEvent.isConsumed()) {
        switch (keyEvent.getID()) {
        case KeyEvent.KEY_PRESSED:
          keyPressed(keyEvent);
          break;
        case KeyEvent.KEY_RELEASED:
          keyReleased(keyEvent);
          break;
        case KeyEvent.KEY_TYPED:
          keyTyped(keyEvent);
          break;
        default:
          throw new AssertionError();
        }
      }
    }
 
    @Override
    public synchronized void keyPressed(KeyEvent e) {
      int keyCode = e.getKeyCode();
 
      if (fakeKeyCode == keyCode
          || (suppressKeyRepeats && keysDown.contains(keyCode))) {
        e.consume();
        consumeKeyTyped = true;
        consumeKeyReleased = true;
      } else {
        keysDown.add(keyCode);
 
        if (suppressKeyRepeats) {
          Iterator<Integer> alphabetIterator = keyCodes.iterator();
          Integer unheldKey = alphabetIterator.next();
          while (keysDown.contains(unheldKey) || unheldKey.equals(keyCode)) {
            unheldKey = alphabetIterator.next();
          }
          final int finalUnheldKey = unheldKey;
          EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
              fakeKeyCode = finalUnheldKey;
            }
          });
 
          robot.keyPress(finalUnheldKey);
          robot.keyRelease(finalUnheldKey);
        }
 
        if (logKeysPressed) {
          keysPressed.add(keyCode);
        }
 
        notifyKeyPressed(e);
      }
    }
 
    @Override
    public synchronized void keyReleased(KeyEvent e) {
      int keyCode = e.getKeyCode();
 
      if (fakeKeyCode == keyCode && consumeKeyReleased) {
        e.consume();
        consumeKeyReleased = false;
        fakeKeyCode = KeyEvent.VK_UNDEFINED;
      } else {
        keysDown.remove(keyCode);
 
        notifyKeyReleased(e);
      }
    }
 
    @Override
    public synchronized void keyTyped(KeyEvent e) {
      if (consumeKeyTyped) {
        e.consume();
        consumeKeyTyped = false;
      } else if (logKeysTyped) {
        keysTyped.append(e.getKeyChar());
 
        notifyKeyTyped(e);
      }
    }
 
    @Override
    public void focusGained(FocusEvent arg0) {
    }
 
    @Override
    public void focusLost(FocusEvent arg0) {
      keysDown.clear();
      notifyChange();
    }
  }
 
  private final Listener listener;
 
  private final ArrayList<Integer> keysPressed;
  private final HashSet<Integer> keysDown;
  private StringBuilder keysTyped;
 
  private boolean suppressKeyRepeats;
  private boolean logKeysPressed;
  private boolean logKeysTyped;
 
  private final EventListenerList eventListenerList;
 
  public KeyRepeatSuppressor() throws AWTException {
    this(true, false, false);
  }
 
  public KeyRepeatSuppressor(boolean suppressKeyRepeats,
      boolean logKeysPressed, boolean logKeysTyped) throws AWTException {
    this(suppressKeyRepeats, logKeysPressed, logKeysTyped, new Robot());
  }
 
  public KeyRepeatSuppressor(boolean suppressKeyRepeats,
      boolean logKeysPressed, boolean logKeysTyped, Robot robot)
      throws AWTException {
    listener = new Listener(robot);
 
    keysDown = new HashSet<Integer>();
    keysPressed = new ArrayList<Integer>();
    keysTyped = new StringBuilder();
 
    this.suppressKeyRepeats = suppressKeyRepeats;
    this.logKeysPressed = logKeysPressed;
    this.logKeysTyped = logKeysTyped;
 
    eventListenerList = new EventListenerList();
  }
 
  public void install() {
    Toolkit.getDefaultToolkit().addAWTEventListener(listener,
        AWTEvent.KEY_EVENT_MASK);
  }
 
  public void remove() {
    Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
  }
 
  public void installTo(Component component) {
    component.addKeyListener(listener);
    component.addFocusListener(listener);
  }
 
  public void removeFrom(Component component) {
    component.removeKeyListener(listener);
    component.removeFocusListener(listener);
  }
 
  public synchronized List<Integer> getKeysPressed() {
    @SuppressWarnings("unchecked")
    List<Integer> keysPressed = (List<Integer>) this.keysPressed.clone();
 
    if (!keysPressed.isEmpty()) {
      this.keysPressed.clear();
 
      notifyChange();
    }
 
    return keysPressed;
  }
 
  public synchronized Set<Integer> getKeysDown() {
    return Collections.unmodifiableSet(keysDown);
  }
 
  public synchronized StringBuilder getKeysTyped() {
    StringBuilder keysTyped = this.keysTyped;
 
    this.keysTyped = new StringBuilder();
 
    if (keysTyped.toString().length() > 0) {
      notifyChange();
    }
 
    return keysTyped;
  }
 
  public boolean getLogKeysPressed() {
    return logKeysPressed;
  }
 
  public synchronized void setLogKeysPressed(boolean logKeysPressed) {
    this.logKeysPressed = logKeysPressed;
  }
 
  public boolean getLogKeysTyped() {
    return logKeysTyped;
  }
 
  public synchronized void setLogKeysTyped(boolean logKeysTyped) {
    this.logKeysTyped = logKeysTyped;
  }
 
  public boolean getSuppressKeyRepeats() {
    return suppressKeyRepeats;
  }
 
  public void setSuppressKeyRepeats(boolean suppressKeyRepeats) {
    this.suppressKeyRepeats = suppressKeyRepeats;
  }
 
  public void addKeyListener(KeyListener changeListener) {
    eventListenerList.add(KeyListener.class, changeListener);
  }
 
  public void removeKeyListener(KeyListener changeListener) {
    eventListenerList.remove(KeyListener.class, changeListener);
  }
 
  protected void notifyKeyPressed(KeyEvent keyEvent) {
    for (KeyListener keyListener : eventListenerList
        .getListeners(KeyListener.class)) {
      keyListener.keyPressed(keyEvent);
    }
    notifyChange();
  }
 
  protected void notifyKeyReleased(KeyEvent keyEvent) {
    for (KeyListener keyListener : eventListenerList
        .getListeners(KeyListener.class)) {
      keyListener.keyReleased(keyEvent);
    }
    notifyChange();
  }
 
  protected void notifyKeyTyped(KeyEvent keyEvent) {
    for (KeyListener keyListener : eventListenerList
        .getListeners(KeyListener.class)) {
      keyListener.keyTyped(keyEvent);
    }
    notifyChange();
  }
 
  public void addChangeListener(ChangeListener changeListener) {
    eventListenerList.add(ChangeListener.class, changeListener);
  }
 
  public void removeChangeListener(ChangeListener changeListener) {
    eventListenerList.remove(ChangeListener.class, changeListener);
  }
 
  protected void notifyChange() {
    for (ChangeListener changeListener : eventListenerList
        .getListeners(ChangeListener.class)) {
      changeListener.stateChanged(new ChangeEvent(this));
    }
  }
 
  protected static class KeyRepeatTest extends JFrame {
    private static final long serialVersionUID = 1L;
 
    private final KeyRepeatSuppressor keyRepeatSuppressor;
 
    private final JLabel keysDownLabel;
 
    public KeyRepeatTest() throws AWTException {
      super("Key Repeat Suppression Test");
 
      keyRepeatSuppressor = new KeyRepeatSuppressor();
 
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());
      contentPane.setPreferredSize(new Dimension(480, 240));
 
      JTextArea suppressedTextArea = new JTextArea();
      keyRepeatSuppressor.installTo(suppressedTextArea);
      JTextArea textArea = new JTextArea();
 
      JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
          suppressedTextArea, textArea);
      splitPane.setResizeWeight(0.5);
      contentPane.add(splitPane);
 
      keysDownLabel = new JLabel();
      keyRepeatSuppressor.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
          refreshKeysDownLabel();
        }
      });
      refreshKeysDownLabel();
      contentPane.add(keysDownLabel, BorderLayout.PAGE_END);
    }
 
    protected void refreshKeysDownLabel() {
      keysDownLabel.setText("Keys down: " + keyRepeatSuppressor.getKeysDown());
    }
  }
 
  public static void main(String... args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          KeyRepeatTest keyRepeatTest = new KeyRepeatTest();
 
          keyRepeatTest.pack();
          keyRepeatTest.setLocationRelativeTo(null);
          keyRepeatTest.setVisible(true);
        } catch (AWTException e) {
          e.printStackTrace();
        }
      }
    });
  }
}