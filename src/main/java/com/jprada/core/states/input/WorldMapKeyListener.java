package com.jprada.core.states.input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jprada.core.GameWindow;
import com.jprada.core.entity.GameCharacter;
import com.jprada.core.entity.Actor.Direction;
import com.jprada.core.states.GameState;

public class WorldMapKeyListener extends Keyboard {

		

	public WorldMapKeyListener(GameCharacter player) {
		super();
		this.player = player;
	}

	

	@Override
	public void keyReleased(KeyEvent e) {

		if (!GameState.INPUT_DISABLED) {
			if (!e.isAutoRepeat()) {
				int keyCode = e.getKeyCode();

				if (keyCode == KeyEvent.VK_SHIFT) {
					GameWindow.ENABLE_DEBUG_INFO = false;
				}

				if (!GameState.INPUT_DISABLED) {
					if (keyCode == KeyEvent.VK_LEFT) {
						this.keyStates[LEFT_KEY] = false;
						if (keyStates[DOWN_KEY]) {
							player.setMovingDirection(Direction.down);
						} else if (keyStates[UP_KEY]) {
							player.setMovingDirection(Direction.up);
						} else if (keyStates[RIGHT_KEY]) {
							player.setMovingDirection(Direction.right);
						} else {
							player.setMovingDirection(null);
						}
					} else if (keyCode == KeyEvent.VK_RIGHT) {
						this.keyStates[RIGHT_KEY] = false;

						if (keyStates[DOWN_KEY]) {
							player.setMovingDirection(Direction.down);
						} else if (keyStates[UP_KEY]) {
							player.setMovingDirection(Direction.up);
						} else if (keyStates[LEFT_KEY]) {
							player.setMovingDirection(Direction.left);
						} else {
							player.setMovingDirection(null);
						}
					} else if (keyCode == KeyEvent.VK_UP) {
						this.keyStates[UP_KEY] = false;
						if (keyStates[DOWN_KEY]) {
							player.setMovingDirection(Direction.down);
						} else if (keyStates[RIGHT_KEY]) {
							player.setMovingDirection(Direction.right);
						} else if (keyStates[LEFT_KEY]) {
							player.setMovingDirection(Direction.left);
						} else {
							player.setMovingDirection(null);
						}
					} else if (keyCode == KeyEvent.VK_DOWN) {
						this.keyStates[DOWN_KEY] = false;
						if (keyStates[UP_KEY]) {
							player.setMovingDirection(Direction.up);
						} else if (keyStates[RIGHT_KEY]) {
							player.setMovingDirection(Direction.right);
						} else if (keyStates[LEFT_KEY]) {
							player.setMovingDirection(Direction.left);
						} else {
							player.setMovingDirection(null);
						}
					}
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!e.isAutoRepeat()) {
			int keyCode = e.getKeyCode();

			if (keyCode == KeyEvent.VK_SPACE && !player.isWantToInteract()) {
				player.setWantToInteract(true);
				flushStates(); // FLush the key states so that no key is hold down by any script
			}

			if (keyCode == KeyEvent.VK_SHIFT) {
				System.out.println("Pressed Tab");
				GameWindow.ENABLE_DEBUG_INFO = true;
			}

			if (!GameState.INPUT_DISABLED) {
				if (keyCode == KeyEvent.VK_LEFT) {
					this.keyStates[LEFT_KEY] = true;
					if (player.getMovingDirection() != Direction.left) {
						player.setMovingDirection(Direction.left);
					}
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					this.keyStates[RIGHT_KEY] = true;
					if (player.getMovingDirection() != Direction.right) {
						player.setMovingDirection(Direction.right);
					}
				} else if (keyCode == KeyEvent.VK_UP) {
					this.keyStates[UP_KEY] = true;
					if (player.getMovingDirection() != Direction.up) {
						player.setMovingDirection(Direction.up);
					}
				} else if (keyCode == KeyEvent.VK_DOWN) {
					this.keyStates[DOWN_KEY] = true;
					if (player.getMovingDirection() != Direction.down) {
						player.setMovingDirection(Direction.down);
					}
				}
			}
		}
	}
}
